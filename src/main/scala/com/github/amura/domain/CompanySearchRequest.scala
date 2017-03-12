package com.github.amura.domain

import com.twitter.finatra.request.QueryParam
import com.twitter.finatra.validation.{MethodValidation, Size, ValidationResult}

/**
  * Created by amura on 3/13/17.
  */
case class CompanySearchRequest(
  @QueryParam term: String = ""
) {

  @MethodValidation
  def checkTerm = {
    val _term = term.trim
    ValidationResult.validate(_term.size >= 2 && _term.size <= 30, "Search term should be between 2 to 30 characters")
  }
}

