package controllers

import javax.inject._
import play.api.mvc._

object HomeController extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

}
