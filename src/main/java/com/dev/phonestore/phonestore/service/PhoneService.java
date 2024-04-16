package com.dev.phonestore.phonestore.service;

import com.dev.phonestore.phonestore.entity.Phone;
import com.dev.phonestore.phonestore.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneService {
    @Autowired
    private PhoneRepository phoneRepository;

    public Phone addNewPhone(Phone phone) {
        return phoneRepository.save(phone);
    }
}
