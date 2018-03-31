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
 * @param cardNumber
 * @param expirationDate
 * @param CVV
 */ 

case class CreditCard(Id: Option[Long], paymentId: Long, cardNumber: Long, expirationDate: String, CVV: Long) {

	def create(implicit db:java.sql.Connection) = {
		SQL("""
			INSERT INTO credit_card (paymentId, cardNumber, expirationDate, CVV) 
			VALUES ({paymentId}, {cardNumber}, {expirationDate},{CVV});
			""").on('paymentId -> paymentId, 'cardNumber -> cardNumber, 'expirationDate -> expirationDate,
			'CVV -> CVV).executeInsert()
	}
}

object CreditCard {
	val tableName = "credit_card"
	val simple = {
		get[Option[Long]](tableName + ".Id") ~
		get[Long](tableName + "paymentId") ~
		get[Long](tableName + "cardNumber") ~
		get[String](tableName + "expirationDate") ~
		get[Long](tableName + "CVV") map {
			case id~paymentId~cardNumber~expirationDate~cvv =>
				CreditCard(id, paymentId, cardNumber, expirationDate, cvv)
		}
	}

	val joined = {
		get[Long]("userId") ~
		get[Long]("paymentId") ~
		get[Long]("cardNumber") ~
		get[String]("expirationDate") ~
		get[Long]("CVV") map {
			case userId~paymentId~cardNumber~expirationDate~cvv =>
				(userId, paymentId, cardNumber, expirationDate, cvv)
		}
	}

	val joinedShort = {
		get[Long]("userId") ~
		get[Long]("paymentId") ~
		get[Long]("cardNumber") map {
			case userId~paymentId~cardNumber =>
				(userId, paymentId, cardNumber)
		}
	}

	def returnAllForUser(implicit db:java.sql.Connection, userId: Long) = {
		SQL("""
			SELECT userId, paymentId, cardNumber, expirationDate, CVV 
			FROM credit_card 
			INNER JOIN payment_method 
			ON credit_card.paymentId = payment_method.Id 
			WHERE userId = {userId};
			""").on('userId -> userId).as(joined *)
	}

	def returnAllForUserShort(implicit db:java.sql.Connection, userId: Long) = {
		SQL("""
			SELECT userId, paymentId, cardNumber
			FROM credit_card 
			INNER JOIN payment_method 
			ON credit_card.paymentId = payment_method.Id 
			WHERE userId = {userId};
			""").on('userId -> userId).as(joinedShort *)
	}
}