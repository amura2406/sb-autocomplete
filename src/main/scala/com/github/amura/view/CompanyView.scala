package com.github.amura.view

import com.twitter.finatra.response.Mustache

/**
  * Created by amura on 3/13/17.
  */
@Mustache("company")
case class CompanyView(
  id: Int,
  name: String
)
