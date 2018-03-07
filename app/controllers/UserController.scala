package controllers

import javax.inject._
import play.api.data._
import play.api.data.{Mapping, Form}
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db._
import play.api.mvc._
import anorm._
import models._


@Singleton
class UserController @Inject()(db: Database, cc: ControllerComponents) extends AbstractController(cc) {

  def create = Action(parse.urlFormEncoded) { implicit request =>

	//Extract all of the values from the from
	val username = request.body("username")(0)
	val userPhoneNumber = request.body("userPhoneNumber")(0)
	val userFirstName = request.body("userFirstName")(0)
	val userLastName = request.body("userLastName")(0)
	val userEmail = request.body("userEmail")(0)
	val userPassword = request.body("userPassword")(0)
	val userGender = request.body("userGender")(0)
	val userAdmin = request.body("userAdmin")(0).toBoolean

	val userForm = Form(
		mapping(
			"id" -> optional(longNumber),
			"username" -> nonEmptyText,
			"userPhoneNumber" -> nonEmptyText,
			"userFirstName" -> nonEmptyText,
			"userLastName" -> nonEmptyText,
			"userEmail" -> email,
			"userPassword" -> nonEmptyText,
			"userGender" -> nonEmptyText,
			"userAdmin" -> boolean)(User.apply)(User.unapply))

	val processedForm = userForm.bindFromRequest
	processedForm.fold(hasErrors => BadRequest("Invalid submmission"), success => {
	  Ok("Product registered.")
	})


	implicit val conn = db.getConnection()
	val user = User(None, username, userPhoneNumber, userFirstName, userLastName, userEmail, 
		userPassword, userGender, userAdmin).create
	println("*********************************************************")
	conn.close()
	Redirect(routes.AdminController.index)
  }

}
