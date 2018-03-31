package models

import anorm._
import anorm.SqlParser._
import util.Random
import java.sql.SQLException
import java.sql.Date
import play.api.db._
import play.api.Play.current
import play.api.Logger
import controllers.routes
import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter, ISODateTimeFormat}
import javax.inject._

/**
 * @param Id
 * @param userId
 * @param shippingAddId
 * @param billingAddId
 * @param paymentId
 * @param createdDate
 * @param productOrderId
 * @param subtotal
 * @param tax
 * @param total
 */ 

case class Orders(Id: Option[Long], userId: Long, shippingAddId: Long, billingAddId: Long, paymentId: Long,
	createdDate: Option[DateTime], productOrderId: Long, subtotal: BigDecimal, tax: BigDecimal, total: BigDecimal) {

	def createOrder(implicit db:java.sql.Connection) = {
		SQL("""
			INSERT INTO orders (userId, shippingAddId, billingAddId, 
			productOrderId, paymentId, subtotal, tax, total) 
			VALUES ({userId}, {shippingAddId}, {billingAddId},
			{productOrderId}, {paymentId}, {subtotal}, {tax}, {total});
			""").on('userId -> userId, 
			'shippingAddId -> shippingAddId, 'billingAddId -> billingAddId, 
			'paymentId -> paymentId, 'productOrderId -> productOrderId, 
			'subtotal -> subtotal, 'tax -> tax, 'total -> total).executeInsert()
	}
}

object Orders {

	val tableName = "orders"
	val simple = {
		get[Option[Long]](tableName + ".Id") ~
		get[Long](tableName + ".userId") ~
		get[Long](tableName + ".shippingAddId") ~
		get[Long](tableName + ".billingAddId") ~
		get[Long](tableName + ".paymentId") ~
		get[Option[DateTime]](tableName + ".createdDate") ~
		get[Long](tableName + ".productOrderId") ~
		get[BigDecimal](tableName + ".subtotal") ~
		get[BigDecimal](tableName + ".tax") ~
		get[BigDecimal](tableName + ".total") map {
			case id~userId~shippingAddId~billingAddId~paymentId~createdDate~productOrderId~subtotal~tax~
			total =>
				Orders(id, userId, shippingAddId, billingAddId, paymentId, createdDate, productOrderId, subtotal, 
					tax, total)
		}
	}

	val simpleProductOrder = {
		get[Option[Long]]("Id") ~
		get[Long]("productId") ~
		get[Long]("quantity") map {
			case id~productId~quantity =>
				(id, productId, quantity)
		}
	}

	val joinedSubtotal = {
		get[BigDecimal]("subtotal") ~
		get[Long]("Id") map {
			case subtotal~id =>
				(subtotal, id)
		}
	}

	def returnAllForUser(implicit db:java.sql.Connection, userId: Long) = {
		SQL("""
			SELECT * FROM orders WHERE userId = {userId};
			""").on('userId -> userId).as(simple *)
	}

	def createProductOrder(implicit db:java.sql.Connection, productId: Long, quantity: Long) = {
		SQL("""
			INSERT INTO order_products (productId, quantity)
			VALUES ({productId}, {quantity});
			""").on('productId -> productId, 'quantity -> quantity).executeInsert()
	}

	def getLastIdOrderProducts(implicit db: java.sql.Connection) = {
		SQL("""
			SELECT * FROM order_products WHERE Id = LAST_INSERT_ID();
			""").as(simpleProductOrder *)
	}

	def getSubtotal(implicit db: java.sql.Connection, productOrderId: Long) = {
		SQL("""
			SELECT price * quantity AS subtotal, order_products.Id
			FROM order_products
			INNER JOIN product ON order_products.productId = product.Id WHERE order_products.Id = {productOrderId};
			""").on('productOrderId -> productOrderId).as(joinedSubtotal *)
	}

	def getLastIdOrder(implicit db: java.sql.Connection) = {
		SQL("""
			SELECT * FROM orders WHERE Id = LAST_INSERT_ID();
			""").as(simple *)
	}

	def runProcedure(implicit db: java.sql.Connection, orderId: Long) = {
		SQL("""
			CALL orderTotals ({orderId});
			""").on('orderId -> orderId).executeUpdate()
	}
}

