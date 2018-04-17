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
class ReviewController @Inject()(db: Database, cc: ControllerComponents) extends AbstractController(cc) {

  def create = Action(parse.formUrlEncoded) { implicit request =>
  	
  	//Extract all of the values from the from
  	val userId = request.body("userId")(0).toString
  	val productId = request.body("productId")(0).toString
  	val reviewText = request.body("reviewText")(0).toString

    //Run the bash script to use elasticsearch
  	val createScriptPath = "bash ../../scripts/createReview.sh " + userId + " " + productId + " " + reviewText

  	Process(createScriptPath).!

  	Ok(views.html.index("Thank you for your review"))
  }

  def deleteOne = Action (parse.formUrlEncoded) { implicit request =>

    //Extract all of the values from the form
    val reviewId = request.body("reviewId")(0)

    //Run the bash script to use elasticsearch
    val deleteScript = "bash ../../scripts/deleteReview.sh " + reviewId

    Process(deleteScript).!

    Ok(views.html.index("Review deleted"))
  }

}
