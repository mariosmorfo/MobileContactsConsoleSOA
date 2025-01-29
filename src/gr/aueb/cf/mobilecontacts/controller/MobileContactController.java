package gr.aueb.cf.mobilecontacts.controller;

import gr.aueb.cf.mobilecontacts.core.serializer.Serializer;
import gr.aueb.cf.mobilecontacts.dao.IMobileContactDAO;
import gr.aueb.cf.mobilecontacts.dao.MobileContactDAOImpl;
import gr.aueb.cf.mobilecontacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactReadOnlyDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactUpdateDTO;
import gr.aueb.cf.mobilecontacts.exceptions.ContactNotFoundException;
import gr.aueb.cf.mobilecontacts.exceptions.PhoneNumberAlreadyExists;
import gr.aueb.cf.mobilecontacts.mapper.Mapper;
import gr.aueb.cf.mobilecontacts.model.MobileContact;
import gr.aueb.cf.mobilecontacts.service.IMobileContactService;
import gr.aueb.cf.mobilecontacts.service.MobileContactServiceImpl;
import gr.aueb.cf.mobilecontacts.validation.ValidationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MobileContactController {

    private final IMobileContactDAO dao = new MobileContactDAOImpl();
    private final IMobileContactService service = new MobileContactServiceImpl(dao);

    public String insertContact (MobileContactInsertDTO insertDTO){
        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;

        try {
            String errorVector = ValidationUtil.validateDTO(insertDTO);
            if(! errorVector.isEmpty()){
                return "Error" +" Validation error\n" + errorVector;
            }
            mobileContact = service.insertMobileContact(insertDTO);
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);
            return "OK\n" + Serializer.serializeDTO(readOnlyDTO);
        }catch (PhoneNumberAlreadyExists e) {
            return "OK\n" + e.getMessage() + "\n";
        }
    }

    public String updateContact (MobileContactUpdateDTO updateDTO){
        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;

        try {
            String errorVector = ValidationUtil.validateDTO(updateDTO);
            if(! errorVector.isEmpty()){
                return "Error" +" Validation error\n" + errorVector;
            }
            mobileContact = service.updateMobileContact(updateDTO);
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);
            return "OK\n" + Serializer.serializeDTO(readOnlyDTO);
        }catch (PhoneNumberAlreadyExists e) {
            return "OK\n" + e.getMessage() + "\n";
        }catch (ContactNotFoundException e){
            return "Error\n" + e.getMessage();

        }
    }

    public String deleteContactById(Long id) {
        try {
            service.deleteContactById(id);
            return "OK\n Η επαφή διαγράφτηκε";
        } catch (ContactNotFoundException e) {
            return "Error\n Λάθος κατά τη διαγραφή. Η επαφή δεν βρέθηκε";
        }
    }

    public String getContactById(Long id){
        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;

        try{
            mobileContact = service.getContactById(id);
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);
            return "OK\n" + Serializer.serializeDTO(readOnlyDTO);
        }catch (ContactNotFoundException e){
            return "Error. \n Η επαφή δεν βρέθηκε";
        }
    }

    public List<String> getAllContacts(){
        List<MobileContact> contacts;
        List<String> serializedList = new ArrayList<>();
        MobileContactReadOnlyDTO readOnlyDTO;
        String serialized;
        contacts = service.getAllContacts();

        for (MobileContact contact : contacts) {
            readOnlyDTO = Mapper.mapMobileContactToDTO(contact);
            serialized = Serializer.serializeDTO(readOnlyDTO);
            serializedList.add(serialized);
        }
        return serializedList;
    }


    public String getContactByPhoneNumber(String phoneNumber){
        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;

        try{
            mobileContact = service.getContactByPhoneNumber(phoneNumber);
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);
            return "OK\n" + Serializer.serializeDTO(readOnlyDTO);
        }catch (ContactNotFoundException e){
            return "Error. \n Η επαφή δεν βρέθηκε";
        }
    }

    public String deleteContactByPhoneNumber(String phoneNumber){
        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;

        try{
            mobileContact = service.getContactByPhoneNumber(phoneNumber);
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);
            service.deleteContactByPhoneNumber(phoneNumber);

            return "OK\n" + Serializer.serializeDTO(readOnlyDTO);
        }catch (ContactNotFoundException e){
            return "Error. \n Η επαφή δεν βρέθηκε";
        }
    }



}