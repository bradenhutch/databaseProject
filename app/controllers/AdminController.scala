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
import sys.process._

@Singleton
class AdminController @Inject()(db: Database, cc: ControllerComponents) extends AbstractController(cc) {

	def index = Action { implicit request =>
		if(request.cookies.get("admin").toString != "None") {
			if(request.cookies.get("admin").toList(0).value.toString == "true") {
				implicit val conn = db.getConnection()
				val allProducts = Product.returnAll
				val allUsers = User.returnAll

				//Use the view and show the total amount of products
				val dirtyCount = Product.getCount

				//Regex to get the right count
				val cleanParen = "[()]".toSet
				val cleanAlpha = "List".toSet
				val productCount = dirtyCount.toString.filterNot(cleanParen).filterNot(cleanAlpha).toLong

				conn.close()

				//Run the python script to track logins
				val showLoginScript = "python scripts/showLogins.py"
				val result = Process(showLoginScript).!!
				val removeU = "u'".toSet
				val removeCommas = ",,".toSet
				val logins = result.toString.filterNot(removeU).filterNot(removeCommas)

				Ok(views.html.admin(allProducts, allUsers, productCount, logins))
			} else {
				Ok(views.html.index("You are not authorized"))
			}
		} else {
			Ok(views.html.login())
		}
		
	}

	def clearLogins = Action {
		val clearLoginScript = "python scripts/clearLogins.py"
		Process(clearLoginScript).!

		Ok(views.html.index("Login history cleared"))
	}

}
