package controllers

import javax.inject._
import play.api.db._
import play.api.mvc._
import anorm._
import models._


@Singleton
class ProductController @Inject()(db: Database, cc: ControllerComponents) extends AbstractController(cc) {

  def create = Action(parse.urlFormEncoded) { implicit request =>

  	//Validate all the data from the form

  	//Extract all of the values from the form
  	val productName = request.body("productName")(0)
  	val productPrice = request.body("productPrice")(0).toDouble
  	val productImageLocation = request.body("productImageLocation")(0)
  	val productColor = request.body("productColor")(0)
  	val productMaterial = request.body("productMaterial")(0)
  	val productDimensions = request.body("productDimensions")(0)
  	val productWeight = request.body("productWeight")(0).toDouble
  	val productStock = request.body("productStock")(0).toInt
  	val productDescription = request.body("productDescription")(0)

  	//Connect to the database and run the create query
  	implicit val conn = db.getConnection()
  	val user = Product(None, productName, productPrice, productImageLocation, productColor, 
  		productMaterial, productDimensions, productWeight, productStock, 
  		productDescription).create
  	println("*********************************************************")
  	conn.close()
  	Ok(views.html.admin())
  }

}
