package org.smart4j.chapter2.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.helper.DataBaseHelper;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.util.PropsUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lenovo on 2016-12-22.
 */
public class CustomerService {
    private static final Logger LOGGER= LoggerFactory.getLogger(PropsUtil.class);
    Connection connection=null;

    public List<Customer> getCustomerList (){
        connection = DataBaseHelper.getConnection();
        try {
            List<Customer> customerList=new ArrayList<>();
            String sql="select * from customer";

            PreparedStatement statement=connection.prepareStatement(sql);
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
            DataBaseHelper.closeConneciton(connection);
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
