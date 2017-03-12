package com.github.amura.controller

import com.github.amura.domain.{CompanyGetRequest, CompanyResult, CompanySearchRequest}
import com.github.amura.util.DBConstants._
import com.github.amura.repository.CompanyRepository
import com.github.amura.view.CompanyView
import com.google.inject.Inject
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import org.mongodb.scala.Document
import org.mongodb.scala.bson.{BsonInt32, BsonString}

/**
  * Created by amura on 3/12/17.
  */
class CompanyController @Inject() (
  companyRepository: CompanyRepository
) extends Controller {

  get("/v1/company/search") { request: CompanySearchRequest =>
    val searchTerm = request.term
    companyRepository.search(searchTerm) map { results =>
      results flatMap convertDocToModel
    }
  }

  get("/search") { request: Request =>
    response.ok.file("/search.html")
  }

  get("/company/:id"){ request: CompanyGetRequest =>
    companyRepository.getById(request.id) map { results =>
      (results flatMap convertDocToModel).headOption.map { com =>
        CompanyView(com.value, com.label)
      }
    }
  }

  private def convertDocToModel(doc: Document) =
    for {
      id <- doc.get[BsonInt32](Company.fieldId)
      name <- doc.get[BsonString](Company.fieldName)
    } yield CompanyResult(id.getValue, name.getValue)
}
