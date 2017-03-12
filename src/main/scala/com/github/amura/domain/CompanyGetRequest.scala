package com.github.amura.domain

import com.twitter.finatra.request.RouteParam
import com.twitter.finatra.validation.Min

/**
  * Created by amura on 3/13/17.
  */
case class CompanyGetRequest(
  @RouteParam @Min(1) id: Int
)
