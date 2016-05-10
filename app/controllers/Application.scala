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

import scala.util.{Try, Success, Failure}

class Application extends Controller {
  implicit val string2B64 = connect[String,Array[Byte],Base64String]

  def arithInputsForm = Form(
    tuple(
      "x" -> number.verifying(min(0)),
      "y" -> number.verifying(min(0))
    )
  )

  def customTmForm = Form(
    tuple(
      "description" -> text,
      "q0" -> number.verifying(min(0)),
      "qH" -> number.verifying(min(0)),
      "blankSymbol" -> text,
      "startPosition" -> number.verifying(min(0)),
      "input" -> text
    )
  )

  def customResubmitForm = Form(
    single(
      "input" -> text
    )
  )

  def main = Action {
    Ok(views.html.main("PlaTM"))
  }

  def create = Action {
    Ok(views.html.custom("PlaTM: Custom Machine"))
  }

  def computeFormResponse(tm: String) = Action { implicit request =>
    try {
      val (x,y) = arithInputsForm.bindFromRequest.get
      Redirect(routes.Application.turing(tm,x,y))
    } catch {
      case _: NoSuchElementException => Redirect(routes.Application.main)
    }
  }

  def customTmFormResponse = Action { implicit request =>
    try {
      val (desc, q0, qH, blank, start, in) = customTmForm.bindFromRequest.get
      val encodedTm = desc.as[Base64String]
      val encodedInit = (s"$q0 $qH $blank $start").as[Base64String]
      Redirect(routes.Application.custom(encodedTm.str, encodedInit.str, in))
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

  def custom(tm: String, init: String, in: String) = Action {
    val description = string2B64.invert(Base64String(tm))
    val parameters = string2B64.invert(Base64String(init))
    (description, parameters) match {
      case (Success(desc), Success(params)) =>
          val initState = params split " "
          val transitions: Array[String] = desc split "\r\n"
          if ((transitions filter (_.length != 5)).length != 0) {
            Redirect(routes.Application.main)
          } else if (initState.length != 4){
            Redirect(routes.Application.main)
          } else {
            val delta = (transitions map createTransition).toMap
            val q0 = initState(0).toInt
            val qH = initState(1).toInt
            val blankSymbol = initState(2)(0)
            val startPosition = initState(3).toInt
            val customTm = CustomTM(delta, q0, qH, blankSymbol, startPosition)
            val result = customTm(in)
            Ok(views.html.customtmresult(in, result, tm, init))
          }
      case _ => Redirect(routes.Application.main)
    }
  }

  def customResubmit(tm: String, init: String) = Action { implicit request => 
    val input = customResubmitForm.bindFromRequest.get
    Redirect(routes.Application.custom(tm, init, input))
  }

  private def createTransition(rule: String):
      ((State, Char), (State, Char, Direction)) = rule(4) match {
    case 'L' => ((rule(0).asDigit, rule(1)), (rule(2).asDigit, rule(3), Left))
    case 'R' => ((rule(0).asDigit, rule(1)), (rule(2).asDigit, rule(3), Right))
    case 'S' => ((rule(0).asDigit, rule(1)), (rule(2).asDigit, rule(3), Still))
  }

}
