package dao;

import beans.Customer;

import java.sql.SQLException;

public interface CustomersDAO extends DaoMain<Customer, Integer> {

    boolean isExistByEmailAndPassword(String email, String password) throws SQLException;

    boolean isExistByEmail(String email) throws SQLException;

    boolean isOtherExistByEmail(int idCustomer, String email) throws SQLException;

    int getCustomerIdByEmail(String email) throws SQLException;
}
