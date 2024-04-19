package com.dev.phonestore.phonestore.controller;

import com.dev.phonestore.phonestore.entity.ImageModel;
import com.dev.phonestore.phonestore.entity.Phone;
import com.dev.phonestore.phonestore.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PhoneController {
    @Autowired
    private PhoneService phoneService;

    @PreAuthorize("hasRole('Admin')")
    @PostMapping(value = {"/phone/add"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Phone addNewPhone(@RequestPart("phone") Phone phone,
                             @RequestPart("imageFile") MultipartFile[] file) {

        try {
            Set<ImageModel> images = uploadImage(file);
            phone.setPhoneImageSet(images);
            return phoneService.addNewPhone(phone);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Set<ImageModel> uploadImage(MultipartFile[] files) throws IOException {
        Set<ImageModel> imageModelSet = new HashSet<>();

        for (MultipartFile file : files) {
            ImageModel imageModel = new ImageModel(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            imageModelSet.add(imageModel);
        }
        return imageModelSet;
    }


    @GetMapping("/getPhoneDetailsById/{phoneId}")
    public Phone getPhoneDetailsById(@PathVariable("phoneId") Integer phoneId) {
        return phoneService.getPhoneDetailsById(phoneId);
    }


    @GetMapping("/phone/get")
    public List<Phone> getAllPhone() {
        return phoneService.getAllPhone();
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/phone/delete/{phoneId}")
    public void deletePhone(@PathVariable("phoneId") Integer phoneId) {
        phoneService.deletePhone(phoneId);
    }


}
