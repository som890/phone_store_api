package com.dev.phonestore.phonestore.service;

import com.dev.phonestore.phonestore.configuration.SecurityFilter;
import com.dev.phonestore.phonestore.entity.*;
import com.dev.phonestore.phonestore.repository.OrderDetailsRepository;
import com.dev.phonestore.phonestore.repository.PhoneRepository;
import com.dev.phonestore.phonestore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsService {
    private static final String ORDER_STATUS = "Placed";
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    public void placeOrder(OrderInput orderInput) {
        List<OrderQuantity> orderInputList = orderInput.getOrderQuantityList();

        if (orderInputList != null) {
            for (OrderQuantity o : orderInputList) {
                Optional<Phone> optionalPhone = phoneRepository.findById(o.getPhoneId());
                Phone phone = null;
                if (optionalPhone.isPresent()) {
                    phone = optionalPhone.get();
                }
                String username = SecurityFilter.CURRENT_USER;

                User user = null;
                Optional<User> optionalUser = userRepository.findById(username);
                if (optionalUser.isPresent()) {
                    user = optionalUser.get();
                }
                OrderDetails orderDetails = new OrderDetails(
                        orderInput.getOrderFullName(),
                        orderInput.getOrderFullAddress(),
                        orderInput.getOrderContactNumber(),
                        orderInput.getOrderAlternateNumber(),
                        (phone != null ? phone.getPhoneActualPrice() : null) *o.getQuantity(),
                        user,
                        phone,
                        ORDER_STATUS
                );
                orderDetailsRepository.save(orderDetails);
            }
        } else {
            System.out.println("Order  input list is null!");
        }
    }

}
