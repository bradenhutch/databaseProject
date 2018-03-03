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
 * @param userId
 * @param username
 * @param phoneNumber
 * @param name
 * @param email
 * @param password
 * @param gender
 * @param admin
 */ 

case class User(userId: Option[Long], username: String, phoneNumber: String, name: String,
	email: String, password: String, gender: String, admin: Boolean) {

	def create(implicit db:java.sql.Connection) = {
		println("######################################")
		//SQL("INSERT INTO user (username, phoneNumber, name, email, password, gender, admin) 
			// VALUES ({username}, {phoneNumber}, {name}, {email}, {password}, {gender}, 
			// {admin});").on('username -> username,'phoneNumber -> phoneNumber, 'name -> name, 
			// 'email -> email, 'password -> password, 'gender -> gender, 'admin -> admin).executeInsert()
	}
}