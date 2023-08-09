CREATE USER 'springboot'@'localhost' IDENTIFIED BY 'springboot';
FLUSH PRIVILEGES;
-- GRANT ALL PRIVILEGE ON database.table TO 'username'@'host';
GRANT ALL PRIVILEGES ON *.* TO 'username'@'host' IDENTIFIED BY 'springboot';