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
class PaymentMethodController @Inject()(db: Database, cc: ControllerComponents) extends AbstractController(cc) {

	//Create constraints for the payment method form
	val paymentMethodForm = Form (
		mapping(
			"id" -> optional(longNumber),
			"userId" -> longNumber)(PaymentMethod.apply)(PaymentMethod.unapply))

	//Create constraints for the paypal form
	/*val paypalForm = Form (
		mapping(
			"id" -> optional(longNumber),
			"paymentId" -> longNumber,
			"paypalEmail" -> email)(Paypal.apply)(Paypal.unapply))*/


	def createPaypal = Action(parse.formUrlEncoded) { implicit request =>

		//Extract all of the values from the form
		val userId = request.body("userId")(0)
		

		implicit val conn = db.getConnection()

  		println("*********************************************************")
  		conn.close()
  		Ok
	}


}