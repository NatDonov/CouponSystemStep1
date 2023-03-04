package tests;

import beans.Company;
import beans.Customer;
import security.ClientType;
import security.LoginManager;
import dao.*;
import exception.CouponSystemException;
import facade.*;


import java.sql.SQLException;
import java.util.List;

public class AdminTest {

    private AdminFacade adminFacade;

    private CompaniesDAO companiesDAO;
    private CouponsDAO couponsDAO;
    private CustomersDAO customersDAO;
    private static AdminTest instance = null;

    public static AdminTest getInstance() {
        if (instance == null) {
            synchronized (AdminTest.class) {
                if (instance == null) {
                    instance = new AdminTest();
                }
            }
        }
        return instance;
    }

    private AdminTest() {
        this.companiesDAO = new CompaniesDAOImpl();
        this.couponsDAO = new CouponsDAOImpl();
        this.customersDAO = new CustomersDAOImpl();
    }

    ;

    public void runAdminTests() {
        System.out.print(Color.GREEN_BOLD);
        System.out.println(" █████╗ ██████╗ ███╗   ███╗██╗███╗   ██╗    ████████╗███████╗███████╗████████╗███████╗\n" +
                "██╔══██╗██╔══██╗████╗ ████║██║████╗  ██║    ╚══██╔══╝██╔════╝██╔════╝╚══██╔══╝██╔════╝\n" +
                "███████║██║  ██║██╔████╔██║██║██╔██╗ ██║       ██║   █████╗  ███████╗   ██║   ███████╗\n" +
                "██╔══██║██║  ██║██║╚██╔╝██║██║██║╚██╗██║       ██║   ██╔══╝  ╚════██║   ██║   ╚════██║\n" +
                "██║  ██║██████╔╝██║ ╚═╝ ██║██║██║ ╚████║       ██║   ███████╗███████║   ██║   ███████║\n" +
                "╚═╝  ╚═╝╚═════╝ ╚═╝     ╚═╝╚═╝╚═╝  ╚═══╝       ╚═╝   ╚══════╝╚══════╝   ╚═╝   ╚══════╝\n" +
                "                                                                                      ");
        System.out.println(Color.RESET);


        adminLogin();
        System.out.println();
        addCompany();
        System.out.println();
        updateCompany();
        System.out.println();
        deleteCompany();
        System.out.println();
        getAllCompanies();
        System.out.println();
        getOneCompany();
        System.out.println();
        addCustomer();
        System.out.println();
        updateCustomer();
        System.out.println();
        deleteCustomer();
        System.out.println();
        getAllCustomers();
        System.out.println();
        getOneCustomer();
        System.out.println();

        ;
    }

    public void getOneCustomer() {
        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************GET ONE CUSTOMER*****************");
        System.out.println(Color.RESET);
        try {
            System.out.println(">>>>Succeed to get one customer<<<<");
            System.out.println(adminFacade.getOneCustomers(5));
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

        try {
            System.out.println("Failed to get one customer - customer not found");
            adminFacade.getOneCustomers(20);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
    }

    public void getAllCustomers() {
        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************GET ALL CUSTOMERS*****************");
        System.out.println(Color.RESET);
        try {
            System.out.println(">>>>Succeed to get all customers<<<<");
            List<Customer> customerList = adminFacade.getAllCustomers();
            for (Customer customer : customerList) {
                System.out.println(customer);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
    }

    public void deleteCustomer() {
        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************DELETE CUSTOMER*****************");
        System.out.println(Color.RESET);
        try {

            System.out.println(">>>>Succeed delete customer<<<<");
            Customer customerToDelete = adminFacade.getOneCustomers(2);
            adminFacade.deleteCustomer(customerToDelete.getId());
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

        try {
            System.out.println("Failed update customer - customer not found");
            Customer customerToDelete = adminFacade.getOneCustomers(20);
            adminFacade.deleteCustomer(customerToDelete.getId());
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
    }

    public void updateCustomer() {
        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************UPDATE CUSTOMER*****************");
        System.out.println(Color.RESET);
        try {
            System.out.println(Color.WHITE_BOLD);
            System.out.println("Customer before update: " + adminFacade.getOneCustomers(1));
            System.out.println(Color.RESET);
            System.out.println(">>>>Succeed update customer>>>>");
            Customer customerToUpdate = adminFacade.getOneCustomers(1);
            customerToUpdate.setFirstName("Guy");
            customerToUpdate.setLastName("Wolfson");
            customerToUpdate.setPassword("Guy1212");
            adminFacade.updateCustomer(1, customerToUpdate);
            System.out.println(Color.WHITE_BOLD);
            System.out.println("Customer after update: " + customerToUpdate);
            System.out.println(Color.RESET);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

        try {
            System.out.println("Failed to update customer - error email - the email is exists already");
            Customer customerToUpdate = adminFacade.getOneCustomers(1);
            customerToUpdate.setFirstName("Guy");
            customerToUpdate.setLastName("Wolfson");
            customerToUpdate.setPassword("Guy1212");
            customerToUpdate.setEmail("Adi@gmail.com");
            adminFacade.updateCustomer(1, customerToUpdate);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

        try {
            System.out.println("Failed to update customer - not found customer");
            Customer customerToUpdate = adminFacade.getOneCustomers(10);
            customerToUpdate.setFirstName("Liya");
            customerToUpdate.setLastName("Ben Moshe");
            adminFacade.updateCustomer(10, customerToUpdate);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

        try {
            System.out.println("Failed to update customer - error id");
            Customer customerToUpdate = adminFacade.getOneCustomers(2);
            customerToUpdate.setFirstName("Marina");
            adminFacade.updateCustomer(7, customerToUpdate);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
    }

    public void addCustomer() {
        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************ADD CUSTOMER*****************");
        System.out.println(Color.RESET);
        try {
            System.out.println(">>>>Succeed add customer>>>>");
            Customer customer = new Customer("Natali", "Donov", "Natali@gmail.com", "nat1234");
            Customer customer1 = new Customer("Moshe", "Levi", "Moshe@gmail.com", "mosh567");
            Customer customer2 = new Customer("Adi", "Halfon", "Adi@gmail.com", "adihal5566");
            Customer customer3 = new Customer("Simon", "Ganon", "Simon@gmail.com", "simon2233");
            Customer customer4 = new Customer("Lea", "Shriki", "Lea@gmail.com", "leashriki1");

            adminFacade.addCustomer(customer);
            adminFacade.addCustomer(customer1);
            adminFacade.addCustomer(customer2);
            adminFacade.addCustomer(customer3);
            adminFacade.addCustomer(customer4);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

        try {
            System.out.println("Failed add customer");
            Customer customer = new Customer("Viki", "Kol", "Natali@gmail.com", "1234");
            adminFacade.addCustomer(customer);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
    }

    public void getOneCompany() {
        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************GET ONE COMPANY*****************");
        System.out.println(Color.RESET);
        try {
            System.out.println(">>>>Succeed to get one company<<<<");
            System.out.println(adminFacade.getOneCompany(5));
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

        try {
            System.out.println("Failed to get one company - company not found");
            adminFacade.getOneCompany(20);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

    }

    public void getAllCompanies() {
        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************GET ALL COMPANIES*****************");
        System.out.println(Color.RESET);
        try {
            System.out.println(">>>>Succeed to get all companies<<<<");
            List<Company> companyList = adminFacade.getAllCompanies();
            for (Company company : companyList) {
                System.out.println(company);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
    }

    public void deleteCompany() {
        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************DELETE COMPANY*****************");
        System.out.println(Color.RESET);
        try {
            System.out.println(">>>>Succeed delete company<<<<");
            Company companyToDelete = adminFacade.getOneCompany(2);
            adminFacade.deleteCompany(companyToDelete.getId());
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

        try {
            System.out.println("Failed update company - company not found");
            Company companyToDelete = adminFacade.getOneCompany(20);
            adminFacade.deleteCompany(companyToDelete.getId());
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

    }

    public void updateCompany() {
        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************UPDATE COMPANY*****************");
        System.out.println(Color.RESET);
        try {
            System.out.println(Color.WHITE_BOLD);
            System.out.println("Company before update: " + adminFacade.getOneCompany(1));
            System.out.println(Color.RESET);
            System.out.println(">>>>Succeed update company<<<<");
            Company companyToUpdate = adminFacade.getOneCompany(1);
            companyToUpdate.setEmail("Amazon2@gmail.com");
            companyToUpdate.setPassword("4567");
            adminFacade.updateCompany(1, companyToUpdate);
            System.out.println(Color.WHITE_BOLD);
            System.out.println("Company after update: " + companyToUpdate);
            System.out.println(Color.RESET);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

        try {
            System.out.println("Failed update company - error not found");
            Company companyToUpdate = adminFacade.getOneCompany(20);
            companyToUpdate.setEmail("Amazon2@gmail.com");
            companyToUpdate.setPassword("4567");
            adminFacade.updateCompany(2, companyToUpdate);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

        try {
            System.out.println("Failed update company - error id");
            Company companyToUpdate = adminFacade.getOneCompany(1);
            companyToUpdate.setEmail("Amazon2@gmail.com");
            companyToUpdate.setPassword("4567");
            adminFacade.updateCompany(5, companyToUpdate);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

        try {
            System.out.println("Failed update company - error name");
            Company companyToUpdate = adminFacade.getOneCompany(1);
            companyToUpdate.setEmail("Amazon2@gmail.com");
            companyToUpdate.setPassword("4567");
            companyToUpdate.setName("Toto");
            adminFacade.updateCompany(1, companyToUpdate);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        try {
            System.out.println("Failed update company - error email - the email is exists already");
            Company companyToUpdate = adminFacade.getOneCompany(1);
            companyToUpdate.setEmail("Rami@gmail.com");
            companyToUpdate.setPassword("4567");
            adminFacade.updateCompany(1, companyToUpdate);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }


    }

    public void addCompany() {
        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************ADD COMPANY*****************");
        System.out.println(Color.RESET);
        try {
            System.out.println(">>>>Succeed add company<<<<");
            Company company = new Company("Amazon", "Amazon@gmail.com", "1234");
            Company company2 = new Company("Coca-Cola", "Cola@gmail.com", "5678");
            Company company3 = new Company("RamiLevi", "Rami@gmail.com", "rami123");
            Company company4 = new Company("Elite", "Elite@gmail.com", "elitbig");
            Company company5 = new Company("Nespresso", "Nespres@gmail.com", "netggg");
            Company company6 = new Company("Bezeq", "Bezeq@gmail.com", "bezeq1234");
            Company company7 = new Company("John Bryce", "Johnbryce@gmail.com", "john666");

            adminFacade.addCompany(company);
            adminFacade.addCompany(company2);
            adminFacade.addCompany(company3);
            adminFacade.addCompany(company4);
            adminFacade.addCompany(company5);
            adminFacade.addCompany(company6);
            adminFacade.addCompany(company7);


        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

        try {
            System.out.println("Failed add company - email error");
            Company company = new Company("eBay", "amazon@gmail.com", "1234");
            adminFacade.addCompany(company);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        try {
            System.out.println("Failed add company - name error");
            Company company = new Company("amazon", "ebay@gmail.com", "1234");
            adminFacade.addCompany(company);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

    }


    public void adminLogin() {
        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************ADMIN LOGIN*****************");
        System.out.println(Color.RESET);
        LoginManager loginManager = LoginManager.getInstance();
        try {
            System.out.println(">>>>Succeed login<<<<");
            adminFacade = (AdminFacade) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);

        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        try {
            System.out.println("Failed login - email is wrong");
            adminFacade = (AdminFacade) loginManager.login("admin@tuyiu.com", "rtruy", ClientType.ADMINISTRATOR);


        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }


    }
}

