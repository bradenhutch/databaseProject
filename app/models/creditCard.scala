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
 * @param paymentId
 * @param cardNumber
 * @param expirationDate
 * @param CVV
 */ 

case class CreditCard(Id: Option[Long], paymentId: Long, cardNumber: Long, expirationDate: String, CVV: Long) {

	def create(implicit db:java.sql.Connection) = {
		println("######################################")
		SQL("""
			INSERT INTO credit_card (paymentId, cardNumber, expirationDate, CVV) VALUES ({paymentId}, {cardNumber},
			{expirationDate},{CVV});
			""").on('paymentId -> paymentId, 'cardNumber -> cardNumber, 'expirationDate -> expirationDate,
			'CVV -> CVV).executeInsert()
	}
}