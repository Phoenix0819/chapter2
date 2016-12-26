package org.smart4j.chapter2.helper;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.util.PropsUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Created by lenovo on 2016-12-26.
 */
public final class DataBaseHelper {
    private static final Logger LOGGER= LoggerFactory.getLogger(PropsUtil.class);
    private static final QueryRunner QUERY_RUNNER=new QueryRunner();

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;
    static {
        Properties conf= PropsUtil.loadProps("config.properties");
        DRIVER=conf.getProperty("jdbc.driver");
        URL=conf.getProperty("jdbc.url");
        USERNAME=conf.getProperty("jdbc.username");
        PASSWORD=conf.getProperty("jdbc.password");

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver"+e);
        }

    }

    public static Connection getConnection(){
        Connection connection=null;
        try{
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            LOGGER.error("get connection failure"+e);
        }
        return connection;
    }

    public static void closeConneciton(Connection connection){
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure"+e);
            }
        }
    }

    public static <T> List<T> queryEntityList(Class<T> entityClass,Connection connection,String sql,Object... params){
        List<T> entityList;
        try {
            entityList=QUERY_RUNNER.query(connection,sql,new BeanListHandler<T>(entityClass),params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure"+e);
            throw new RuntimeException(e);
        }finally {
            closeConneciton(connection);
        }
        return entityList;
    }
}
