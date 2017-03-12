package com.github.amura.module

import com.google.inject.{Provides, Singleton}
import com.twitter.inject.TwitterModule
import com.typesafe.config.Config
import org.mongodb.scala.MongoClient

/**
  * Created by amura on 3/12/17.
  */
object MongoModule extends TwitterModule{

  @Provides
  @Singleton
  def client(config: Config) = {
    val _conf = config.getConfig("mongodb")
    val host = _conf.getString("host")
    val port = _conf.getString("port")
    val maxPool = _conf.getString("maxPoolSize")
    val waitMultiplier = _conf.getString("waitQueueMultiple")

    MongoClient(s"mongodb://$host:$port/?maxPoolSize=$maxPool&waitQueueMultiple=$waitMultiplier")
  }

  @Provides
  @Singleton
  def database(client: MongoClient, config: Config) =
    client.getDatabase(config.getString("mongodb.name"))
}
