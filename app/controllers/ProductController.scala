package controllers

import javax.inject._
import play.api.db._
import play.api.mvc._
import anorm._
import models._


@Singleton
class ProductController @Inject()(db: Database, cc: ControllerComponents) extends AbstractController(cc) {

  def create = Action(parse.urlFormEncoded) { implicit request =>
  	val productPrice = request.body("productPrice")(0).toDouble
  	val productImageLocation = request.body("productImageLocation")(0)
  	val productColor = request.body("productColor")(0)
  	val productMaterial = request.body("productMaterial")(0)
  	val productDimensions = request.body("productDimensions")(0)
  	val productWeight = request.body("productWeight")(0).toDouble
  	val productStock = request.body("productStock")(0).toInt
  	val productDescription = request.body("productDescription")(0)

  	implicit val conn = db.getConnection()
  	val user = Product(None, productPrice, productImageLocation, productColor, 
  		productMaterial, productDimensions, productWeight, productStock, 
  		productDescription).create
  	println("*********************************************************")
  	conn.close()
  	Ok
  }

}
