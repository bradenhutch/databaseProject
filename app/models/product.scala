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
 * @param name
 * @param price
 * @param imageLocation
 * @param color
 * @param material
 * @param dimensions
 * @param weight
 * @param currentStock
 * @param description
 */ 

case class Product(Id: Option[Long], name: String, price: BigDecimal, imageLocation: String, 
	color: String, material: String, dimensions: String, weight: BigDecimal, currentStock: Int, 
	description: String) {

	def create(implicit db:java.sql.Connection) = {
		println("######################################")
		SQL("""
			INSERT INTO product (name, price, imageLocation, color, material, 
			dimensions, weight, currentStock, description) VALUES ({name}, {price}, 
			{imageLocation}, {color}, {material}, {dimensions}, {weight}, 
			{currentStock}, {description});
			""").on(
			'name -> name, 'price -> price, 'imageLocation -> imageLocation, 
			'color -> color, 'material -> material, 'dimensions -> dimensions, 
			'weight -> weight, 'currentStock -> currentStock, 
			'description -> description).executeInsert()
	}

}

object Product {

	val tableName = "product"
	val simple = {
    	get[Option[Long]](tableName + ".Id") ~
    	get[String](tableName + ".name") ~
    	get[BigDecimal](tableName + ".price") ~
    	get[String](tableName + ".imageLocation") ~
    	get[String](tableName + ".color") ~
    	get[String](tableName + ".material") ~
    	get[String](tableName + ".dimensions") ~
    	get[BigDecimal](tableName + ".weight") ~
    	get[Int](tableName + ".currentStock") ~
    	get[String](tableName + ".description") map {
    		case id~name~price~imageLocation~color~material~dimensions~weight~currentStock~
    		description =>
    			Product(id, name, price, imageLocation, color, material, dimensions, weight, 
    				currentStock, description)
    	}
	}

	def returnAll(implicit db:java.sql.Connection) = {
		SQL("""
			SELECT * FROM product;
			""").as(simple *)
	}
}