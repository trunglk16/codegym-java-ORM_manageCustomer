package com.codegym.cms.controller;


import com.codegym.cms.model.Customer;
import com.codegym.cms.service.CustomerService;
import com.codegym.cms.service.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.GeneratedValue;
import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/")
    public String list() {
        return "list";
    }

    @GetMapping("/create-customer")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping("/create-customer")
    public String saveCustomer(@ModelAttribute("customer") Customer customer, RedirectAttributes redirect){
        customerService.save(customer);

        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("message", "New customer created successfully");
        return "redirect:/customers";
    }

    @GetMapping("/customers")
    public ModelAndView listCustomers(){
        List<Customer> customers = customerService.findAll();
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/edit-customer/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Customer customer = customerService.findById(id);
        if(customer != null) {
            ModelAndView modelAndView = new ModelAndView("/customer/edit");
            modelAndView.addObject("customer", customer);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-customer")
    public String updateCustomer(@ModelAttribute("customer") Customer customer, RedirectAttributes redirect){
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("/customer/edit");
        modelAndView.addObject("customer", customer);
        modelAndView.addObject("message", "Customer updated successfully");
        return "redirect:/customers";
    }

    @GetMapping("/delete-customer/{id}")
    public String showDeleteForm(@PathVariable Long id){
        Customer customer = customerService.findById(id);

        if(customer != null) {
            customerService.remove(customer.getId());


            return "redirect:/customers";

        }else {

            return "redirect:/error.404";
        }
    }

//    @PostMapping("/delete-customer")
//    public String deleteCustomer(@ModelAttribute("customer") Customer customer){
//        customerService.remove(customer.getId());
//        return "redirect:customers";
//    }
}