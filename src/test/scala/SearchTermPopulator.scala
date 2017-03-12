import com.github.amura.util.DBConstants.{Company => Comp}
import org.mongodb.scala.MongoClient
import faker._
import org.mongodb.scala.bson.collection.immutable.Document

import scala.collection.mutable
import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by amura on 3/12/17.
  */
object SearchTermPopulator extends App{
  val client = MongoClient("mongodb://localhost:27017/?maxPoolSize=1000&waitQueueMultiple=2")
  val db = client.getDatabase("sb")

  val collection = db.getCollection(Comp.name)

  val tempSet = mutable.Set[String]()
  (1 to 100000) foreach { i =>
    tempSet add Company.name
  }
  println(s"Generated ${tempSet.size} names")

  val documents = (tempSet.zipWithIndex map { case (company, idx) =>
    Document(Comp.fieldId -> idx, Comp.fieldName -> company)
  }).toSeq

  val insertAndCount = for {
    _ <- collection.drop()
    _ <- collection.createIndex(Document(Comp.fieldName -> "text"))
    _ <- collection.insertMany(documents)
    count <- collection.count()
  } yield count

  val count = Await.result(insertAndCount.head(), 2 minutes)
  println(s"Finished inserting $count documents")
}
