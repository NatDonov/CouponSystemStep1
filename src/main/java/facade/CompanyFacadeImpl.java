package facade;

import beans.CATEGORY;
import beans.Company;
import beans.Coupon;
import exception.CouponSystemException;
import exception.ERRORMSG;

import java.sql.SQLException;
import java.util.List;

public class CompanyFacadeImpl extends ClientFacade implements CompanyFacade {

    private int companyId;


    public CompanyFacadeImpl() {
    }


    @Override
    public boolean login(String email, String password) throws CouponSystemException, SQLException {
        if (!(companiesDAO.isExistByEmailAndPassword(email, password))) {
            throw new CouponSystemException(ERRORMSG.COMPANY_EMAIL_OR_PASSWORD);
        }
        this.companyId = companiesDAO.getCompanyIdByEmail(email);
        return true;
    }

    @Override
    public void addCoupon(Coupon coupon) throws SQLException, CouponSystemException {

        if (this.couponsDAO.isExistByTitleAndCompanyId(coupon.getTitle(), coupon.getCompanyId())) {
            throw new CouponSystemException(ERRORMSG.COUPON_TITLE_EXIST);
        }
        if (this.companyId != coupon.getCompanyId()) {
            throw new CouponSystemException(ERRORMSG.INVALID_ADD_COUPON);
        }
        this.couponsDAO.add(coupon);
    }

    @Override
    public void updateCoupon(int couponId, Coupon coupon) throws SQLException, CouponSystemException {
        if (!(couponsDAO.isExist(couponId))) {
            throw new CouponSystemException(ERRORMSG.NOT_EXIST_COUPON);
        }

        if (couponsDAO.getSingle(couponId).getCompanyId() != coupon.getCompanyId()) {
            throw new CouponSystemException(ERRORMSG.COUPON_UPDATE_COMP_ID);
        }

        if (couponId != coupon.getId()) {
            throw new CouponSystemException(ERRORMSG.COUPON_UPDATE_ID);
        }

        if (this.companyId != coupon.getCompanyId())
            throw new CouponSystemException(ERRORMSG.INVALID_UPDATE_COUPON);


        if (!(couponsDAO.isExistByCouponIdAndCompanyId(couponId, this.companyId))) {
            throw new CouponSystemException(ERRORMSG.INVALID_COUPON_UPDATE);
        }
        if (couponsDAO.isExistByTitleAndCompanyId(coupon.getTitle(), this.companyId, couponId)) {
            throw new CouponSystemException(ERRORMSG.INVALID_COUPONS_TITLE_UPDATE);
        }

        couponsDAO.update(couponId, coupon);

    }

    @Override
    public void deleteCoupon(int couponId) throws SQLException, CouponSystemException {
        if (!(couponsDAO.isExist(couponId))) {
            throw new CouponSystemException(ERRORMSG.NOT_EXIST_COUPON);
        }
        if (!(couponsDAO.isExistByCouponIdAndCompanyId(couponId, this.companyId))) {
            throw new CouponSystemException(ERRORMSG.INVALID_COUPON_DELETE);
        }

        couponsDAO.deleteAllCouponPurchaseByCouponId(couponId);
        couponsDAO.delete(couponId);
    }

    @Override
    public List<Coupon> getCompanyCoupons() throws SQLException {

        return this.couponsDAO.getAllByCompanyId(this.companyId);
    }

    @Override
    public List<Coupon> getCompanyCouponsByCategory(CATEGORY category) throws SQLException {

        return this.couponsDAO.getCouponByCategory(this.companyId, category);
    }

    @Override
    public List<Coupon> getCompanyCouponsByMaxPrice(double maxPrice) throws SQLException {
        return this.couponsDAO.getAllByMaxPrice(this.companyId, maxPrice);
    }

    @Override
    public Company getCompanyDetails() throws SQLException {
        List<Coupon> coupons = couponsDAO.getAllByCompanyId(this.companyId);
        Company company = companiesDAO.getSingle(this.companyId);
        company.setCoupons(coupons);

        return company;

    }
}
