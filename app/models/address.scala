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

object Address {

	val tableName = "address"
	val simple = {
    	get[Option[Long]](tableName + ".Id") ~
    	get[Long](tableName + ".userId") ~
    	get[String](tableName + ".streetNumber") ~
    	get[Long](tableName + ".zipCode") ~
    	get[String](tableName + ".city") map {
    		case id~userId~streetNumber~zipCode~city =>
    			Address(id, userId, streetNumber, zipCode, city)
    	}
	}

	def returnAll(implicit db:java.sql.Connection) = {
		SQL("""
			SELECT * FROM address;
			""").as(simple *)
	}
}