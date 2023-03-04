package exception;

public enum ERRORMSG {

    COMPANY_NAME_EXIST("cannot add company with existing name"),
    COMPANY_EMAIL_EXIST("cannot add company with existing email"),

    ADMIN_LOGIN_ERROR("login error"),

    CUSTOMER_EMAIL_EXIST("cannot add customer with existing email"),

    COMPANY_EMAIL_OR_PASSWORD("Login error, email or password not exist"),

    CUSTOMER_EMAIL_OR_PASSWORD("Login error, email or password not exist"),

    COUPON_TITLE_EXIST("cannot add coupon with existing title"),

    COUPON_OUT_OF_STOCK("cannot purchased coupon out of stock"),

    COUPON_EXPIRED("Sorry, coupon has expired"),

    NOT_EXIST_COMPANY("Error, the company does not exist"),

    NOT_EXIST_COUPON("Error, the coupon does not exist"),

    NOT_EXIST_CUSTOMER("Error, the customer does not exist"),

    COMPANY_UPDATE_ID("Error, cannot update company`s id"),

    COMPANY_UPDATE_NAME("Error, cannot update company`s name"),

    CUSTOMER_UPDATE_ID("Error, cannot update customer`s id"),

    COUPON_UPDATE_ID("Error, cannot update coupon`s id"),

    COUPON_UPDATE_COMP_ID("Error, cannot update company`s id"),

    COUPON_PURCHASED("Coupon already purchased, you cannot purchase it again"),

    INVALID_CLIENT_TYPE("Client type is not supported"),

    LOGIN_FAILED("Failed to login"),

    INVALID_COUPON_DELETE("can't to delete coupon,to other company"),

    INVALID_COUPON_UPDATE("can`t update coupon"),

    INVALID_COUPONS_TITLE_UPDATE("can`t update coupon`s title"),

    INVALID_ADD_COUPON("can't to add coupon,to other company"),

    INVALID_UPDATE_COUPON("can't to update coupon,to other company");





    private String message;

    ERRORMSG(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
