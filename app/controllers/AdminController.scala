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
class AdminController @Inject()(db: Database, cc: ControllerComponents) extends AbstractController(cc) {

	def index = Action { implicit request =>
		if(request.cookies.get("admin").toString != "None") {
			if(request.cookies.get("admin").toList(0).value.toString == "true") {
				implicit val conn = db.getConnection()
				val allProducts = Product.returnAll
				val allUsers = User.returnAll
				conn.close()
				Ok(views.html.admin(allProducts, allUsers))
			} else {
				Ok(views.html.index("You are not authorized"))
			}
		} else {
			Ok(views.html.login())
		}
		
	}

}
