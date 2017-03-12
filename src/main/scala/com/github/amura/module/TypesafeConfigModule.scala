package com.github.amura.module

import com.github.racc.tscg.{TypesafeConfigModule => TCModule}
import com.google.inject.{Provides, Singleton}
import com.twitter.inject.{Logging, TwitterModule}
import com.typesafe.config.ConfigFactory

/**
  * Created by amura on 9/11/16.
  */
object TypesafeConfigModule extends TwitterModule with Logging{
  val mode = flag("mode", "dev", "application run mode [dev:default, alpha, sandbox, beta, real]")

  val configPath = "conf/"

  @Provides
  @Singleton
  def provideConfig = config

  protected override def configure(): Unit = {
    install(TCModule.fromConfigWithPackage(config, "com.github.amura"))
  }

  private lazy val config = {
    info(s"LOADING CONFIG FROM: ${mode()}")

    ConfigFactory load (configPath + mode())
  }
}
