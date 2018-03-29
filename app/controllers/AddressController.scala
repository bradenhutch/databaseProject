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
class AddressController @Inject()(db: Database, cc: ControllerComponents) extends AbstractController(cc) {

	//Create constraints for the address form
	val addressForm = Form(
		mapping(
			"id" -> optional(longNumber),
			"userId" -> longNumber,
			"streetNumber" -> nonEmptyText,
			"zipCode" -> longNumber,//(min = 10000, max = 99999),
			"city" -> nonEmptyText)(Address.apply)(Address.unapply))

	def create = Action(parse.formUrlEncoded) { implicit request =>
		
		//Extract all of the values from the from
		val userId = request.body("userId")(0)
		val streetNumber = request.body("streetNumber")(0)
		val zipCode = request.body("zipCode")(0).toInt
		val city = request.body("city")(0)

		//Bind the form and evaluate it
		val processedForm = addressForm.bindFromRequest
		processedForm.fold(hasErrors => {
			println(userId, streetNumber, zipCode, city)
			println("Why do you try to break my addresses??!?!?")
			BadRequest(views.html.index(hasErrors.toString))
		},
		success => {
			//Convert values now that it is safe
			val zCode = zipCode.toLong
			val usId = userId.toLong
			//Connect to the database and run the create query
			implicit val conn = db.getConnection()
			val address = Address(None, usId, streetNumber, zCode, city).create
			println("*********************************************************")
			conn.close()
			Ok(views.html.index("Address registered."))
		}
	)
		
  }

}
