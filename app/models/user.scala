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
 * @param firstName
 * @param lastName
 * @param email
 * @param password
 * @param gender
 * @param admin
 */ 

case class User(userId: Option[Long], username: String, phoneNumber: String, firstName: String,
	lastName: String, email: String, password: String, gender: String, admin: Boolean) {

	def create(implicit db:java.sql.Connection) = {
		println("######################################")
		//SQL("INSERT INTO user (username, phoneNumber, firstName, lastName, email, password, gender, admin) 
			// VALUES ({username}, {phoneNumber}, {firstName}, {lastName}, {email}, {password}, {gender}, 
			// {admin});").on('username -> username,'phoneNumber -> phoneNumber, 'firstName -> firstName, 'lastName -> lastName 
			// 'email -> email, 'password -> password, 'gender -> gender, 'admin -> admin).executeInsert()
	}
}