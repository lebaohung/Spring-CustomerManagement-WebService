package com.codegym.cms.controller;

import com.codegym.cms.model.Customer;
import com.codegym.cms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    //Retrieve all customers
    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> listAllCustomers() {
        List<Customer> customers = customerService.findAll();
        if (customers.isEmpty()) {
            return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
     }

     //Retrive single customer
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") long id){
        Customer customer = customerService.findById(id);
        if (customer == null) {
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }

    //Create a customer
//    @RequestMapping(value = "/create", method = RequestMethod.POST)
//    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
//        customerService.save(customer);
//        return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
//    }

    @PostMapping("/create")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
        customerService.save(customer);
        return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
    }

    //Delete a customer
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        if (customer == null) {
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        } else {
            customerService.remove(id);
            return new ResponseEntity<Customer>(customer, HttpStatus.ACCEPTED);
        }
    }

    //Update a customer
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Customer updateCustomer(@PathVariable("id") Long id, @RequestBody Customer newCustomer){
        Customer customer = customerService.findById(id);
        if (customer == null) {
            return null;
        } else {
            Customer tempCustomer = new Customer();
            tempCustomer.setId(id);
            tempCustomer.setFirstName(newCustomer.getFirstName());
            tempCustomer.setLastName(newCustomer.getLastName());
            customerService.save(tempCustomer);
            return tempCustomer;
        }
    }
}
