package tests;

import beans.CATEGORY;
import beans.Coupon;
import security.ClientType;
import security.LoginManager;
import dao.CouponsDAO;
import dao.CouponsDAOImpl;
import exception.CouponSystemException;
import facade.CompanyFacade;

import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

public class CompanyTest {

    private CompanyFacade companyFacade;

    private CouponsDAO couponsDAO;

    private static CompanyTest instance = null;

    public static CompanyTest getInstance() {
        if (instance == null) {
            synchronized (CompanyTest.class) {
                if (instance == null) {
                    instance = new CompanyTest();
                }
            }
        }
        return instance;
    }

    private CompanyTest() {
        this.couponsDAO = new CouponsDAOImpl();
    }

    ;


    public void runCompanyTest() {
        System.out.print(Color.GREEN_BOLD);

        System.out.println(" ██████╗ ██████╗ ███╗   ███╗██████╗  █████╗ ███╗   ██╗██╗   ██╗    ████████╗███████╗███████╗████████╗███████╗\n" +
                "██╔════╝██╔═══██╗████╗ ████║██╔══██╗██╔══██╗████╗  ██║╚██╗ ██╔╝    ╚══██╔══╝██╔════╝██╔════╝╚══██╔══╝██╔════╝\n" +
                "██║     ██║   ██║██╔████╔██║██████╔╝███████║██╔██╗ ██║ ╚████╔╝        ██║   █████╗  ███████╗   ██║   ███████╗\n" +
                "██║     ██║   ██║██║╚██╔╝██║██╔═══╝ ██╔══██║██║╚██╗██║  ╚██╔╝         ██║   ██╔══╝  ╚════██║   ██║   ╚════██║\n" +
                "╚██████╗╚██████╔╝██║ ╚═╝ ██║██║     ██║  ██║██║ ╚████║   ██║          ██║   ███████╗███████║   ██║   ███████║\n" +
                " ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝   ╚═╝          ╚═╝   ╚══════╝╚══════╝   ╚═╝   ╚══════╝\n" +
                "                                                                                                             ");
        System.out.println(Color.RESET);

        companyLogin();
        System.out.println();
        addCoupon();
        System.out.println();
        updateCoupon();
        System.out.println();
        deleteCoupon();
        System.out.println();
        getCompanyCoupons();
        System.out.println();
        getCompanyCouponsByCategory();
        System.out.println();
        getCompanyCouponsByMaxPrice();
        System.out.println();
        getCompanyDetails();
        System.out.println();


    }

    public void getCompanyDetails() {
        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************GET COMPANY DETAILS*****************");
        System.out.println(Color.RESET);
        try {

            System.out.println(companyFacade.getCompanyDetails());
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
    }


    public void getCompanyCouponsByMaxPrice() {
        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************GET COMPANY COUPONS BY MAX(100) PRICE*****************");
        System.out.println(Color.RESET);
        try {
            List<Coupon> couponList = companyFacade.getCompanyCouponsByMaxPrice(100);
            System.out.println("Size: " + couponList.size());
            for (Coupon coupon : couponList) {
                System.out.println(coupon + "\n");
            }

        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
    }

    public void getCompanyCouponsByCategory() {
        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************GET COMPANY COUPONS BY CATEGORY(FOOD)*****************");
        System.out.println(Color.RESET);
        try {
            List<Coupon> couponList = companyFacade.getCompanyCouponsByCategory(CATEGORY.FOOD);
            System.out.println("Size: " + couponList.size());
            for (Coupon coupon : couponList) {
                System.out.println(coupon + "\n");
            }

        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
    }


    public void getCompanyCoupons() {
        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************GET COMPANY COUPONS*****************");
        System.out.println(Color.RESET);
        try {
            List<Coupon> couponList = companyFacade.getCompanyCoupons();
            System.out.println("Size: " + couponList.size());
            for (Coupon coupon : couponList) {
                System.out.println(coupon + "\n");
            }

        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
    }

    public void deleteCoupon() {
        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************DELETE COUPON*****************");
        System.out.println(Color.RESET);

        try {
            System.out.println(">>>>Succeed to delete coupon>>>>");
            companyFacade.deleteCoupon(8);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        try {
            System.out.println("Failed to delete coupon - not your coupon");

            companyFacade.deleteCoupon(3);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        try {
            System.out.println("Failed to delete coupon - not found coupon");
            companyFacade.deleteCoupon(20);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

    }

    public void updateCoupon() {
        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************UPDATE COUPON*****************");
        System.out.println(Color.RESET);
        try {
            System.out.println(Color.WHITE_BOLD);
            System.out.println("Coupon before update: " + couponsDAO.getSingle(9));
            System.out.println(Color.RESET);
            System.out.println(">>>>Succeed update coupon<<<<");
            Coupon couponToUpdate = couponsDAO.getSingle(9);
            couponToUpdate.setDescription("New description");
            couponToUpdate.setEndDate(Date.valueOf(couponToUpdate.getEndDate().toLocalDate().minusMonths(2)));
            companyFacade.updateCoupon(9, couponToUpdate);
            System.out.println(Color.WHITE_BOLD);
            System.out.println("Coupon after update: " + couponToUpdate);
            System.out.println(Color.RESET);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        try {
            System.out.println("Failed to update coupon - not your coupon");
            Coupon couponToUpdate = couponsDAO.getSingle(3);
            couponToUpdate.setTitle("Big sale on toys22222");
            couponToUpdate.setDescription("Buy two, get free22222");
            couponToUpdate.setAmount(5);
            companyFacade.updateCoupon(3, couponToUpdate);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        try {
            System.out.println("Failed to update coupon - error company-id");
            Coupon couponToUpdate = couponsDAO.getSingle(10);
            couponToUpdate.setCompanyId(3);
            companyFacade.updateCoupon(10, couponToUpdate);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        try {
            System.out.println("Failed to update coupon - error coupon-id");
            Coupon couponToUpdate = couponsDAO.getSingle(10);
            couponToUpdate.setId(5);
            companyFacade.updateCoupon(10, couponToUpdate);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

        try {
            System.out.println("Failed to update coupon - error title");
            Coupon couponToUpdate = couponsDAO.getSingle(10);
            couponToUpdate.setTitle("Router222");
            companyFacade.updateCoupon(10, couponToUpdate);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

    }

    public void addCoupon() {

        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************ADD COUPON*****************");
        System.out.println(Color.RESET);
        try {
            System.out.println(">>>>Succeed add coupon<<<<");
            Coupon coupon = new Coupon(9, CATEGORY.getCategoryById(2), "Big Sale - Laptop", "Bay a laptop, get a charger as a gift ", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(2)), 5, 200, "" +
                    "| ___ |\n" +
                    "  ||   ||  J.O.\n" +
                    "  ||___||\n" +
                    "  |   _ |\n" +
                    "  |_____|\n" +
                    " /_/_|_\\_\\----.\n" +
                    "/_/__|__\\_\\   )\n" +
                    "             (\n" +
                    "             []");
            Coupon coupon1 = new Coupon(9, CATEGORY.FOOD, "1+1", "Buy two for the price of one ", Date.valueOf(LocalDate.now().minusMonths(2)), Date.valueOf(LocalDate.now().plusMonths(1)), 0, 20, " _                                   \n" +
                    "          .!.!.                             \n" +
                    "           ! !                                   \n" +
                    "           ; :                                \n" +
                    "          ;   :                                \n" +
                    "         ;_____:                                 \n" +
                    "         ! Coca!                                 \n" +
                    "         !_____!                                 \n" +
                    "         :     :\n" +
                    "         :     ;                                       \n" +
                    "         .'   '.                             \n" +
                    "         :     :  k&n                           \n" +
                    "          '''''");

            Coupon coupon2 = new Coupon(9, CATEGORY.COURSES, "Hottest sale", " Buy a course, get the best practitioner as a gift", Date.valueOf(LocalDate.now().minusMonths(5)), Date.valueOf(LocalDate.now().plusMonths(2)), 5, 24_000, "" +
                    "  _____\n" +
                    " _/ _ _ \\_  \n" +
                    "(o / | \\ o)\n" +
                    " || o|o ||\n" +
                    " | \\_|_/ |\n" +
                    " |  ___  |\n" +
                    " | (___) |\n" +
                    " |\\_____/|\n" +
                    " | \\___/ |\n" +
                    " \\       /\n" +
                    "  \\__ __/\n" +
                    "     U");

            Coupon coupon3 = new Coupon(9, CATEGORY.TOYS, "Big sale on toys", "Buy two, get free", Date.valueOf(LocalDate.now().minusMonths(5)), Date.valueOf(LocalDate.now().plusMonths(1)), 3, 350, "" +
                    "(\".___.\")\n" +
                    "  _ { + } _\n" +
                    "()__|>o<|__()\n" +
                    "   (  .  )\n" +
                    " (_)     (_)");

            Coupon coupon4 = new Coupon(9, CATEGORY.ELECTRICITY, "Router", "Connect to internet and get a router free", Date.valueOf(LocalDate.now().minusMonths(1)), Date.valueOf(LocalDate.now().plusMonths(2)), 2, 100, "router");
            Coupon coupon5 = new Coupon(9, CATEGORY.VACATION, "Router222", "Connect to internet and get a router free222", Date.valueOf(LocalDate.now().minusMonths(1)), Date.valueOf(LocalDate.now().plusMonths(2)), 2, 200, "router");
            Coupon coupon6 = new Coupon(9, CATEGORY.FOOD, "Router333", "Connect to internet and get a router free333", Date.valueOf(LocalDate.now().minusMonths(1)), Date.valueOf(LocalDate.now().plusMonths(2)), 2, 100, "router");

            companyFacade.addCoupon(coupon);
            companyFacade.addCoupon(coupon1);
            companyFacade.addCoupon(coupon2);
            companyFacade.addCoupon(coupon3);
            companyFacade.addCoupon(coupon4);
            companyFacade.addCoupon(coupon5);
            companyFacade.addCoupon(coupon6);

        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

        try {
            System.out.println("Failed add coupon - title error");

            Coupon coupon1 = new Coupon(9, CATEGORY.FOOD, "1+1", "Buy two for the price of one ", Date.valueOf(LocalDate.now().minusMonths(2)), Date.valueOf(LocalDate.now().plusMonths(1)), 2, 20, " _                                   \n" +
                    "          .!.!.                             \n" +
                    "           ! !                                   \n" +
                    "           ; :                                \n" +
                    "          ;   :                                \n" +
                    "         ;_____:                                 \n" +
                    "         ! Coca!                                 \n" +
                    "         !_____!                                 \n" +
                    "         :     :\n" +
                    "         :     ;                                       \n" +
                    "         .'   '.                             \n" +
                    "         :     :  k&n                           \n" +
                    "          '''''");

            companyFacade.addCoupon(coupon1);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

        try {
            System.out.println(">>>>Succeed add coupon - with title like other company's coupon<<<<");

            Coupon coupon1 = new Coupon(9, CATEGORY.FOOD, "title 3", "Buy two for the price of one ", Date.valueOf(LocalDate.now().minusMonths(2)), Date.valueOf(LocalDate.now().plusMonths(1)), 2, 20, " _                                   \n" +
                    "          .!.!.                             \n" +
                    "           ! !                                   \n" +
                    "           ; :                                \n" +
                    "          ;   :                                \n" +
                    "         ;_____:                                 \n" +
                    "         ! Coca!                                 \n" +
                    "         !_____!                                 \n" +
                    "         :     :\n" +
                    "         :     ;                                       \n" +
                    "         .'   '.                             \n" +
                    "         :     :  k&n                           \n" +
                    "          '''''");


            companyFacade.addCoupon(coupon1);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

        try {
            System.out.println("Failed add coupon - not my company");
            Coupon coupon = new Coupon(3, CATEGORY.VACATION, "Router222", "Connect to internet and get a router free222", Date.valueOf(LocalDate.now().minusMonths(1)), Date.valueOf(LocalDate.now().plusMonths(2)), 2, 200, "router");
            companyFacade.addCoupon(coupon);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

    }


    public void companyLogin() {
        System.out.print(Color.BLUE_BOLD);
        System.out.println("*****************COMPANY LOGIN*****************");
        System.out.println(Color.RESET);
        LoginManager loginManager = LoginManager.getInstance();
        try {
            System.out.println(">>>>Succeed login<<<<");
            companyFacade = (CompanyFacade) loginManager.login("Bezeq@gmail.com", "bezeq1234", ClientType.COMPANY);

        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

        try {
            System.out.println("Failed login - password is wrong");
            companyFacade = (CompanyFacade) loginManager.login("Bezeq@gmail.com", "bezeq567", ClientType.COMPANY);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }

        try {
            System.out.println("Failed login - email is wrong");
            companyFacade = (CompanyFacade) loginManager.login("Mezeq@gmail.com", "bezeq1234", ClientType.COMPANY);
        } catch (CouponSystemException couponSystemException) {
            System.out.println(couponSystemException);
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }


    }

}
