package com.odenzo.dbdumper.app

import com.typesafe.config.Config
import io.circe.Json

/** Expectd to be at "cm" level */
case class CMAppConfig(cm: CMConfig, config: Config)

case class CMConfig(applicationId: Int, apiVersion: String, sqlDb: DbConfig, hbaseDb: DbConfig, authEnable: Boolean, http: HttpConfig)

case class LdapConfig(config: Map[String, Json])

case class DbConfig(
    url: String,
    driver: String,
    dbType: String, // MySQL or HBASE, more of a comment
    user: String,
    password: String,
    poolInitialSize: Int = 10,
    poolMaxSize: Int = 10,
    poolConnectionTimeoutMillis: Int = 33000
)

case class SecureKeyStore(keyStoreType: String, keys: String)

case class HttpConfig(maxUploadSizeInMb: Int, timeoutInSeconds: Int)
