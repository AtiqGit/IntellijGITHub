package com.javatechie.service;

import com.javatechie.hash.Customer;
import com.javatechie.repository.CustomerDAO;
import com.javatechie.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class CustomerService {

    //@Autowired
  //  private CustomerDAO dao;

    @Autowired
    private CustomerRepository customerDao;

 private static final String CACHE_NAME = "customers"; //cache name only

    @CachePut(key ="#customer.id",value = CACHE_NAME)
    public Customer saveCustomer(Customer customer){
         log.info(" CustomerService:: saveCustomer() Inserting record into DB");
        return customerDao.save(customer);
    }
    @Cacheable(value= CACHE_NAME)
    public List<Customer> getAllCustomer(){
        log.info(" CustomerService:: getAllCustomer() fetching record from DB");

        //return (List<Customer>) customerDao.findAll();
        return StreamSupport.stream(customerDao.findAll().spliterator(),false).collect(Collectors.toList());
    }

    @Cacheable(key = "#id",value =CACHE_NAME)
    public Customer getCustomer(int id){
        log.info(" CustomerService:: getCustomer() fetching single record from DB");
        return customerDao.findById(id). get();
    }
   @CacheEvict(key ="#id",value=CACHE_NAME)
    public String  deleteCustomer(int id){
        log.info(" CustomerService::  deleteCustomer() deleting single record from DB");
          customerDao.deleteById(id);
          return "customer removed;";
    }
    @CachePut(key = "#id",value =CACHE_NAME)
    public Customer  updateCustomer(int id,Customer customer) {

        log.info(" CustomerService::  updateCustomer() updating record from DB");
      Customer existenceCustomer=  customerDao.findById(id).get();
      if(existenceCustomer!=null) {
          existenceCustomer.setFirstName(customer.getFirstName());
          existenceCustomer.setLastName(customer.getLastName());
          existenceCustomer.setEmail(customer.getEmail());
          existenceCustomer.setPhone(customer.getPhone());
          existenceCustomer.setDob(customer.getDob());
          return customerDao.save(existenceCustomer);
      }else{
          throw  new RuntimeException("customer not found");
      }

    }
}
