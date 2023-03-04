package tests;

import beans.CATEGORY;
import beans.Coupon;
import security.ClientType;
import security.LoginManager;
import dao.CouponsDAO;
import dao.CouponsDAOImpl;
import dao.CustomersDAO;
import dao.CustomersDAOImpl;
import exception.CouponSystemException;
import facade.CustomerFacade;


import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CustomerTest {


    private CustomerFacade customerFacade;

    private CustomersDAO customersDAO;
    private CouponsDAO couponsDAO;

    private static CustomerTest instance = null;

    public static CustomerTest getInstance() {
        if (instance == null) {
            synchronized (CustomerTest.class) {
                if (instance == null) {
                    instance = new CustomerTest();
                }
            }
        }
        return instance;
    }

    private CustomerTest() {
        this.customersDAO = new CustomersDAOImpl();
        this.couponsDAO = new CouponsDAOImpl();
    }

    public void runCustomerTest() {
        System.out.print(Color.GREEN_BOLD);

        System.out.println(" ██████╗██╗   ██╗███████╗████████╗ ██████╗ ███╗   ███╗███████╗██████╗     ████████╗███████╗███████╗████████╗███████╗\n" +
                "██╔════╝██║   ██║██╔════╝╚══██╔══╝██╔═══██╗████╗ ████║██╔════╝██╔══██╗    ╚══██╔══╝██╔════╝██╔════╝╚══██╔══╝██╔════╝\n" +
                "██║     ██║   ██║███████╗   ██║   ██║   ██║██╔████╔██║█████╗  ██████╔╝       ██║   █████╗  ███████╗   ██║   ███████╗\n" +
                "██║     ██║   ██║╚════██║   ██║   ██║   ██║██║╚██╔╝██║██╔══╝  ██╔══██╗       ██║   ██╔══╝  ╚════██║   ██║   ╚════██║\n" +
                "╚██████╗╚██████╔╝███████║   ██║   ╚██████╔╝██║ ╚═╝ ██║███████╗██║  ██║       ██║   ███████╗███████║   ██║   ███████║\n" +
                " ╚═════╝ ╚═════╝ ╚══════╝   ╚═╝    ╚═════╝ ╚═╝     ╚═╝╚══════╝╚═╝  ╚═╝       ╚═╝   ╚══════╝╚══════╝   ╚═╝   ╚══════╝\n" +
                "                                                                                                                    ");
        System.out.println(Color.RESET);


        customerLogin();
        System.out.println();
        purchaseCoupon();
        System.out.println();
        getCustomerCoupons();
        System.out.println();
        getCustomerCouponsByCategory();
        System.out.println();
        getCustomerCouponsByMaxPrice();
        System.out.println();
        getCustomerDetails();
        System.out.println();


    }

    public void getCustomerDetails() {
        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************GET CUSTOMER DETAILS*****************");
        System.out.println(Color.RESET);
        try {
            System.out.println(customerFacade.getCustomerDetails());
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
    }

    public void getCustomerCouponsByMaxPrice() {
        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************GET CUSTOMER COUPONS BY MAX PRICE(200)*****************");
        System.out.println(Color.RESET);
        try {
            List<Coupon> couponList = customerFacade.getCustomerCouponByMaxPrice(200);
            for (Coupon coupon : couponList) {
                System.out.println(coupon + "\n");
            }

        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
    }

    public void getCustomerCouponsByCategory() {
        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************GET CUSTOMER COUPONS BY CATEGORY(FOOD)*****************");
        System.out.println(Color.RESET);
        try {
            List<Coupon> couponList = customerFacade.getCustomerCouponByCategory(CATEGORY.FOOD);
            for (Coupon coupon : couponList) {
                System.out.println(coupon);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
    }


    public void getCustomerCoupons() {
        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************GET CUSTOMER COUPONS*****************");
        System.out.println(Color.RESET);
        try {
            List<Coupon> couponList = customerFacade.getCustomerCoupon();
            for (Coupon coupon : couponList) {
                System.out.println(coupon + "\n");
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
    }


    public void purchaseCoupon() {
        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************PURCHASE COUPON*****************");
        System.out.println(Color.RESET);
        try {
            System.out.println(">>>>Succeed purchase coupons<<<<");
            Coupon coupon = couponsDAO.getSingle(3);
            customerFacade.purchaseCoupon(coupon);
            Coupon coupon2 = couponsDAO.getSingle(7);
            customerFacade.purchaseCoupon(coupon2);
            Coupon coupon3 = couponsDAO.getSingle(11);
            customerFacade.purchaseCoupon(coupon3);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        try {
            System.out.println("Failed purchase coupon - expiry date has passed");
            couponsDAO.add(new Coupon(9, CATEGORY.ELECTRICITY, "Abc", "Connect to internet and get a router free", Date.valueOf(LocalDate.now().minusMonths(2)), Date.valueOf(LocalDate.now().minusMonths(1)), 2, 100, "abc"));
            Coupon coupon = couponsDAO.getSingle(12);
            customerFacade.purchaseCoupon(coupon);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        try {
            System.out.println("Failed purchase coupon - coupons are out of stock");
            Coupon coupon = couponsDAO.getSingle(5);
            customerFacade.purchaseCoupon(coupon);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

    }

    public void customerLogin() {
        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************CUSTOMER LOGIN*****************");
        System.out.println(Color.RESET);
        LoginManager loginManager = LoginManager.getInstance();
        try {
            System.out.println(">>>>Succeed login<<<<");
            customerFacade = (CustomerFacade) loginManager.login("Moshe@gmail.com", "mosh567", ClientType.CUSTOMER);

        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

        try {
            System.out.println("Failed login - email is wrong");
            customerFacade = (CustomerFacade) loginManager.login("Bezeq@gmail.com", "mosh567", ClientType.CUSTOMER);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

        try {
            System.out.println("Failed login - password is wrong");
            customerFacade = (CustomerFacade) loginManager.login("Simon@gmail.com", "bezeq1234", ClientType.CUSTOMER);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
    }
}
