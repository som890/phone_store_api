package com.dev.phonestore.phonestore.service;

import com.dev.phonestore.phonestore.entity.Phone;
import com.dev.phonestore.phonestore.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneService {
    @Autowired
    private PhoneRepository phoneRepository;

    public Phone getPhoneDetailsById(Integer phoneId) {
        Optional<Phone> optionPhone = phoneRepository.findById(phoneId);
        return optionPhone.orElse(null);
    }


    public Phone addNewPhone(Phone phone) {
        return phoneRepository.save(phone);
    }
    public List<Phone> getAllPhone() {
        return phoneRepository.findAll();
    }
    public void deletePhone(Integer phoneId) {
        phoneRepository.deleteById(phoneId);
    }
}
