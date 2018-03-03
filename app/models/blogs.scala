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
 * Blogs
 * @param id - The ID of the blog post
 * @param title - The title of the blog post
 * @param dateCreated - The date the blog post was created
 * @param text - The text of the blog post
 */ 

case class Blogs(id: Option[Long], title: String, dateCreated: Option[Date] = None, text: String) {

	def create(implicit db:java.sql.Connection) = {
		println("######################################")
		SQL("INSERT INTO blogEntries (title, blogText) VALUES ({title}, {text});").on('title -> title, 'text -> text).executeInsert()
	}
}