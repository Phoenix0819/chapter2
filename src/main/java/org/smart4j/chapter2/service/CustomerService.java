package org.smart4j.chapter2.service;


import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.util.PropsUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.smart4j.chapter2.util.PropsUtil.LOGGER;

/**
 * Created by lenovo on 2016-12-22.
 */
public class CustomerService {
    public static final String DRIVER;
    public static final String URL;
    public static final String USERNAME;
    public static final String PASSWORD;

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

    public List<Customer> getCustomerList (){
        Connection conn=null;
        try {
            List<Customer> customerList=new ArrayList<Customer>();
            String sql="select * from customer";
            conn= DriverManager.getConnection(URL,USERNAME,PASSWORD);
            PreparedStatement statement=conn.prepareStatement(sql);
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
                Customer customer=new Customer();
                customer.setId(resultSet.getLong("id"));
                customer.setName(resultSet.getNString("name"));
                customer.setContact(resultSet.getNString("contact"));
                customer.setTelephone(resultSet.getNString("telephone"));
                customer.setEmail(resultSet.getNString("email"));
                customer.setRemark(resultSet.getNString("remark"));
                customerList.add(customer);
            }
            return customerList;
        } catch (SQLException e) {
            LOGGER.error("execute sql failure"+e);
        }finally {
            if (conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error("close connection failure"+e);
                }
            }
        }


        return null;
    }

    public  Customer getCustomer(long id){
        //TODO
        return null;
    }

    public boolean createCustomer(Map<String,Object> fieldMap){
        //TODO
        return false;
    }

    public boolean updateCustomer(long id,Map<String,Object> fieldMap){
        //TODO
        return false;
    }

    public boolean deleteCustomer(long id){
        //TODO
        return false;
    }
}
