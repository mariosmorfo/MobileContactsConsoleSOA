package gr.aueb.cf.mobilecontacts.service;

import gr.aueb.cf.mobilecontacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactUpdateDTO;
import gr.aueb.cf.mobilecontacts.exceptions.ContactNotFoundException;
import gr.aueb.cf.mobilecontacts.exceptions.PhoneNumberAlreadyExists;
import gr.aueb.cf.mobilecontacts.model.MobileContact;

import java.util.List;

public interface IMobileContactService {

   MobileContact insertMobileContact(MobileContactInsertDTO dto) throws PhoneNumberAlreadyExists;

   MobileContact updateMobileContact(MobileContactUpdateDTO dto) throws PhoneNumberAlreadyExists, ContactNotFoundException;

   void deleteContactById(Long id) throws ContactNotFoundException;

   MobileContact getContactById(Long id) throws ContactNotFoundException;

   List<MobileContact> getAllContacts();

   MobileContact getContactByPhoneNumber(String phoneNumber) throws ContactNotFoundException;

   void deleteContactByPhoneNumber(String phoneNumber) throws ContactNotFoundException;
}
