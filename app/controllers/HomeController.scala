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
import org.apache.commons.codec.digest.DigestUtils
import sys.process._

@Singleton
class HomeController @Inject()(db: Database, cc: ControllerComponents) extends AbstractController(cc) {

	def index = Action {
		Ok(views.html.index("Chairs 4 Days Homepage"))
	}

	def login = Action {
		Ok(views.html.login())
	}

	def postLogin = Action(parse.urlFormEncoded) { implicit request =>

		val username = request.body("username")(0)
		val password = DigestUtils.sha256Hex(request.body("password")(0))
		//The script to track logins in mongodb
		val addLoginScript = "python scripts/addLogin.py " + username

		implicit val conn = db.getConnection()
		val loginAttempt = User.returnOneByUserPass(conn, username, password)
		if(loginAttempt.toString != "List()") {
			if(loginAttempt(0).admin.toString == "true") {
				//Run the add script
				Process(addLoginScript).!
				Ok(views.html.index("Login successful with admin privileges"))
					.withCookies(Cookie("admin","true", Option(86400)), 
						Cookie("username", username), Cookie("userId", loginAttempt(0).Id.toString)).bakeCookies()
			} else {
				//Run the add script
				Process(addLoginScript).!
				Ok(views.html.index("Login successful with basic privileges"))
					.withCookies(Cookie("admin","false", Option(86400)), 
						Cookie("username", username), Cookie("userId", loginAttempt(0).Id.toString)).bakeCookies()
			}	
		} else {
			Ok(views.html.index("Login failed"))
		}
	}

	def logout = Action {
		Ok(views.html.index("Logout successful")).discardingCookies(DiscardingCookie("admin"), 
			DiscardingCookie("username"), DiscardingCookie("userId"))
	}

}
