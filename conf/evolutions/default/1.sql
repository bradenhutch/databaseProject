# --- !Ups

CREATE TABLE user (
	Id				BIGINT NOT NULL AUTO_INCREMENT,
	username		VARCHAR(255) NOT NULL,
	phoneNumber		VARCHAR(255) NOT NULL,
	name			VARCHAR(255) NOT NULL,
	email			VARCHAR(255) NOT NULL,
	password		VARCHAR(255) NOT NULL,
	gender			VARCHAR(255) NOT NULL,
	admin			BOOLEAN NOT NULL,
	PRIMARY KEY(Id)
);

CREATE TABLE payment_method (
	Id				BIGINT NOT NULL AUTO_INCREMENT,
	userId			VARCHAR(255) NOT NULL,
	PRIMARY KEY(Id)
);

CREATE TABLE paypal (
	Id				BIGINT NOT NULL AUTO_INCREMENT,
	paymentId		VARCHAR(255) NOT NULL,
	paypalEmail		VARCHAR(255) NOT NULL,
	PRIMARY KEY(Id)
);

CREATE TABLE credit_card (
	Id				BIGINT NOT NULL AUTO_INCREMENT,
	paymentId		VARCHAR(255) NOT NULL,
	cardNumber		VARCHAR(255) NOT NULL,
	expirationDate	VARCHAR(255) NOT NULL,
	CVV				VARCHAR(255) NOT NULL,
	PRIMARY KEY(Id)
);

CREATE TABLE address (
	Id				BIGINT NOT NULL AUTO_INCREMENT,
	userId			VARCHAR(255) NOT NULL,
	streetNumber	VARCHAR(255) NOT NULL,
	zipCode			VARCHAR(255) NOT NULL,
	city			VARCHAR(255) NOT NULL,
	PRIMARY KEY(Id)
);

CREATE TABLE orders (
	Id				BIGINT NOT NULL AUTO_INCREMENT,
	userId			VARCHAR(255) NOT NULL,
	shippingAddId	VARCHAR(255) NOT NULL,
	billingAddId	VARCHAR(255) NOT NULL,
	createdDate		VARCHAR(255) NOT NULL,
	shippedDate		VARCHAR(255) NOT NULL,
	subtotal		DOUBLE NOT NULL,
	tax				DOUBLE NOT NULL,
	total			DOUBLE NOT NULL,
	PRIMARY KEY(Id)
);

CREATE TABLE product (
	Id				BIGINT NOT NULL AUTO_INCREMENT,
	price			DOUBLE NOT NULL,
	imageLocation	VARCHAR(255) NOT NULL,
	color			VARCHAR(255) NOT NULL,
	material		VARCHAR(255) NOT NULL,
	dimensions		VARCHAR(255) NOT NULL,
	weight			VARCHAR(255) NOT NULL,
	currentStock	INT NOT NULL,
	description		TEXT NOT NULL,
	PRIMARY KEY(Id)
);

CREATE TABLE review (
	Id				BIGINT NOT NULL AUTO_INCREMENT,
	productId		VARCHAR(255) NOT NULL,
	reviewDate		VARCHAR(255) NOT NULL,
	author			VARCHAR(255) NOT NULL,
	reviewText		TEXT NOT NULL,
	PRIMARY KEY(Id)
);

CREATE TABLE order_products (
	Id				VARCHAR(255) NOT NULL,
	productId		VARCHAR(255) NOT NULL,
	quantity		VARCHAR(255) NOT NULL,
	PRIMARY KEY(Id)
);

# --- !Downs

DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS payment_method;
DROP TABLE IF EXISTS paypal;
DROP TABLE IF EXISTS credit_card;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS order_products;
