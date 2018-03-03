package controllers

import javax.inject._
import play.api.db._
import play.api.mvc._
import anorm._
import models._


@Singleton
class OrderController @Inject()(db: Database, cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
  	implicit val conn = db.getConnection()
  	//val order = Orders().create
  	println("*********************************************************")
  	conn.close()
  	Ok
  }

}
