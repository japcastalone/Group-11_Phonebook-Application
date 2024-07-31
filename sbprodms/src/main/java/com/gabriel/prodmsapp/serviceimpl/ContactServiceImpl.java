package com.gabriel.prodmsapp.serviceimpl;

import com.gabriel.prodmsapp.entity.ContactData;
import com.gabriel.prodmsapp.model.Contact;
import com.gabriel.prodmsapp.repository.ContactDataRepository;
import com.gabriel.prodmsapp.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContactServiceImpl implements ContactService {
    Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

    @Autowired
    ContactDataRepository contactDataRepository;

    @Override
    public Contact[] getContacts() {
        List<ContactData> productsData = new ArrayList<>();
        List<Contact> contacts = new ArrayList<>();
        contactDataRepository.findAll().forEach(productsData::add);
        Iterator<ContactData> it = productsData.iterator();

        while(it.hasNext()) {
            Contact contact = new Contact();
            ContactData ContactData = it.next();
            contact.setId(ContactData.getId());
            contact.setName(ContactData.getName());
            contact.setNumber(ContactData.getNumber());
            contact.setCategoryId(ContactData.getCategoryId());
            contact.setCategoryName(ContactData.getCategoryName());
            contacts.add(contact);
        }

        Contact[] array = new Contact[contacts.size()];
        for  (int i = 0; i< contacts.size(); i++){
            array[i] = contacts.get(i);
        }
//        Contact[] array = (Contact[])contacts.toArray();
        return array;
    }

    @Override
    public Contact create(Contact contact) {
        logger.info("add: Input"+ contact.toString());
        ContactData contactData = new ContactData();
        contactData.setName(contact.getName());
        contactData.setNumber(contact.getNumber());
        contactData.setCategoryId(contact.getCategoryId());
        contactData.setCategoryName(contact.getCategoryName());

        contactData = contactDataRepository.save(contactData);
        logger.info("add: Input"+ contactData.toString());

        Contact newContact = new Contact();
        newContact.setId(contactData.getId());
        newContact.setName(contactData.getName());
        newContact.setNumber(contactData.getNumber());
        newContact.setCategoryId(contactData.getCategoryId());
        newContact.setCategoryName(contactData.getCategoryName());
        return newContact;
    }

    @Override
    public Contact update(Contact contact) {
        ContactData contactData = new ContactData();
        contactData.setId(contact.getId());
        contactData.setName(contact.getName());
        contactData.setNumber(contact.getNumber());
        contactData.setCategoryId(contact.getCategoryId());
        contactData.setCategoryName(contact.getCategoryName());

        contactData = contactDataRepository.save(contactData);

        Contact newContact = new Contact();
        newContact.setId(contactData.getId());
        newContact.setName(contactData.getName());
        newContact.setNumber(contactData.getNumber());
        newContact.setCategoryId(contactData.getCategoryId());
        newContact.setCategoryName(contactData.getCategoryName());
        return newContact;
    }

    @Override
    public Contact getProduct(Integer id) {
        logger.info("Input id >> "+  Integer.toString(id) );
        Optional<ContactData> optional = contactDataRepository.findById(id);
        if(optional.isPresent()) {
            logger.info("Is present >> ");
            Contact contact = new Contact();
            ContactData productDatum = optional.get();
            contact.setId(productDatum.getId());
            contact.setName(productDatum.getName());
            contact.setNumber(productDatum.getNumber());
            contact.setCategoryId(productDatum.getCategoryId());
            contact.setCategoryName(productDatum.getCategoryName());
            return contact;
        }
        logger.info("Failed  >> unable to locate product" );
        return null;
    }

    @Override
    public void delete(Integer id) {
        Contact contact = null;
        logger.info("Input >> " + Integer.toString(id));
         Optional<ContactData> optional = contactDataRepository.findById(id);
         if( optional.isPresent()) {
             ContactData productDatum = optional.get();
             contactDataRepository.delete(optional.get());
             logger.info("Successfully deleted >> " + productDatum.toString());
             contact = new Contact();
             contact.setId(optional.get().getId());
             contact.setName(optional.get().getName());
             contact.setNumber(optional.get().getNumber());
             contact.setCategoryId(optional.get().getCategoryId());
             contact.setCategoryName(optional.get().getCategoryName());
         }
         else {
             logger.info("Failed  >> unable to locate contact id: " +  Integer.toString(id));
         }
    }
}
