package dao;

import beans.CATEGORY;
import beans.Coupon;
import db.ConvertUtils;
import db.JDBCUtils;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponsDAOImpl implements CouponsDAO {

    private static final String INSERT_COUPONS = "INSERT INTO `project_coupons`.`coupons` (`company_id`, `category_id`, `title`, `description`, `start_date`, `end_date`, `amount`, `price`, `image`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_COUPONS = "UPDATE `project_coupons`.`coupons` SET `company_id` = ?, `category_id` = ?, `title` = ?, `description` = ?, `start_date` = ?, `end_date` = ?, `amount` = ?, `price` = ?, `image` = ? WHERE (`id` = ?)";

    private static final String DELETE_COUPONS = "DELETE FROM `project_coupons`.`coupons` WHERE (`id` = ?)";

    private static final String GET_ALL_COUPONS = "SELECT * FROM `project_coupons`.`coupons`";

    private static final String GET_ALL_COUPONS_BY_COMPANY_ID = "SELECT * FROM `project_coupons`.`coupons` WHERE (`company_id` = ?)";

    private static final String GET_SINGLE = "SELECT * FROM `project_coupons`.`coupons` WHERE (`id` = ?)";

    private static final String IS_EXIST_COUPON = "SELECT EXISTS ( SELECT * FROM `project_coupons`.`coupons` WHERE `id`=?) as res";

    private static final String INSERT_COUPONS_CUSTOMERS = "INSERT INTO `project_coupons`.`customers_coupons` (`customers_id`, `coupon_id`) VALUES (?, ?)";

    private static final String DELETE_ALL_COUPONS_BY_CUSTOMERS_ID = "DELETE FROM `project_coupons`.`customers_coupons` WHERE (`customers_id` = ?)";

    private static final String GET_ALL_COUPONS_BY_CUSTOMERS_ID = "SELECT * FROM `project_coupons`.`coupons` AS c JOIN `project_coupons`.`customers_coupons` AS cvc ON c.id = cvc.coupon_id WHERE (`customers_id` = ?)";

    private static final String DELETE_ALL_COUPONS_BY_COUPONS_ID = "DELETE FROM `project_coupons`.`customers_coupons` WHERE (`coupon_id` = ?)";

    private static final String DELETE_ALL_COUPONS_BY_COMPANY_ID = "DELETE FROM `project_coupons`.`coupons` WHERE (`company_id` = ?)";

    private static final String IS_EXIST_BY_TITLE_AND_COMPANY_ID = "SELECT EXISTS ( SELECT * FROM `project_coupons`.`coupons`  WHERE (`title` = ?) and (`company_id` = ?) and (`id`<> ?)) as res";
    private static final String IS_EXIST_BY_TITLE_AND_COMPANY_ID_D = "SELECT EXISTS ( SELECT * FROM `project_coupons`.`coupons`  WHERE (`title` = ?) and (`company_id` = ?))  as res";

    private static final String IS_EXIST_BY_CUSTOMER_ID_AND_COUPON_ID = "SELECT EXISTS ( SELECT * FROM `project_coupons`.`customers_coupons`  WHERE (`customers_id` = ?) and (`coupon_id` = ?)) as res";
    private static final String IS_EXIST_BY_COUPON_ID_AND_COMPANY_ID = "SELECT EXISTS ( SELECT * FROM `project_coupons`.`coupons`  WHERE (`id` = ?) and (`company_id` = ?)) as res";

    private static final String GET_ALL_COUPONS_BY_MAX_PRICE = "SELECT * FROM `project_coupons`.`coupons` WHERE (`company_id` = ?) AND (`price`<= ?)";

    private static final String GET_ALL_COUPONS_BY_CATEGORY = "SELECT * FROM `project_coupons`.`coupons` WHERE (`company_id` = ?) AND (`category_id`=?)";

    private static final String GET_ALL_COUPONS_BY_CUSTOMERS_ID_AND_CATEGORY = "SELECT * FROM `project_coupons`.`coupons`  JOIN `project_coupons`.`customers_coupons` ON `project_coupons`.`coupons`.id = `project_coupons`.`customers_coupons`.coupon_id WHERE (`project_coupons`.`customers_coupons`.`customers_id` = ?) AND (`project_coupons`.`coupons`.`category_id` = ?)";

    private static final String GET_ALL_COUPONS_BY_CUSTOMERS_ID_AND_MAX_PRICE = "SELECT * FROM `project_coupons`.`coupons`  JOIN `project_coupons`.`customers_coupons` ON `project_coupons`.`coupons`.id = `project_coupons`.`customers_coupons`.coupon_id WHERE (`project_coupons`.`customers_coupons`.`customers_id` = ?) AND (`project_coupons`.`coupons`.`price` <= ?)";

    private static final String GET_ALL_COUPONS_EXPIRED = "SELECT * FROM `project_coupons`.`coupons` WHERE (`end_date` < ?)";

    private static final String DELETE_EXPIRED_PURCHASE_COUPON = "DELETE FROM `project_coupons`.`customers_coupons` WHERE `coupon_id` IN (SELECT `id` FROM `project_coupons`.`coupons` WHERE `end_date`< current_date())";

    private static final String DELETE_PURCHASE_COUPON = "DELETE FROM `project_coupons`.`coupons` WHERE `end_date`< current_date()";


    @Override
    public void addCouponPurchase(int customerId, int couponId) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2, couponId);

        JDBCUtils.runQuery(INSERT_COUPONS_CUSTOMERS, params);

    }


    @Override
    public void deleteAllCouponPurchaseByCustomerId(int customerId) throws SQLException {

        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);

        JDBCUtils.runQuery(DELETE_ALL_COUPONS_BY_CUSTOMERS_ID, params);

    }

    @Override
    public void deleteAllCouponPurchaseByCouponId(int couponId) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponId);

        JDBCUtils.runQuery(DELETE_ALL_COUPONS_BY_COUPONS_ID, params);
    }

    @Override
    public void deleteAllCouponByCompaniesId(int companyId) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);

        JDBCUtils.runQuery(DELETE_ALL_COUPONS_BY_COMPANY_ID, params);

    }

    @Override
    public boolean isExistByTitleAndCompanyId(String title, int companyId, int couponId) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, title);
        params.put(2, companyId);
        params.put(3, couponId);
        List<?> list = JDBCUtils.runQueryWithResult(IS_EXIST_BY_TITLE_AND_COMPANY_ID, params);
        result = ConvertUtils.objectToBool((Map<String, Object>) list.get(0));
        return result;
    }

    @Override
    public boolean isExistByTitleAndCompanyId(String title, int companyId) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, title);
        params.put(2, companyId);
        List<?> list = JDBCUtils.runQueryWithResult(IS_EXIST_BY_TITLE_AND_COMPANY_ID_D, params);
        result = ConvertUtils.objectToBool((Map<String, Object>) list.get(0));
        return result;
    }

    @Override
    public boolean isExistByCustomerIdAndCouponId(int customerId, int couponId) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2, couponId);
        List<?> list = JDBCUtils.runQueryWithResult(IS_EXIST_BY_CUSTOMER_ID_AND_COUPON_ID, params);
        result = ConvertUtils.objectToBool((Map<String, Object>) list.get(0));
        return result;
    }

    @Override
    public List<Coupon> getAllByCompanyId(int companyId) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        List<?> list = JDBCUtils.runQueryWithResult(GET_ALL_COUPONS_BY_COMPANY_ID, params);
        for (Object obj : list) {
            Coupon coupon = ConvertUtils.objectToCoupon((Map<String, Object>) obj);
            coupons.add(coupon);
        }
        return coupons;
    }

    @Override
    public List<Coupon> getAllByMaxPrice(int companyId, double price) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        params.put(2, price);
        List<?> list = JDBCUtils.runQueryWithResult(GET_ALL_COUPONS_BY_MAX_PRICE, params);
        for (Object obj : list) {
            Coupon coupon = ConvertUtils.objectToCoupon((Map<String, Object>) obj);
            coupons.add(coupon);
        }
        return coupons;
    }


    @Override
    public void add(Coupon coupon) throws SQLException {

        Map<Integer, Object> params = new HashMap<>();
        params.put(1, coupon.getCompanyId());
        params.put(2, coupon.getCategory().ordinal() + 1);
        params.put(3, coupon.getTitle());
        params.put(4, coupon.getDescription());
        params.put(5, coupon.getStartDate());
        params.put(6, coupon.getEndDate());
        params.put(7, coupon.getAmount());
        params.put(8, coupon.getPrice());
        params.put(9, coupon.getImage());

        JDBCUtils.runQuery(INSERT_COUPONS, params);

    }

    @Override
    public void update(Integer id, Coupon coupon) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, coupon.getCompanyId());
        params.put(2, coupon.getCategory().ordinal() + 1);
        params.put(3, coupon.getTitle());
        params.put(4, coupon.getDescription());
        params.put(5, coupon.getStartDate());
        params.put(6, coupon.getEndDate());
        params.put(7, coupon.getAmount());
        params.put(8, coupon.getPrice());
        params.put(9, coupon.getImage());
        params.put(10, id);

        JDBCUtils.runQuery(UPDATE_COUPONS, params);

    }

    @Override
    public void delete(Integer coupId) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, coupId);

        JDBCUtils.runQuery(DELETE_COUPONS, params);


    }

    @Override
    public List<Coupon> getAll() throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        List<?> list = JDBCUtils.runQueryWithResult(GET_ALL_COUPONS);
        for (Object obj : list) {
            Coupon coupon = ConvertUtils.objectToCoupon((Map<String, Object>) obj);
            coupons.add(coupon);
        }
        return coupons;


    }

    @Override
    public Coupon getSingle(Integer id) throws SQLException {

        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);

        List<?> list = JDBCUtils.runQueryWithResult(GET_SINGLE, params);
        Coupon coupon = ConvertUtils.objectToCoupon((Map<String, Object>) list.get(0));

        return coupon;
    }

    @Override
    public boolean isExist(Integer coupId) throws SQLException {

        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, coupId);
        List<?> list = JDBCUtils.runQueryWithResult(IS_EXIST_COUPON, params);
        result = ConvertUtils.objectToBool((Map<String, Object>) list.get(0));
        return result;
    }


    @Override
    public List<Coupon> getCouponByCategory(int companyId, CATEGORY category) throws SQLException {

        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        params.put(2, category.ordinal() + 1);
        List<?> list = JDBCUtils.runQueryWithResult(GET_ALL_COUPONS_BY_CATEGORY, params);
        for (Object obj : list) {
            Coupon coupon = ConvertUtils.objectToCoupon((Map<String, Object>) obj);
            coupons.add(coupon);
        }
        return coupons;

    }

    @Override
    public List<Coupon> getAllCouponByCustomerId(int customerId) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        List<?> list = JDBCUtils.runQueryWithResult(GET_ALL_COUPONS_BY_CUSTOMERS_ID, params);
        for (Object obj : list) {
            Coupon coupon = ConvertUtils.objectToCoupon((Map<String, Object>) obj);
            coupons.add(coupon);
        }
        return coupons;


    }

    @Override
    public List<Coupon> getAllCouponByCustomerIdAndCategory(int customerId, CATEGORY category) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2, category.ordinal() + 1);
        List<?> list = JDBCUtils.runQueryWithResult(GET_ALL_COUPONS_BY_CUSTOMERS_ID_AND_CATEGORY, params);
        for (Object obj : list) {
            Coupon coupon = ConvertUtils.objectToCoupon((Map<String, Object>) obj);
            coupons.add(coupon);
        }
        return coupons;
    }

    @Override
    public List<Coupon> getAllCouponByCustomerIdAndMaxPrice(int customerId, double price) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2, price);
        List<?> list = JDBCUtils.runQueryWithResult(GET_ALL_COUPONS_BY_CUSTOMERS_ID_AND_MAX_PRICE, params);
        for (Object obj : list) {
            Coupon coupon = ConvertUtils.objectToCoupon((Map<String, Object>) obj);
            coupons.add(coupon);
        }
        return coupons;
    }

    @Override
    public boolean isExistByCouponIdAndCompanyId(int couponId, int companyId) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponId);
        params.put(2, companyId);
        List<?> list = JDBCUtils.runQueryWithResult(IS_EXIST_BY_COUPON_ID_AND_COMPANY_ID, params);
        result = ConvertUtils.objectToBool((Map<String, Object>) list.get(0));
        return result;
    }

    @Override
    public List<Coupon> getAllCouponsExpired(Date date) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, date);
        List<?> list = JDBCUtils.runQueryWithResult(GET_ALL_COUPONS_EXPIRED, params);
        for (Object obj : list) {
            Coupon coupon = ConvertUtils.objectToCoupon((Map<String, Object>) obj);
            coupons.add(coupon);
        }
        return coupons;
    }

    @Override
    public void deleteALLExpiredCoupon() throws SQLException {
        JDBCUtils.runQuery(DELETE_EXPIRED_PURCHASE_COUPON);
        JDBCUtils.runQuery(DELETE_PURCHASE_COUPON);
    }


}
