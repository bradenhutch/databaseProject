#Trigger and view

DELIMITER //
CREATE TRIGGER priceCheck
BEFORE INSERT ON product
FOR EACH ROW IF NEW.price < 0 THEN SET NEW.price = 0;
END IF;//

CREATE VIEW totalProducts AS
	SELECT COUNT(*) FROM product;

