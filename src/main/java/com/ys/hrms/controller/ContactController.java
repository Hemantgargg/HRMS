package com.ys.hrms.controller;

import com.ys.hrms.dao.ContactDao;
import com.ys.hrms.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactDao contactDao;

    @Autowired
    public ContactController(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    // Save a single contact
    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
        Contact savedContact = contactDao.saveContact(contact);
        return new ResponseEntity<>(savedContact, HttpStatus.CREATED);
    }

    // Save multiple contacts
    @PostMapping("/batch")
    public ResponseEntity<List<Contact>> createContacts(@RequestBody List<Contact> contacts) {
        List<Contact> savedContacts = contactDao.saveContacts(contacts);
        return new ResponseEntity<>(savedContacts, HttpStatus.CREATED);
    }

    // Get a contact by ID
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable int id) {
        Optional<Contact> contact = contactDao.findContactById(id);
        return contact.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get contacts by organization ID
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<Contact>> getContactsByOrganizationId(@PathVariable int organizationId) {
        List<Contact> contacts = contactDao.findByOrganizationId(organizationId);
        return ResponseEntity.ok(contacts);
    }

    // Update a contact
    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable int id, @RequestBody Contact updatedContact) {
        Contact contact = contactDao.updateContact(id, updatedContact);
        return contact != null ? ResponseEntity.ok(contact) : ResponseEntity.notFound().build();
    }

    // Delete a contact
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable int id) {
        contactDao.deleteContact(id);
        return ResponseEntity.noContent().build();
    }

    // Get all contacts
    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts() {
        List<Contact> contacts = contactDao.getAllContacts();
        return ResponseEntity.ok(contacts);
    }
}
