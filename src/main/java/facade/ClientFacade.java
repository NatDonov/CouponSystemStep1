package facade;

import dao.*;
import exception.CouponSystemException;

import java.sql.SQLException;

public abstract class ClientFacade {

    protected CompaniesDAO companiesDAO = new CompaniesDAOImpl();
    protected CustomersDAO customersDAO = new CustomersDAOImpl();
    protected CouponsDAO couponsDAO = new CouponsDAOImpl();

    public ClientFacade() {
    }

    public abstract boolean login(String email, String password) throws CouponSystemException, SQLException;
}
