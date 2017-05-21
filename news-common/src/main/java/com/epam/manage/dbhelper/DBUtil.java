package com.epam.manage.dbhelper;


import com.epam.manage.exception.DaoException;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBUtil {

    private static Logger LOG = Logger.getLogger(DBUtil.class);

    public static void closeResource(Connection connection, Statement statement,DataSource dataSource) throws DaoException {

            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }

        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static void closeResource(Connection connection, Statement statement, ResultSet resultSet,DataSource dataSource) throws DaoException {

        closeResource(connection,statement,dataSource);

        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            LOG.error(e);
        }


    }
}
