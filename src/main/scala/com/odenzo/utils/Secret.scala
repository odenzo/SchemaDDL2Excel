package com.odenzo.utils

import cats.Show
import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec

import scala.util.Random

/** Note: User oprint instead of pprint to continue masking */
case class Secret(secret: String) {

  override def toString = s"${secret.take(2)}...${secret.takeRight(2)}"
}

object Secret {

  def generatePassword(len: Int = 15): String = Random.alphanumeric.take(len).toList.mkString
  def generate: Secret                        = Secret(generatePassword())
  def generate(len: Int = 15): Secret         = Secret(generatePassword(len))

  implicit val codec: Codec[Secret] = deriveCodec[Secret]
  implicit val show: Show[Secret]   = Show.fromToString[Secret]
}

case class LoginCreds(user: String, password: Secret)

object LoginCreds {
  val dummy: LoginCreds                    = LoginCreds("placeholder", Secret("placeholder"))
  def genForUser(user: String): LoginCreds = LoginCreds(user, Secret.generate(15))
}
