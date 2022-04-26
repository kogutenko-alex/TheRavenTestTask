package ua.kogutenko.market.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import ua.kogutenko.market.exception.DeletedException;
import ua.kogutenko.market.exception.EmailShouldNotBeChangedException;
import ua.kogutenko.market.model.Customer;
import ua.kogutenko.market.service.impl.CustomerServiceImpl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ControllerUtil {
    private final CustomerServiceImpl customerService;

    public ControllerUtil(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }


    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public Customer checker(Customer customer, Long id) throws DeletedException, EmailShouldNotBeChangedException {
        Customer existing = customerService.findById(id).get();
        if (!Objects.equals(customer.getEmail(), existing.getEmail()) && customer.getEmail() != null) {
            throw new EmailShouldNotBeChangedException(
                    "Email should not be changed ( old = " + existing.getEmail()
                            + ", new = " + customer.getEmail() + " )");
        }
        return customer;
    }

}
