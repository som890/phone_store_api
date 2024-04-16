package com.dev.phonestore.phonestore.controller;

import com.dev.phonestore.phonestore.entity.Phone;
import com.dev.phonestore.phonestore.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PhoneController {
    @Autowired
    private PhoneService phoneService;

    @PostMapping("/phone/add")
    public Phone addNewPhone(@RequestBody Phone phone) {
        return phoneService.addNewPhone(phone);
    }

}
