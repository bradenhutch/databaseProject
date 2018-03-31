package controllers

import javax.inject._
import play.api.db._
import play.api.mvc._
import anorm._
import models._


@Singleton
class OrderController @Inject()(db: Database, cc: ControllerComponents) extends AbstractController(cc) {

	def create = Action(parse.formUrlEncoded) { implicit request =>

		/*try {*/
			//Extract all of the values from the form
			val userId = request.body("userId")(0).toLong
			val productId = request.body("productId")(0).toLong
			val quantity = request.body("quantity")(0).toLong
			val paymentId = request.body("paymentId")(0).toLong
			val shipAddId = request.body("shipAddId")(0).toLong
			val billAddId = request.body("billAddId")(0).toLong

			implicit val conn = db.getConnection()

			val productOrder = Orders.createProductOrder(conn, productId, quantity)

			val dirtyProductOrderId = Orders.getLastIdOrderProducts(conn)(0)._1
			//Regex to get the right id
			val cleanParen = "[()]".toSet
			val cleanAlpha = "Some".toSet
			val productOrderId = dirtyProductOrderId.toString.filterNot(cleanParen).filterNot(cleanAlpha).toLong

			val subtotal = Orders.getSubtotal(conn, productOrderId)(0)._1

			val order = Orders(None, userId, shipAddId, billAddId, paymentId, None, productOrderId, subtotal, 0, 0).createOrder

			val dirtyOrderId = Orders.getLastIdOrder(conn)(0).Id
			//Regex to get the right id
			val orderId = dirtyOrderId.toString.filterNot(cleanParen).filterNot(cleanAlpha).toLong

			println(orderId)
			Orders.runProcedure(conn, orderId)

			conn.close()
			Ok(views.html.index("Order added successfully!"))
		/*} catch {
			case e:Exception=>
				Ok(views.html.index("Bad input..."))
		}
*/


		
	}

}
