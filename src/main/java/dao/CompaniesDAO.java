package dao;

import beans.Company;

import java.sql.SQLException;


public interface CompaniesDAO extends DaoMain<Company, Integer> {

    boolean isExistByEmailOrName(String email, String name) throws SQLException;

    boolean isExistByName(String name) throws SQLException;

    boolean isExistByEmail(String email) throws SQLException;

    boolean isExistByEmailAndPassword(String email, String password) throws SQLException;

    boolean isOtherExistByEmail(int idCompany, String email) throws SQLException;

    int getCompanyIdByEmail(String email) throws SQLException;


}
