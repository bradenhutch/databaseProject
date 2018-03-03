package controllers

import javax.inject._
import play.api.db._
import play.api.mvc._
import anorm._
import models._


@Singleton
class BlogController @Inject()(db: Database, cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
  	implicit val conn = db.getConnection()
  	val blog = Blogs(None, "title", None, "text").create
  	println("*********************************************************")
  	conn.close()

  	Ok
  }

  def display = Action {
    Ok(views.html.index("whut up homez"))
  }

}
