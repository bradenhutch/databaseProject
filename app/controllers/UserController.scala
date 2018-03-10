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
import org.apache.commons.codec.digest.DigestUtils


@Singleton
case class UserController @Inject()(db: Database, cc: ControllerComponents) extends AbstractController(cc) {

	//Create constraints for the user form
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

  	def create = Action(parse.urlFormEncoded) { implicit request =>

		//Extract all of the values from the from
		val username = request.body("username")(0)
		val userPhoneNumber = request.body("userPhoneNumber")(0)
		val userFirstName = request.body("userFirstName")(0)
		val userLastName = request.body("userLastName")(0)
		val userEmail = request.body("userEmail")(0)
		val userPassword = DigestUtils.sha256Hex(request.body("userPassword")(0))
		val userGender = request.body("userGender")(0)
		val userAdminString = request.body("userAdmin")(0)

		//Bind the form and evaluate it
		val processedForm = userForm.bindFromRequest
		processedForm.fold(hasErrors => {
			println("Why do you try to break my users??!?!?")
			BadRequest(views.html.index("Bad input..."))
		}, 
		success => {
			//Convert values now that it is safe
			val userAdmin = userAdminString.toBoolean
			//Connect to the database and run the create query
		  	implicit val conn = db.getConnection()
			val user = User(None, username, userPhoneNumber, userFirstName, userLastName, userEmail, 
			userPassword, userGender, userAdmin).create
			println("*********************************************************")
			conn.close()
			Ok(views.html.index("Successfully added new user"))
		})
	}

	def delete = Action(parse.urlFormEncoded) { implicit request =>
		
		//Check to see if the input is an int
		try {
			val deleteID = request.body("deleteID")(0).toInt
			implicit val conn = db.getConnection()
			//This will be an empty list if the user doesn't exist
			if(User.returnOneById(conn, deleteID).toString != "List()") {
				User.delete(conn, deleteID)
				conn.close()
				Ok(views.html.index("Delete success!"))
			} else {
				conn.close()
				Ok(views.html.index("That user doesn't exist"))
			}
		} catch {
			case e:Exception=>
			Ok(views.html.index("Bad input..."))
		}		
	}
}
