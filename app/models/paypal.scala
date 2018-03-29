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
 * @param paypalEmail
 */ 

case class Paypal(Id: Option[Long], userId: Long, paypalEmail: String) {

	def create(implicit db:java.sql.Connection) = {
		println("######################################")
	}
}