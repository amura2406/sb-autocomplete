package com.github.amura.test

import com.github.amura.domain.CompanyResult
import com.github.amura.server.APIServer
import com.twitter.finagle.http.Status
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.finatra.json.FinatraObjectMapper
import com.twitter.inject.server.FeatureTest

/**
  * Created by amura on 3/13/17.
  */
class AutocompleteTest extends FeatureTest {

  override val server = new EmbeddedHttpServer(new APIServer)

  val objMapper = server.injector.instance[FinatraObjectMapper]

  test("GET /v1/company/search#normal") {
    val res = server.httpGet(
      path = "/v1/company/search?term=Abb",
      andExpect = Status.Ok
    )

    val json = objMapper.parse[Array[CompanyResult]](res.contentString)
    json.length shouldEqual(15)
  }

  test("GET /v1/company/search#with space") {
    val res = server.httpGet(
      path = "/v1/company/search?term=Abbott%20an",
      andExpect = Status.Ok
    )

    val json = objMapper.parse[Array[CompanyResult]](res.contentString)
    json.length shouldEqual(1)
  }

  test("GET /v1/company/search#with no result") {
    val res = server.httpGet(
      path = "/v1/company/search?term=NoCompanyMatch",
      andExpect = Status.Ok
    )

    val json = objMapper.parse[Array[CompanyResult]](res.contentString)
    json.length shouldEqual(0)
  }

  test("GET /v1/company/search#below 2 chars") {
    val res = server.httpGet(
      path = "/v1/company/search?term=a",
      andExpect = Status.BadRequest
    )
  }

  test("GET /v1/company/search#more than 30 chars") {
    val res = server.httpGet(
      path = "/v1/company/search?term=adwa%20dwa%20dwa%20dwaddsfgaadas%20ada%20wd%20wawdwadaw",
      andExpect = Status.BadRequest
    )
  }

  test("GET /v1/company/search#without query param") {
    val res = server.httpGet(
      path = "/v1/company/search",
      andExpect = Status.BadRequest
    )
  }
}
