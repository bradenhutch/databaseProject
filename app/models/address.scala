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
 * @param streetNumber
 * @param zipCode
 * @param city
 */ 

case class Address(Id: Option[Long], userId: Long, streetNumber: String, zipCode: Long, 
	city: String) {


	def create(implicit db:java.sql.Connection) = {
		println("######################################")
		SQL("""
			INSERT INTO address (userId, streetNumber, zipCode, city) 
			VALUES ({userId}, {streetNumber}, {zipCode}, {city});
			""").on('userId -> userId, 'streetNumber -> streetNumber, 'zipCode -> zipCode, 'city -> city).executeInsert()
	}
}