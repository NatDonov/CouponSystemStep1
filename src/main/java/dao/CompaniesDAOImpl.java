package dao;

import beans.Company;
import db.ConvertUtils;
import db.JDBCUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompaniesDAOImpl implements CompaniesDAO {

    private static final String INSERT_COMPANIES = "INSERT INTO `project_coupons`.`companies` (`name`, `email`, `password`) VALUES (?, ?, ?)";

    private static final String DELETE_COMPANIES = "DELETE FROM `project_coupons`.`companies` WHERE (`id` = ?)";

    private static final String UPDATE_COMPANIES = "UPDATE `project_coupons`.`companies` SET `name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?)";
    private static final String GET_ALL_COMPANIES = "SELECT * FROM `project_coupons`.`companies`";
    private static final String GET_COMPANY_ID_BY_EMAIL = "SELECT `id` FROM `project_coupons`.`companies` WHERE (`email` = ?)";
    private static final String GET_SINGLE = "SELECT * FROM `project_coupons`.`companies` WHERE (`id` = ?)";

    private static final String IS_EXIST_COMPANIES = "SELECT EXISTS ( SELECT * FROM `project_coupons`.`companies` WHERE `id`=?) as res";
    private static final String IS_EXIST_BY_EMAIL_OR_NAME = "SELECT EXISTS ( SELECT * FROM `project_coupons`.`companies` WHERE `email`=? OR `name`=?) as res";

    private static final String IS_OTHER_EXIST_BY_EMAIL = "SELECT EXISTS ( SELECT * FROM `project_coupons`.`companies` WHERE `id`<>? AND `email`=?) as res";

    private static final String IS_EXIST_BY_NAME = "SELECT EXISTS ( SELECT * FROM `project_coupons`.`companies` WHERE `name`=?) as res";

    private static final String IS_EXIST_BY_EMAIL = "SELECT EXISTS ( SELECT * FROM `project_coupons`.`companies` WHERE `email`=?) as res";

    private static final String IS_EXIST_BY_EMAIL_AND_PASSWORD = "SELECT EXISTS ( SELECT * FROM `project_coupons`.`companies` WHERE `email`=? AND `PASSWORD`=?) as res";


    @Override
    public void add(Company company) throws SQLException {

        Map<Integer, Object> params = new HashMap<>();
        params.put(1, company.getName());
        params.put(2, company.getEmail());
        params.put(3, company.getPassword());

        JDBCUtils.runQuery(INSERT_COMPANIES, params);

    }

    @Override
    public void update(Integer id, Company company) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, company.getName());
        params.put(2, company.getEmail());
        params.put(3, company.getPassword());

        params.put(4, id);

        JDBCUtils.runQuery(UPDATE_COMPANIES, params);

    }

    @Override
    public void delete(Integer compId) throws SQLException {

        Map<Integer, Object> params = new HashMap<>();
        params.put(1, compId);

        JDBCUtils.runQuery(DELETE_COMPANIES, params);

    }

    @Override
    public List<Company> getAll() throws SQLException {
        List<Company> companies = new ArrayList<>();
        List<?> list = JDBCUtils.runQueryWithResult(GET_ALL_COMPANIES);
        for (Object obj : list) {
            Company company = ConvertUtils.objectToCompany((Map<String, Object>) obj);
            companies.add(company);
        }
        return companies;
    }

    @Override
    public Company getSingle(Integer id) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);

        List<?> list = JDBCUtils.runQueryWithResult(GET_SINGLE, params);
        Company company = ConvertUtils.objectToCompany((Map<String, Object>) list.get(0));

        return company;
    }

    @Override
    public boolean isExist(Integer compId) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, compId);
        List<?> list = JDBCUtils.runQueryWithResult(IS_EXIST_COMPANIES, params);
        result = ConvertUtils.objectToBool((Map<String, Object>) list.get(0));
        return result;
    }

    @Override
    public boolean isExistByEmailOrName(String email, String name) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        params.put(2, name);
        List<?> list = JDBCUtils.runQueryWithResult(IS_EXIST_BY_EMAIL_OR_NAME, params);
        result = ConvertUtils.objectToBool((Map<String, Object>) list.get(0));
        return result;
    }

    @Override
    public boolean isExistByName(String name) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, name);
        List<?> list = JDBCUtils.runQueryWithResult(IS_EXIST_BY_NAME, params);
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
    public boolean isOtherExistByEmail(int idCompany, String email) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, idCompany);
        params.put(2, email);
        List<?> list = JDBCUtils.runQueryWithResult(IS_OTHER_EXIST_BY_EMAIL, params);
        result = ConvertUtils.objectToBool((Map<String, Object>) list.get(0));
        return result;

    }

    @Override
    public int getCompanyIdByEmail(String email) throws SQLException {
        int id = 0;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        List<?> list = JDBCUtils.runQueryWithResult(GET_COMPANY_ID_BY_EMAIL, params);

        for (Object obj : list) {
            id = ConvertUtils.objectToInt((Map<String, Object>) obj);

        }
        return id;
    }

}
