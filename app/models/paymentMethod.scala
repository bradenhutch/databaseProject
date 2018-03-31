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
 */ 

case class PaymentMethod(Id: Option[Long], userId: Long) {

	def create(implicit db:java.sql.Connection) = {
		SQL("""
			INSERT INTO payment_method (userId) VALUES ({userId});
			""").on('userId -> userId).executeInsert();
	}
}

object PaymentMethod {

	val tableName = "payment_method"
	val simple = {
		get[Option[Long]](tableName + ".Id") ~
		get[Long](tableName + ".userId") map {
			case id~userId =>
				PaymentMethod(id, userId)
		}
	}

	def getLastId(implicit db: java.sql.Connection) = {
		SQL("""
			SELECT * FROM payment_method WHERE Id = LAST_INSERT_ID();
			""").as(simple *)
	}
}