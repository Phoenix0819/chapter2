package org.smart4j.chapter2.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.helper.DataBaseHelper;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.util.PropsUtil;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016-12-22.
 */
public class CustomerService {
    private static final Logger LOGGER= LoggerFactory.getLogger(PropsUtil.class);

    public List<Customer> getCustomerList (){
            String sql="select * from customer";
            return DataBaseHelper.queryEntityList(Customer.class,sql);

    }

    public  Customer getCustomer(long id){
        //TODO
        return null;
    }

    public boolean createCustomer(Map<String,Object> fieldMap){
        return DataBaseHelper.insertEntity(Customer.class,fieldMap);
    }

    public boolean updateCustomer(long id,Map<String,Object> fieldMap){
        return DataBaseHelper.updateEntity(Customer.class,id,fieldMap);
    }

    public boolean deleteCustomer(long id){
        return DataBaseHelper.deleteEntity(Customer.class,id);
    }
}
