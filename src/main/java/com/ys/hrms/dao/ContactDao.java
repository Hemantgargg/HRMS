package com.ys.hrms.dao;

import com.ys.hrms.entity.Contact;
import com.ys.hrms.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ContactDao {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactDao(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    // Save a single contact
    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    // Save multiple contacts
    public List<Contact> saveContacts(List<Contact> contacts) {
        return contactRepository.saveAll(contacts);
    }

    // Find contact by ID
    public Optional<Contact> findContactById(int id) {
        return contactRepository.findById(id);
    }

    // Find contacts by organization ID
    public List<Contact> findByOrganizationId(int organizationId) {
        return contactRepository.findByOrganizationId(organizationId);
    }

    // Update a contact
    public Contact updateContact(int id, Contact updatedContact) {
        Optional<Contact> existingContactOpt = contactRepository.findById(id);
        if (existingContactOpt.isPresent()) {
            Contact existingContact = existingContactOpt.get();
            // Update fields as needed
            existingContact.setContactPersonName(updatedContact.getContactPersonName());
            existingContact.setContactPersonDesignation(updatedContact.getContactPersonDesignation());
            existingContact.setContactEmail(updatedContact.getContactEmail());
            existingContact.setContactPhone(updatedContact.getContactPhone());
            existingContact.setContactAddress(updatedContact.getContactAddress());
            existingContact.setContactAadharNum(updatedContact.getContactAadharNum());
            existingContact.setContactPanNum(updatedContact.getContactPanNum());
            return contactRepository.save(existingContact);
        }
        return null; // or throw an exception if not found
    }

    // Delete a contact
    public void deleteContact(int id) {
        contactRepository.deleteById(id);
    }

    // Get all contacts
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }
}
