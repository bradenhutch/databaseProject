package models

import anorm._
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
 * @param createdDate
 * @param shippedDate
 * @param subtotal
 * @param tax
 * @param total
 */ 

case class Orders(Id: Option[Long], userId: Long, shippingAddId: Long, billingAddId: Long,
	createdDate: String, dateCreated: String, subtotal: Double, tax: Double, total: Double) {

	def create(implicit db:java.sql.Connection) = {
		println("######################################")
		//SQL("INSERT INTO orders (userId, shippingAddId, billingAddId, 
			// createdDate, shippedDate, subtotal, tax, total) 
			// VALUES ({userId}, {shippingAddId}, {billingAddId}, {createdDate}, 
			// {shippedDate}, {subtotal}, {tax}, {total});").on('userId -> userId, 
			// 'shippingAddId -> shippingAddId, 'billingAddId -> billingAddId, 
			// 'createdDate -> createdDate, 'shippedDate -> shippedDate, 
			// 'subtotal -> subtotal, 'tax -> tax, 'total -> total).executeInsert()
	}
}