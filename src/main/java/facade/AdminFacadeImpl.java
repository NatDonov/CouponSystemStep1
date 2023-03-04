package facade;

import beans.Company;
import beans.Coupon;
import beans.Customer;
import exception.CouponSystemException;
import exception.ERRORMSG;

import java.sql.SQLException;
import java.util.List;

public class AdminFacadeImpl extends ClientFacade implements AdminFacade {


    public AdminFacadeImpl() {
    }

    @Override
    public boolean login(String email, String password) throws CouponSystemException {
        if (!(email.equals("admin@admin.com") && password.equals("admin"))) {
            throw new CouponSystemException(ERRORMSG.ADMIN_LOGIN_ERROR);
        }
        return true;
    }


    @Override
    public void addCompany(Company company) throws CouponSystemException, SQLException {
        if (this.companiesDAO.isExistByName(company.getName())) {
            throw new CouponSystemException(ERRORMSG.COMPANY_NAME_EXIST);
        }
        if (this.companiesDAO.isExistByEmail(company.getEmail())) {
            throw new CouponSystemException(ERRORMSG.COMPANY_EMAIL_EXIST);
        }

        this.companiesDAO.add(company);

    }

    @Override
    public void updateCompany(int companyId, Company company) throws SQLException, CouponSystemException {
        if (!(companiesDAO.isExist(companyId))) {
            throw new CouponSystemException(ERRORMSG.NOT_EXIST_COMPANY);
        }
        if (companyId != company.getId()) {
            throw new CouponSystemException(ERRORMSG.COMPANY_UPDATE_ID);
        }
        if (!(companiesDAO.getSingle(companyId).getName().equals(company.getName()))) {
            throw new CouponSystemException(ERRORMSG.COMPANY_UPDATE_NAME);
        }
        if (companiesDAO.isOtherExistByEmail(companyId, company.getEmail())) {
            throw new CouponSystemException(ERRORMSG.COMPANY_EMAIL_EXIST);
        }

        companiesDAO.update(companyId, company);

    }

    @Override
    public void deleteCompany(int companyId) throws SQLException, CouponSystemException {

        if (!(companiesDAO.isExist(companyId))) {
            throw new CouponSystemException(ERRORMSG.NOT_EXIST_COMPANY);
        }
        for (Coupon coupon : couponsDAO.getAllByCompanyId(companyId)) {
            couponsDAO.deleteAllCouponPurchaseByCouponId(coupon.getId());
        }
        couponsDAO.deleteAllCouponByCompaniesId(companyId);
        companiesDAO.delete(companyId);

    }

    @Override
    public List<Company> getAllCompanies() throws SQLException {
        return companiesDAO.getAll();
    }

    @Override
    public Company getOneCompany(int companyId) throws SQLException, CouponSystemException {
        if (!(companiesDAO.isExist(companyId))) {
            throw new CouponSystemException(ERRORMSG.NOT_EXIST_COMPANY);
        }
        return companiesDAO.getSingle(companyId);
    }

    @Override
    public void addCustomer(Customer customer) throws SQLException, CouponSystemException {
        if (this.customersDAO.isExistByEmail(customer.getEmail())) {
            throw new CouponSystemException(ERRORMSG.CUSTOMER_EMAIL_EXIST);
        }
        this.customersDAO.add(customer);

    }

    @Override
    public void updateCustomer(int customerId, Customer customer) throws SQLException, CouponSystemException {
        if (!(customersDAO.isExist(customerId))) {
            throw new CouponSystemException(ERRORMSG.NOT_EXIST_CUSTOMER);
        }

        if (customerId != customer.getId()) {
            throw new CouponSystemException(ERRORMSG.CUSTOMER_UPDATE_ID);
        }

        if (customersDAO.isOtherExistByEmail(customerId, customer.getEmail())) {
            throw new CouponSystemException(ERRORMSG.CUSTOMER_EMAIL_EXIST);
        }

        customersDAO.update(customerId, customer);

    }

    @Override
    public void deleteCustomer(int customerId) throws SQLException, CouponSystemException {
        if (!(customersDAO.isExist(customerId))) {
            throw new CouponSystemException(ERRORMSG.NOT_EXIST_CUSTOMER);
        }
        couponsDAO.deleteAllCouponPurchaseByCustomerId(customerId);
        customersDAO.delete(customerId);

    }


    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        return customersDAO.getAll();
    }

    @Override
    public Customer getOneCustomers(int customerId) throws SQLException, CouponSystemException {

        if (!(customersDAO.isExist(customerId))) {
            throw new CouponSystemException(ERRORMSG.NOT_EXIST_CUSTOMER);
        }
        return customersDAO.getSingle(customerId);
    }
}
