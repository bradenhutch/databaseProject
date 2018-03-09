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
 * @param username
 * @param phoneNumber
 * @param firstName
 * @param lastName
 * @param email
 * @param password
 * @param gender
 * @param admin
 */ 

case class User(Id: Option[Long], username: String, phoneNumber: String, firstName: String,
	lastName: String, email: String, password: String, gender: String, admin: Boolean) {

	def create(implicit db:java.sql.Connection) = {
		println("######################################")
		SQL("""
			INSERT INTO user (username, phoneNumber, firstName, lastName, email, password, gender, 
			admin) VALUES ({username}, {phoneNumber}, {firstName}, {lastName}, {email}, {password}, 
			{gender}, {admin});
			""").on('username -> username,'phoneNumber -> phoneNumber, 'firstName -> firstName, 
			'lastName -> lastName, 'email -> email, 'password -> password, 'gender -> gender, 
			'admin -> admin).executeInsert()
	}
}

object User {

	val tableName = "user"
	val simple = {
		get[Option[Long]](tableName + ".Id") ~
		get[String](tableName + ".username") ~
		get[String](tableName + ".phoneNumber") ~
		get[String](tableName + ".firstName") ~
		get[String](tableName + ".lastName") ~
		get[String](tableName + ".email") ~
		get[String](tableName + ".gender") ~
		get[Boolean](tableName + ".admin") map {
			case id~username~phoneNumber~firstName~lastName~email~gender~admin =>
				User(id, username, phoneNumber, firstName, lastName, email, "", gender, admin)
		}
	}

	def returnAll(implicit db:java.sql.Connection) = {
		SQL("""
			SELECT * FROM user;
			""").as(simple *)
	}

	def returnOne(implicit db: java.sql.Connection, id: Long) = {
		SQL("""
			SELECT * FROM user WHERE Id = {id} LIMIT 1
			""").on('id -> id).executeQuery.as(simple *)
	}

	def delete(implicit db:java.sql.Connection, id: Long) = {
		SQL("""
			DELETE FROM user WHERE Id = {id}
			""").on('id -> id).executeUpdate()
	}
}