package security;

import exception.CouponSystemException;
import exception.ERRORMSG;
import facade.*;

import java.sql.SQLException;

public class LoginManager {
    private static LoginManager instance = null;

    private LoginManager() {

    }


    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }


    public ClientFacade login(String email, String password, ClientType clientType) throws CouponSystemException, SQLException {

        switch (clientType) {

            case ADMINISTRATOR:
                AdminFacadeImpl adminFacadeImpl = new AdminFacadeImpl();
                if (adminFacadeImpl.login(email, password)) {
                    return adminFacadeImpl;
                }
                break;
            case COMPANY:
                CompanyFacadeImpl companyFacade = new CompanyFacadeImpl();
                if (companyFacade.login(email, password)) {
                    return companyFacade;
                }
                break;
            case CUSTOMER:
                CustomerFacadeImpl customerFacade = new CustomerFacadeImpl();
                if (customerFacade.login(email, password)) {
                    return customerFacade;
                }
                break;
            default:
                throw new CouponSystemException(ERRORMSG.INVALID_CLIENT_TYPE);

        }
        throw new CouponSystemException(ERRORMSG.LOGIN_FAILED);
    }


}
