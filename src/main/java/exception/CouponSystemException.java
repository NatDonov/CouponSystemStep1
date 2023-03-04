package exception;

public class CouponSystemException extends Exception{

    public CouponSystemException(ERRORMSG errormsg) {
        super(errormsg.getMessage());
    }


}
