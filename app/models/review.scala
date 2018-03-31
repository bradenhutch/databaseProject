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
 * @param productId
 * @param reviewDate
 * @param author
 * @param reviewText
 */ 

case class Review(Id: Option[Long], productId: Long, reviewDate: String, author: String, 
	reviewText: String) {

	def create(implicit db:java.sql.Connection) = {
		//SQL("INSERT INTO Address (productId, reviewDate, author, reviewText) 
			// VALUES ({productId}, {reviewDate}, {author}, {reviewText});").on('productId -> productId,
			// 'reviewDate -> reviewDate, 'author -> author, 'reviewText -> reviewText).executeInsert()
	}
}