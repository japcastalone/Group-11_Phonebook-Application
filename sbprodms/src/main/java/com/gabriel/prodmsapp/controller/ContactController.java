package com.gabriel.prodmsapp.controller;

import com.gabriel.prodmsapp.model.Contact;
import com.gabriel.prodmsapp.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContactController {
    Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @GetMapping("/api/contact")
    public ResponseEntity<?> listProducts()
    {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            Contact[] contacts = contactService.getContacts();
            response =  ResponseEntity.ok().headers(headers).body(contacts);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PutMapping("api/contacts")
    public ResponseEntity<?> add(@RequestBody Contact contact){
        logger.info("Input >> "+  contact.toString() );
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Contact newContact = contactService.create(contact);
            logger.info("created contact >> "+  newContact.toString() );
            response = ResponseEntity.ok(newContact);
        }
        catch( Exception ex)
        {
            logger.error("Failed to retrieve contact with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PostMapping("api/contacts")
    public ResponseEntity<?> update(@RequestBody Contact contact){
        logger.info("Update Input >> "+  contact.toString() );
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Contact newContact = contactService.update(contact);
            response = ResponseEntity.ok(contact);
        }
        catch( Exception ex)
        {
            logger.error("Failed to retrieve contact with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @GetMapping("api/contacts/{id}")
    public ResponseEntity<?> get(@PathVariable final Integer id){
        logger.info("Input product id >> "+  Integer.toString(id));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Contact contact = contactService.getProduct(id);
            response = ResponseEntity.ok(contact);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @DeleteMapping("api/contacts/{id}")
    public ResponseEntity<?> delete(@PathVariable final Integer id){
        logger.info("Input >> "+  Integer.toString(id));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            contactService.delete(id);
            response = ResponseEntity.ok(null);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }
}
