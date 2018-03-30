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

	val emailRegex = """^[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"""

	def createPaypal = Action(parse.formUrlEncoded) { implicit request =>

		//Extract all of the values from the form
		val userId = request.body("userId")(0)
		val paypalEmail = request.body("paypalEmail")(0)

		//Make sure the email is valid
		if(emailRegex.r.findFirstIn(paypalEmail) == None) {
			Ok(views.html.index("Bad input..."))
		}
		
		//Convert values now that it is safe
		val usId = userId.toLong
		//Connect to database and run the create query
		implicit val conn = db.getConnection()
		val paymentMethod = PaymentMethod(None, usId).create
		//Grab the last payment Id to make the paypal entry
		val pId = PaymentMethod.getLastId(conn)
		val dirtyId = pId(0).Id.toString
		//Regex to get the right paymentId
		val cleanParen = "[()]".toSet
		val paymentId = dirtyId.toString.filterNot(cleanParen).takeRight(1).toLong

		val paypal = Paypal(None, paymentId, paypalEmail).create

		println("*********************************************************")
		conn.close()
		Ok(views.html.index("New paypal added!"))
	}

	def createCreditCard = Action(parse.formUrlEncoded) { implicit request =>

		//Extract all of the values from the form
		val userId = request.body("userId")(0)
		val cardNumber = request.body("cardNumber")(0)
		val expirationDate = request.body("expirationDate")(0)
		val cvv = request.body("cvv")(0)

		//Band aid validation for now...
		if(cardNumber.length != 16) {
			Ok(views.html.index("Bad input..."))
		} else if(cvv.length != 3) {
			Ok(views.html.index("Bad input..."))	
		} else {
			try {
				val usId = userId.toLong
				val cNumber = cardNumber.toLong
				val cvvLong = cvv.toLong

				implicit val conn = db.getConnection()
				val paymentMethod = PaymentMethod(None, usId).create
				//Grab the last payment Id to make the paypal entry
				val pId = PaymentMethod.getLastId(conn)
				val dirtyId = pId(0).Id.toString
				//Regex to get the right paymentId
				val cleanParen = "[()]".toSet
				val paymentId = dirtyId.toString.filterNot(cleanParen).takeRight(1).toLong

				val creditCard = CreditCard(None, paymentId, cNumber, expirationDate, cvvLong).create

				println("*********************************************************")
				conn.close()
				Ok(views.html.index("New credit card added!"))
			} catch {
				case e:Exception=>
				Ok(views.html.index("Bad input..."))
			}
		}
	}

}