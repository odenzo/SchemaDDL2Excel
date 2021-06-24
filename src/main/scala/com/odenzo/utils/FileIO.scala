package com.odenzo.utils

import java.io.{File, FileInputStream}
import scala.io.{Codec, Source}

import cats._
import cats.data._
import cats.syntax.all._
import cats.effect._
import cats.effect.implicits._

object FileIO {

  def readUtfTextFile(f: File): IO[String] = {
    inputStream(f).use { fis =>
      IO(Source.fromInputStream(fis)(Codec.UTF8).getLines().mkString("\n"))
    }
  }

  def inputStream(f: File): Resource[IO, FileInputStream] =
    Resource.make {
      IO(new FileInputStream(f)) // build
    } { inStream =>
      IO(inStream.close()).handleErrorWith(_ => IO.unit) // release
    }

}
