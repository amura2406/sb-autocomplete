package com.github.amura.server

import com.github.amura.controller.CompanyController
import com.github.amura.module.{MongoModule, TypesafeConfigModule}
import com.google.inject.Module
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, ExceptionMappingFilter, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.inject.Logging

/**
  * Created by amura on 3/12/17.
  */
object APIServerMain extends APIServer

class APIServer extends HttpServer with Logging{
  protected override def defaultFinatraHttpPort: String = ":8080"

  protected override def failfastOnFlagsNotParsed: Boolean = true

  override protected def modules: Seq[Module] = Seq(
    TypesafeConfigModule,
    MongoModule
  )

  override protected def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .filter[ExceptionMappingFilter[Request]]

      .add[CompanyController]
  }
}
