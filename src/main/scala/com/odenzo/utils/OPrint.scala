package com.odenzo.utils

import cats.syntax.all._

import fansi.Str
import pprint.{PPrinter, Tree}

object OPrint {

  def oprint[A](a: A): String = pp.apply(a).render

  def secretHandler(a: Any): Option[Tree] = {
    a match {
      case a: Secret => pprint.Tree.Literal(f"Secret(${a.toString})").some
      case _         => Option.empty[Tree]
    }

  }

  val pp =
    new PPrinter(
      defaultWidth = 80, // Because often after logback prefix
      defaultHeight = 750,
      defaultIndent = 2,
      additionalHandlers = (secretHandler _).unlift,
      colorLiteral = fansi.Color.Yellow ++ fansi.Bold.On,
      colorApplyPrefix = fansi.Color.Magenta ++ fansi.Bold.On
    )
}
