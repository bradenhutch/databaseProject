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
 * @param paymentId
 * @param paypalEmail
 */ 

case class Paypal(Id: Option[Long], paymentId: Long, paypalEmail: String) {

	def create(implicit db:java.sql.Connection) = {
		println("######################################")
		SQL("""
			INSERT INTO paypal (paymentId, paypalEmail) VALUES ({paymentId}, {paypalEmail});
			""").on('paymentId -> paymentId, 'paypalEmail -> paypalEmail).executeInsert()
	}
}

object Paypal {
	val tableName = "paypal"
	val simple = {
		get[Option[Long]](tableName + ".Id") ~
		get[Long](tableName + ".userId") ~
		get[String](tableName + ".paypalEmail") map {
			case id~userId~paypalEmail =>
				Paypal(id, userId, paypalEmail)
		}
	}

	val joined = {
		get[Long]("userId") ~
		get[Long]("paymentId") ~		
		get[String]("paypalEmail") map {
			case userId~paymentId~paypalEmail =>
				(userId, paymentId, paypalEmail)
		}
	}

	def returnAllForUser(implicit db:java.sql.Connection, userId: Long) = {
		SQL("""
			SELECT userId, paymentId, paypalEmail 
			FROM paypal 
			INNER JOIN payment_method ON paypal.paymentId = payment_method.Id 
			WHERE userId = {userId};
			""").on('userId -> userId).as(joined *)
	}
}