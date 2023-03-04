package dao;

import beans.Customer;
import db.ConvertUtils;
import db.JDBCUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomersDAOImpl implements CustomersDAO {

    private static final String INSERT_CUSTOMERS = "INSERT INTO `project_coupons`.`customers` (`first_name`, `last_name`, `email`, `password`) VALUES (?, ?, ?, ?)";

    private static final String DELETE_CUSTOMERS = "DELETE FROM `project_coupons`.`customers` WHERE (`id` = ?)";

    private static final String UPDATE_CUSTOMERS = "UPDATE `project_coupons`.`customers` SET `first_name` = ?, `last_name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?)";
    private static final String GET_ALL_CUSTOMERS = "SELECT * FROM `project_coupons`.`customers`";

    private static final String GET_CUSTOMER_ID_BY_EMAIL = "SELECT `id` FROM `project_coupons`.`customers` WHERE (`email` = ?)";

    private static final String GET_SINGLE = "SELECT * FROM `project_coupons`.`customers` WHERE (`id` = ?)";

    private static final String IS_EXIST_CUSTOMERS = "SELECT EXISTS ( SELECT * FROM `project_coupons`.`customers` WHERE `id`=?) as res";

    private static final String IS_EXIST_BY_EMAIL_AND_PASSWORD = "SELECT EXISTS ( SELECT * FROM `project_coupons`.`customers` WHERE `email`=? AND `PASSWORD`=?) as res";

    private static final String IS_OTHER_EXIST_BY_EMAIL = "SELECT EXISTS ( SELECT * FROM `project_coupons`.`customers` WHERE `id`<>? AND `email`=?) as res";

    private static final String IS_EXIST_BY_EMAIL = "SELECT EXISTS ( SELECT * FROM `project_coupons`.`customers` WHERE `email`= ? ) as res";


    @Override
    public void add(Customer customer) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword());
        params.put(5, customer.getCoupons());

        JDBCUtils.runQuery(INSERT_CUSTOMERS, params);

    }

    @Override
    public void update(Integer id, Customer customer) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword());
        params.put(5, id);

        JDBCUtils.runQuery(UPDATE_CUSTOMERS, params);


    }

    @Override
    public void delete(Integer custId) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, custId);

        JDBCUtils.runQuery(DELETE_CUSTOMERS, params);

    }

    @Override
    public List<Customer> getAll() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        List<?> list = JDBCUtils.runQueryWithResult(GET_ALL_CUSTOMERS);
        for (Object obj : list) {
            Customer customer = ConvertUtils.objectToCustomer((Map<String, Object>) obj);
            customers.add(customer);
        }
        return customers;
    }

    @Override
    public Customer getSingle(Integer id) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);

        List<?> list = JDBCUtils.runQueryWithResult(GET_SINGLE, params);
        Customer customer = ConvertUtils.objectToCustomer((Map<String, Object>) list.get(0));

        return customer;

    }

    @Override
    public boolean isExist(Integer custId) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, custId);
        List<?> list = JDBCUtils.runQueryWithResult(IS_EXIST_CUSTOMERS, params);
        result = ConvertUtils.objectToBool((Map<String, Object>) list.get(0));
        return result;

    }

    @Override
    public boolean isExistByEmailAndPassword(String email, String password) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        params.put(2, password);
        List<?> list = JDBCUtils.runQueryWithResult(IS_EXIST_BY_EMAIL_AND_PASSWORD, params);
        result = ConvertUtils.objectToBool((Map<String, Object>) list.get(0));
        return result;
    }

    @Override
    public boolean isExistByEmail(String email) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        List<?> list = JDBCUtils.runQueryWithResult(IS_EXIST_BY_EMAIL, params);
        result = ConvertUtils.objectToBool((Map<String, Object>) list.get(0));
        return result;
    }

    @Override
    public boolean isOtherExistByEmail(int idCustomer, String email) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, idCustomer);
        params.put(2, email);
        List<?> list = JDBCUtils.runQueryWithResult(IS_OTHER_EXIST_BY_EMAIL, params);
        result = ConvertUtils.objectToBool((Map<String, Object>) list.get(0));
        return result;
    }

    @Override
    public int getCustomerIdByEmail(String email) throws SQLException {
        int id = 0;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        List<?> list = JDBCUtils.runQueryWithResult(GET_CUSTOMER_ID_BY_EMAIL, params);

        for (Object obj : list) {
            id = ConvertUtils.objectToInt((Map<String, Object>) obj);

        }
        return id;
    }
}
