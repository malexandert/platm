package controllers

import turing._

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._

class Application extends Controller {

  def arithInputs = Form(
    tuple(
      "x" -> number.verifying(min(0)),
      "y" -> number.verifying(min(0))
    )
  )

  def main = Action {
    Ok(views.html.main("PlaTM"))
  }

  def formResponse(tm: String) = Action { implicit request =>
    try {
      val (x,y) = arithInputs.bindFromRequest.get
      Redirect(routes.Application.turing(tm,x,y))
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
