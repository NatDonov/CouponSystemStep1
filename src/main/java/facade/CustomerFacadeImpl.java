package facade;

import beans.CATEGORY;
import beans.Coupon;
import beans.Customer;
import exception.CouponSystemException;
import exception.ERRORMSG;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CustomerFacadeImpl extends ClientFacade implements CustomerFacade {

    private int customerId;

    public CustomerFacadeImpl() {
    }


    @Override
    public boolean login(String email, String password) throws SQLException, CouponSystemException {
        if (!(customersDAO.isExistByEmailAndPassword(email, password))) {
            throw new CouponSystemException(ERRORMSG.CUSTOMER_EMAIL_OR_PASSWORD);
        }
        this.customerId = customersDAO.getCustomerIdByEmail(email);
        return true;
    }

    @Override
    public void purchaseCoupon(Coupon coupon) throws CouponSystemException, SQLException {
        if (!(couponsDAO.isExist(coupon.getId()))) {
            throw new CouponSystemException(ERRORMSG.NOT_EXIST_COUPON);
        }
        if (coupon.getAmount() == 0) {
            throw new CouponSystemException(ERRORMSG.COUPON_OUT_OF_STOCK);
        }
        if ((coupon.getEndDate().compareTo(Date.valueOf(LocalDate.now())) < 0)) {
            throw new CouponSystemException(ERRORMSG.COUPON_EXPIRED);
        }
        if (couponsDAO.isExistByCustomerIdAndCouponId(this.customerId, coupon.getId()))
            throw new CouponSystemException(ERRORMSG.COUPON_PURCHASED);
        couponsDAO.addCouponPurchase(this.customerId, coupon.getId());
        coupon.setAmount(coupon.getAmount() - 1);

        couponsDAO.update(coupon.getId(), coupon);

    }

    @Override
    public List<Coupon> getCustomerCoupon() throws SQLException {

        return this.couponsDAO.getAllCouponByCustomerId(this.customerId);
    }

    @Override
    public List<Coupon> getCustomerCouponByCategory(CATEGORY category) throws SQLException {
        return this.couponsDAO.getAllCouponByCustomerIdAndCategory(this.customerId, category);
    }

    @Override
    public List<Coupon> getCustomerCouponByMaxPrice(double maxPrice) throws SQLException {
        return this.couponsDAO.getAllCouponByCustomerIdAndMaxPrice(this.customerId, maxPrice);
    }

    @Override
    public Customer getCustomerDetails() throws SQLException {
        List<Coupon> coupons = this.couponsDAO.getAllCouponByCustomerId(this.customerId);
        Customer customer = this.customersDAO.getSingle(this.customerId);
        customer.setCoupons(coupons);

        return customer;
    }
}
