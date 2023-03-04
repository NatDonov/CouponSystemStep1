package dao;

import beans.CATEGORY;

import java.sql.SQLException;
import java.util.List;


public interface CategoryDAO {
    void addCategory(String name) throws SQLException;

    List<CATEGORY> getAllCategory() throws SQLException;

}
