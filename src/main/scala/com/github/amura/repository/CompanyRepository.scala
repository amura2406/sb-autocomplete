package com.github.amura.repository

import com.github.amura.util.DBConstants._
import com.github.racc.tscg.TypesafeConfig
import com.google.inject.{Inject, Singleton}
import com.twitter.util.{Future => TwitterFuture}
import com.twitter.bijection.Conversion._
import com.twitter.bijection.twitter_util.UtilBijections._
import com.twitter.inject.Logging
import org.mongodb.scala.{Document, MongoDatabase}
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Sorts._

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by amura on 3/12/17.
  */
@Singleton
class CompanyRepository @Inject() (
  database: MongoDatabase,
  @TypesafeConfig("autocomplete.limit") autocompleteLimit: Int
) extends Logging {

  def search(searchTerm: String): TwitterFuture[Seq[Document]] = {
    debug(s"Search term : $searchTerm")
    val companies = database.getCollection(Company.name)

    val normalizedSearchRegex = "^" + searchTerm

    companies
      .find(regex(Company.fieldName, normalizedSearchRegex, "i"))
      .limit(autocompleteLimit)
      .sort(ascending(Company.fieldName))
      .toFuture()
      .as[TwitterFuture[Seq[Document]]]
  }

  def getById(id: Int) = {
    debug(s"Retrieve Company #$id")
    val companies = database.getCollection(Company.name)

    companies
      .find(Document(Company.fieldId -> id))
      .toFuture()
      .as[TwitterFuture[Seq[Document]]]
  }
}
