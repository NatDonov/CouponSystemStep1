package tests;

import beans.CATEGORY;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import dao.*;
import db.DatabaseManager;
import thread.Job;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class AllTest {

    public static void runAllTest() throws SQLException {
        DatabaseManager.dropAndCreateStrategy();
        init();
        Job job = new Job();
        Thread dailyJob = new Thread(job);
        dailyJob.start();
        AdminTest.getInstance().runAdminTests();
        CompanyTest.getInstance().runCompanyTest();
        CustomerTest.getInstance().runCustomerTest();
        job.stopRun();
    }

    public static void init() {
        CompaniesDAO companiesDAO = new CompaniesDAOImpl();
        CustomersDAO customersDAO = new CustomersDAOImpl();
        CouponsDAO couponsDAO = new CouponsDAOImpl();
        try {
            companiesDAO.add(new Company("Company1", "company1@gmail.com", "com1"));
            companiesDAO.add(new Company("Company2", "company2@gmail.com", "com2"));
            companiesDAO.add(new Company("Company3", "company3@gmail.com", "com3"));
            couponsDAO.add(new Coupon(1, CATEGORY.getCategoryById(1), "title 1", "Bay a laptop, get a charger as a gift ", Date.valueOf(LocalDate.now().minusMonths(2)), Date.valueOf(LocalDate.now().minusMonths(1)), 5, 200, "img1"));
            couponsDAO.add(new Coupon(2, CATEGORY.getCategoryById(2), "title 2", "Bay a laptop, get a charger as a gift ", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(2)), 5, 200, "img2"));
            couponsDAO.add(new Coupon(3, CATEGORY.getCategoryById(3), "title 3", "Bay a laptop, get a charger as a gift ", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(2)), 5, 200, "img3"));
            customersDAO.add(new Customer("f1", "l1", "customer1@gmail.com", "1234"));
            customersDAO.add(new Customer("f2", "l2", "customer2@gmail.com", "1234"));
            customersDAO.add(new Customer("f3", "l32", "customer3@gmail.com", "1234"));

        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

    }


}
