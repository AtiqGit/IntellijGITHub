package com.javatechie.controller;

import com.javatechie.hash.Customer;
import com.javatechie.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PostMapping("/insert")
    public Customer saveCustomer(@RequestBody  Customer customer){
        return service.saveCustomer(customer);
    }
    @GetMapping
    public List<Customer> getAllCustomer(){
        return  service.getAllCustomer();
    }

    @GetMapping("/{id}")
    public Optional<Customer> getCustomer(@PathVariable int id){
        return Optional.ofNullable(service.getCustomer(id));
    }

    @DeleteMapping("/{id}")
    public String  deleteCustomer(@PathVariable int id)   {

        return  service.deleteCustomer(id);
    }

    @PutMapping("/{id}")
    public Customer  updateCustomer(@PathVariable int id,@RequestBody  Customer customer) {
        return service.updateCustomer(id,customer);
    }
}
