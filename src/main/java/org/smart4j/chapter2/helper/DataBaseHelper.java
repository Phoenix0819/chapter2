package org.smart4j.chapter2.helper;

import com.sun.xml.internal.ws.spi.db.FieldSetter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.util.CollectionUtil;
import org.smart4j.chapter2.util.PropsUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by lenovo on 2016-12-26.
 */
public final class DataBaseHelper {
    private static final Logger LOGGER= LoggerFactory.getLogger(PropsUtil.class);
    private static final QueryRunner QUERY_RUNNER=new QueryRunner();
    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL=new ThreadLocal<>();

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
        Connection connection=CONNECTION_THREAD_LOCAL.get();//Connection connection=null;
        if (connection==null){
            try{
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                LOGGER.error("get connection failure"+e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_THREAD_LOCAL.set(connection);
            }
        }
        return connection;
    }

    public static void closeConnection(){
        Connection connection=CONNECTION_THREAD_LOCAL.get();
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure"+e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_THREAD_LOCAL.remove();
            }
        }
    }

    public static <T> List<T> queryEntityList(Class<T> entityClass,String sql,Object... params){
        List<T> entityList;
        try {
            Connection connection=getConnection();
            entityList=QUERY_RUNNER.query(connection,sql,new BeanListHandler<T>(entityClass),params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure"+e);
            throw new RuntimeException(e);
        }finally {
            closeConnection();
        }
        return entityList;
    }

    public static <T>  T queryEntity(Class<T> entityClass,String sql,Object... params){
        T entity;
        try {
            Connection connection=getConnection();
            entity=QUERY_RUNNER.query(connection,sql,new BeanHandler<T>(entityClass),params);
        } catch (SQLException e) {
            LOGGER.error("query entity failure"+e);
            throw new RuntimeException(e);
        }finally {
            closeConnection();
        }
        return entity;
    }

    public static List<Map<String,Object>> executeQuery(String sql,Object...params){
        List<Map<String,Object>> result;
        Connection connection=getConnection();
        try {
            result=QUERY_RUNNER.query(connection,sql,new MapListHandler(),params);
        } catch (SQLException e) {
            LOGGER.error("execute query failure"+e);
            throw new RuntimeException(e);
        }finally {
            closeConnection();
        }
        return result;
    }

    public static int executeUpdate(String sql,Object...params){
        int rows=0;
        Connection connection=getConnection();
        try {
            rows=QUERY_RUNNER.update(connection,sql,params);
        } catch (SQLException e) {
            LOGGER.error("execute update failure"+e);
            throw new RuntimeException(e);
        }finally {
            closeConnection();
        }
        return rows;
    }

    public static <T> boolean insertEntity(Class<T> entityClass,Map<String,Object> fieldMap){
        if (CollectionUtil.isEmpty(fieldMap)){
            LOGGER.error("can not insert entity:fieldMap is empty");
            return false;
        }else {
            String sql="insert into "+getTableName(entityClass);
            StringBuilder columns=new StringBuilder("(");
            StringBuilder values=new StringBuilder("(");
            for (String fieldName:fieldMap.keySet()){
                columns.append(fieldName).append(",");
                values.append("?,");
            }
            columns.replace(columns.lastIndexOf(","),columns.length(),")");
            values.replace(values.lastIndexOf(","),values.length(),")");
            sql+=columns+"VALUES"+values;

            Object[] params= fieldMap.values().toArray();
            return executeUpdate(sql,params)==1;
        }
    }

    private static <T> String getTableName(Class<T> entityClass) {
        return  entityClass.getSimpleName();
    }

    public static <T> boolean updateEntity(Class<T> entityClass,long id,Map<String,Object> fieldMap){
        if (CollectionUtil.isEmpty(fieldMap)){
            LOGGER.error("can not update entity:fieldMap is empty");
            return false;
        }else {
            String sql="update "+getTableName(entityClass)+" set ";
            StringBuilder columns=new StringBuilder();
            for (String fieldName:fieldMap.keySet()){
                columns.append(fieldName).append("=?, ");
            }
            sql+=columns.substring(0,columns.lastIndexOf(","))+" where id=?";
            List<Object> paramList=new ArrayList<>();
            paramList.addAll(fieldMap.values());
            paramList.add(id);
            Object[] params=paramList.toArray();

            return executeUpdate(sql,params)==1;
        }
    }

    public static <T> boolean deleteEntity(Class<T> entityClass,long id){
        String sql="delete from "+getTableName(entityClass) +" where id =? ";
        return executeUpdate(sql,id)==1;
    }


}
