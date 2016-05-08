package controllers

import turing._

import com.twitter.bijection.Base64String
import com.twitter.bijection.Conversion.asMethod
import com.twitter.bijection.Injection.connect

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._

class Application extends Controller {
  implicit val string2B64 = connect[String,Array[Byte],Base64String]

  def arithInputs = Form(
    tuple(
      "x" -> number.verifying(min(0)),
      "y" -> number.verifying(min(0))
    )
  )

  def customTM = Form(
    tuple(
      "description" -> text,
    )
  )

  def main = Action {
    Ok(views.html.main("PlaTM"))
  }

  def computeFormResponse(tm: String) = Action { implicit request =>
    try {
      println(request.body)
      val (x,y) = arithInputs.bindFromRequest.get
      Redirect(routes.Application.turing(tm,x,y))
    } catch {
      case _: NoSuchElementException => Redirect(routes.Application.main)
    }
  }

  def customTmFormResponse = Action { implicit request =>
    try {
      val (description, name) = customTM.bindFromRequest.get
      val results: Array[String] = description split "\r\n"
      if ((results filter (_.length != 5)).length != 0)
        Redirect(routes.Application.main)
      else Redirect(routes.Application.create)
    } catch {
      case _: NoSuchElementException => Redirect(routes.Application.main)
    }
  }

  def turing(tm: String, x: Int, y: Int) = Action {
    tm match {
      case "un-add" =>
        val title = "Unary Addition Turing Machine"
        val result = UnaryAdditionTM(x,y)
        Ok(views.html.turingresult(title,x,y,result))
      case "bi-add" =>
        val title = "Binary Addition Turing Machine"
        val result = BinaryAdditionTM(x,y)
        Ok(views.html.turingresult(title,x,y,result))
    }
  }

}
