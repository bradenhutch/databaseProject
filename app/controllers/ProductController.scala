package controllers

import javax.inject._
import play.api.data._
import play.api.data.{Mapping, Form}
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db._
import play.api.mvc._
import anorm._
import models._


@Singleton
case class ProductController @Inject()(db: Database, cc: ControllerComponents) extends AbstractController(cc) {

	//Create constraints for the product form
	val productForm = Form(
		mapping(
			"id" -> optional(longNumber),
			"productName" -> nonEmptyText,
			"productPrice" -> bigDecimal,
			"productImageLocation" -> nonEmptyText,
			"productColor" -> nonEmptyText,
			"productMaterial" -> nonEmptyText,
			"productDimensions" -> nonEmptyText,
			"productWeight" -> bigDecimal,
			"productStock" -> number(min = 0, max = 1000000),
			"productDescription" -> nonEmptyText)(Product.apply)(Product.unapply))

  	def create = Action(parse.urlFormEncoded) { implicit request =>

		//Extract all of the values from the form
		val productName = request.body("productName")(0)
		val productPriceString = request.body("productPrice")(0) //.toDouble
		val productImageLocation = request.body("productImageLocation")(0)
		val productColor = request.body("productColor")(0)
		val productMaterial = request.body("productMaterial")(0)
		val productDimensions = request.body("productDimensions")(0)
		val productWeightString = request.body("productWeight")(0) //.toDouble
		val productStockString = request.body("productStock")(0) //.toInt
		val productDescription = request.body("productDescription")(0)

		//Bind the form and evaluate it
		val processedForm = productForm.bindFromRequest
		processedForm.fold(hasErrors => {
			println("Why do you try to break my products??!?!?")
			Ok(views.html.index("Bad input..."))
		},
		success => {
			//Convert values now that it is safe
			val productPrice = productPriceString.toDouble 
			val productWeight = productWeightString.toDouble
			val productStock = productStockString.toInt
			//Connect to the database and run the create query
			implicit val conn = db.getConnection()
			val user = Product(None, productName, productPrice, productImageLocation, productColor, 
			productMaterial, productDimensions, productWeight, productStock, 
			productDescription).create
			println("*********************************************************")
			conn.close()
			Ok("Product registered.")
		})
		
		Redirect(routes.AdminController.index)
	}

}
