package db;

import beans.CATEGORY;
import dao.CategoryDAO;
import dao.CategoryDAOImpl;

import java.sql.SQLException;


public class DatabaseManager {

    public static CategoryDAO categoryDAO = new CategoryDAOImpl();

    public static final String url = "jdbc:mysql://localhost:3306?createDatabaseIfNotExist=TRUE&useTimezone=TRUE&serverTimezone=UTC";

    public static final String username = "root";

    public static final String password = "1234";

    public static final String CREATE_SCHEMA = "create schema `project_coupons`";

    public static final String DROP_SCHEMA = "drop schema `project_coupons`";

    private static final String CREATE_TABLE_COMPANIES = "CREATE TABLE `project_coupons`.`companies` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  `email` VARCHAR(45) NOT NULL,\n" +
            "  `password` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`));\n";

    private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE `project_coupons`.`customers` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `first_name` VARCHAR(45) NOT NULL,\n" +
            "  `last_name` VARCHAR(45) NOT NULL,\n" +
            "  `email` VARCHAR(45) NOT NULL,\n" +
            "  `password` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`));\n";


    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE `project_coupons`.`categories` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`));";

    private static final String CREATE_TABLE_COUPONS = "CREATE TABLE `project_coupons`.`coupons` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `company_id` INT NOT NULL,\n" +
            "  `category_id` INT NOT NULL,\n" +
            "  `title` VARCHAR(256) NOT NULL,\n" +
            "  `description` VARCHAR(256) NOT NULL,\n" +
            "  `start_date` DATE NOT NULL,\n" +
            "  `end_date` DATE NOT NULL,\n" +
            "  `amount` INT NOT NULL,\n" +
            "  `price` DOUBLE NOT NULL,\n" +
            "  `image` VARCHAR(1000) NOT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  INDEX `company_id_idx` (`company_id` ASC) VISIBLE,\n" +
            "  INDEX `category_id_idx` (`category_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `company_id`\n" +
            "    FOREIGN KEY (`company_id`)\n" +
            "    REFERENCES `project_coupons`.`companies` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `category_id`\n" +
            "    FOREIGN KEY (`category_id`)\n" +
            "    REFERENCES `project_coupons`.`categories` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);\n";

    private static final String CREATE_TABLE_CUSTOMERS_AND_COUPONS = "CREATE TABLE `project_coupons`.`customers_coupons` (\n" +
            "  `customers_id` INT NOT NULL,\n" +
            "  `coupon_id` INT NOT NULL,\n" +
            "  PRIMARY KEY (`customers_id`, `coupon_id`),\n" +
            "  INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `customer_id`\n" +
            "    FOREIGN KEY (`customers_id`)\n" +
            "    REFERENCES `project_coupons`.`customers` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `coupon_id`\n" +
            "    FOREIGN KEY (`coupon_id`)\n" +
            "    REFERENCES `project_coupons`.`coupons` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);";


    public static void dropAndCreateStrategy() throws SQLException {
        JDBCUtils.runQuery(DROP_SCHEMA);
        JDBCUtils.runQuery(CREATE_SCHEMA);
        JDBCUtils.runQuery(CREATE_TABLE_CATEGORIES);
        JDBCUtils.runQuery(CREATE_TABLE_COMPANIES);
        JDBCUtils.runQuery(CREATE_TABLE_COUPONS);
        JDBCUtils.runQuery(CREATE_TABLE_CUSTOMERS);
        JDBCUtils.runQuery(CREATE_TABLE_CUSTOMERS_AND_COUPONS);


        for (CATEGORY category : CATEGORY.values()) {

            String name = category.name();
            categoryDAO.addCategory(name);
        }
    }
}



