package dao;

import beans.CATEGORY;
import beans.Coupon;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface CouponsDAO extends DaoMain<Coupon, Integer> {
    void addCouponPurchase(int customerId, int couponId) throws SQLException;

    void deleteAllCouponPurchaseByCustomerId(int customerId) throws SQLException;

    void deleteAllCouponPurchaseByCouponId(int couponId) throws SQLException;

    void deleteAllCouponByCompaniesId(int companyId) throws SQLException;

    boolean isExistByTitleAndCompanyId(String title, int companyId, int couponId) throws SQLException;

    boolean isExistByTitleAndCompanyId(String title, int companyId) throws SQLException;


    boolean isExistByCustomerIdAndCouponId(int customerId, int couponId) throws SQLException;

    List<Coupon> getAllByCompanyId(int companyId) throws SQLException;

    List<Coupon> getAllByMaxPrice(int companyId, double price) throws SQLException;

    List<Coupon> getCouponByCategory(int companyId, CATEGORY category) throws SQLException;

    List<Coupon> getAllCouponByCustomerId(int customerId) throws SQLException;

    List<Coupon> getAllCouponByCustomerIdAndCategory(int customerId, CATEGORY category) throws SQLException;

    List<Coupon> getAllCouponByCustomerIdAndMaxPrice(int customerId, double price) throws SQLException;

    boolean isExistByCouponIdAndCompanyId(int couponId, int companyId) throws SQLException;

    List<Coupon> getAllCouponsExpired(Date date) throws SQLException;

    void deleteALLExpiredCoupon() throws SQLException;


}
