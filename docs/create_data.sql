
CREATE USER 'repl'@'%' IDENTIFIED BY '@Zz123456';


DROP PROCEDURE IF EXISTS `add_data`;
DELIMITER ;;
CREATE PROCEDURE `add_data`()
BEGIN
    DECLARE i INT;
    DECLARE j INT;
    SET i=0;
    WHILE i<=100 DO
        SET j=1;
        WHILE j<100 DO
            INSERT INTO db(username) VALUES(CONCAT('²âÊÔ',CEILING(RAND()*900+100)));
            SET j = j + 1;
        END WHILE;
        SET i = i + 1;
    END WHILE;
END
;;


-- CALL add_data();

-- TRUNCATE TABLE db;
