package facade;

import beans.CATEGORY;
import beans.Company;
import beans.Coupon;
import exception.CouponSystemException;

import java.sql.SQLException;
import java.util.List;

public interface CompanyFacade {

    boolean login(String email, String password) throws CouponSystemException, SQLException;

    void addCoupon(Coupon coupon) throws SQLException, CouponSystemException;

    void updateCoupon(int couponId, Coupon coupon) throws SQLException, CouponSystemException;

    void deleteCoupon(int couponId) throws SQLException, CouponSystemException;

    List<Coupon> getCompanyCoupons() throws SQLException;

    List<Coupon> getCompanyCouponsByCategory(CATEGORY category) throws SQLException, CouponSystemException;

    List<Coupon> getCompanyCouponsByMaxPrice(double maxPrice) throws SQLException;

    Company getCompanyDetails() throws SQLException;




}
