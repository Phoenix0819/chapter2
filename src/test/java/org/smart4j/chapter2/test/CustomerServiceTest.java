package org.smart4j.chapter2.test;

import org.junit.Before;
import org.junit.Test;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.service.CustomerService;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016-12-22.
 */
public class CustomerServiceTest {
    private final CustomerService customerService;
    public CustomerServiceTest(){
        customerService=new CustomerService();
    }

    @Before
    public void  init(){
        //TODO 初始化数据库
    }

    @Test
    public void getCustomerListTest (){
        //TODO
    }

    @Test
    public  void getCustomerTest(){
        //TODO
    }

    @Test
    public void createCustomerTest(){
        //TODO
    }

    @Test
    public void updateCustomerTest(){
        //TODO
    }

    @Test
    public void deleteCustomerTest(){
        //TODO
    }

}
