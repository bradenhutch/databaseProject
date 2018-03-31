#TRIGGER
DELIMITER 
//
CREATE TRIGGER priceCheck
BEFORE INSERT ON product
FOR EACH ROW IF NEW.price < 0 THEN SET NEW.price = 0;
END IF;
//
DELIMITER

#VIEW
CREATE VIEW totalProducts AS
	SELECT COUNT(*) FROM product;

#PROCEDURE
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `orderTotals`(IN `orderId` INT)
    NO SQL
UPDATE orders SET tax = (subtotal * 0.07), total = (subtotal + tax)
WHERE id = orderId
//


#Give permissions to TestGuy
GRANT EXECUTE ON PROCEDURE databaseProject.orderTotals TO 'TestGuy'@'%';
FLUSH PRIVILEGES;