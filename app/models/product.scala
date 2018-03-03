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
 * @param price
 * @param imageLocation
 * @param color
 * @param material
 * @param dimensions
 * @param weight
 * @param currentStock
 * @param description
 */ 

case class Product(Id: Option[Long], price: Double, imageLocation: String, 
	color: String, material: String, dimensions: String, weight: String, currentStock: Int, 
	description: String) {

	def create(implicit db:java.sql.Connection) = {
		println("######################################")
		//SQL("INSERT INTO Product (price, imageLocation, color, material, dimensions, weight, 
			// currentStock, description) VALUES ({price}, {imageLocation}, {color}, {material}, 
			// {dimensions}, {weight}, {currentStock}, {descriptions});").on('price -> price,
			// 'imageLocation -> imageLocation, 'color -> color, 'material -> material, 
			// 'dimensions -> dimensions, 'weight -> weight, 'currentStock -> currentStock, 
			// 'description -> description).executeInsert()
	}
}