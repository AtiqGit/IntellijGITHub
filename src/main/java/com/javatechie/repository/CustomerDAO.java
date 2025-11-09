package com.javatechie.repository;


import com.javatechie.hash.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDAO {
    private static final String HASH_KEY = "Customer";
    @Autowired
    private RedisTemplate template;

    public Customer addCustomer(Customer customer){
        template.opsForHash().put(HASH_KEY,customer.getId(),customer);
        return customer;
    }

    public List<Customer> getAllCustomer(){
        return  template.opsForHash().values(HASH_KEY);
    }

    public Customer getCustomer(int id){
        return (Customer) template.opsForHash().get(HASH_KEY,id);
    }

    public String  deleteCustomer(int id){
        template.opsForHash().delete(HASH_KEY,id);
        return "Customer" +id+ "has been removed from system !";
    }

    public Customer  updateCustomer(int id,Customer customer){
       Customer existenceCustomer = (Customer) template.opsForHash().get(HASH_KEY,id);
      if(existenceCustomer != null){
          existenceCustomer.setFirstName(customer.getFirstName());
          existenceCustomer.setLastName(customer.getLastName());
          existenceCustomer.setEmail(customer.getEmail());
          existenceCustomer.setPhone(customer.getPhone());
          existenceCustomer.setDob(customer.getDob());
          template.opsForHash().put(HASH_KEY,id,existenceCustomer);
          return existenceCustomer;
      }else{
          throw new RuntimeException("Customer not found");
      }
    }
}
