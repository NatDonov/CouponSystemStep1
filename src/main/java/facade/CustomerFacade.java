package facade;

import beans.CATEGORY;
import beans.Coupon;
import beans.Customer;
import exception.CouponSystemException;

import java.sql.SQLException;
import java.util.List;

public interface CustomerFacade {

    boolean login(String email, String password) throws SQLException, CouponSystemException;

    void purchaseCoupon(Coupon coupon) throws CouponSystemException, SQLException;

    List<Coupon> getCustomerCoupon() throws SQLException;

    List<Coupon> getCustomerCouponByCategory(CATEGORY category) throws SQLException;

    List<Coupon> getCustomerCouponByMaxPrice(double maxPrice) throws SQLException;

    Customer getCustomerDetails() throws SQLException;
}
