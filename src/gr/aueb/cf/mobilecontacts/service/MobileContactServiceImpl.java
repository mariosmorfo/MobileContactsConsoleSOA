package gr.aueb.cf.mobilecontacts.service;

import gr.aueb.cf.mobilecontacts.dao.IMobileContactDAO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactUpdateDTO;
import gr.aueb.cf.mobilecontacts.exceptions.ContactNotFoundException;
import gr.aueb.cf.mobilecontacts.exceptions.PhoneNumberAlreadyExists;
import gr.aueb.cf.mobilecontacts.mapper.Mapper;
import gr.aueb.cf.mobilecontacts.model.MobileContact;

import java.util.List;

public class MobileContactServiceImpl implements  IMobileContactService {
    private final IMobileContactDAO dao;

    public MobileContactServiceImpl(IMobileContactDAO dao) {
        this.dao = dao;
    }

    @Override
    public MobileContact insertMobileContact(MobileContactInsertDTO dto) throws PhoneNumberAlreadyExists {
        MobileContact mobileContact;

        try{
            if(dao.phoneNumberExists(dto.getPhoneNumber())) {
                throw new PhoneNumberAlreadyExists("Contact with phone number " + dto.getPhoneNumber() + " already exists\n");
            }
                mobileContact = Mapper.mapInsertDTOToContact(dto);

            System.err.printf("MobileContactServiceImpl logger: %s was insert\n", mobileContact);
            return dao.insert(mobileContact);


        }catch (PhoneNumberAlreadyExists e){
            System.err.printf("MobileContactServiceImpl logger: contact with phone number: %s already exists\n", dto.getPhoneNumber());
            throw e;
        }
    }

    @Override
    public MobileContact updateMobileContact(MobileContactUpdateDTO dto) throws PhoneNumberAlreadyExists, ContactNotFoundException {
        MobileContact mobileContact;
        MobileContact newContact;
        try{
            if(!dao.userIdExists(dto.getId())){
                throw new ContactNotFoundException("Contact with id: " + dto.getId() + " not found for update");
            }
            mobileContact = dao.getById(dto.getId());

            boolean isPhoneNumberOurOwn = mobileContact.getPhoneNumber().equals(dto.getPhoneNumber());
            boolean isPhoneNumberExists = dao.phoneNumberExists(dto.getPhoneNumber());

            if(isPhoneNumberExists && !isPhoneNumberOurOwn){
                throw new PhoneNumberAlreadyExists("Contact with phone number " + dto.getPhoneNumber() + " already exists and can not be updated");
            }

            newContact = Mapper.mapUpdateDTOToContact(dto);
            System.err.printf("MobileContactServiceImpl Logger: %s was updated with new info: %s \n",mobileContact, newContact);
            newContact = Mapper.mapUpdateDTOToContact(dto);
            return dao.update(dto.getId(), newContact);

        }catch (ContactNotFoundException | PhoneNumberAlreadyExists e) {
            System.err.println( "MobileContactServiceImpl Logger: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteContactById(Long id) throws ContactNotFoundException {
        try{
            if(! dao.userIdExists(id)) {
                throw new ContactNotFoundException("Contact with id: %d " + id + " not found for delete.\n");
            }
            System.err.printf("MobileContactServiceImpl Logger: contact with id: %d " + id + " was deleted\n");
            dao.deleteById(id);
        }catch (ContactNotFoundException e){
            System.err.printf("MobileContactServiceImpl Logger: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public MobileContact getContactById(Long id) throws ContactNotFoundException {
        MobileContact mobileContact;

        try {

            mobileContact = dao.getById(id);
            if(mobileContact == null) {
                throw new ContactNotFoundException("Contact with id: %d " + id + " not found");
            }

            return mobileContact;

        }catch (ContactNotFoundException e){
            System.err.println("Contact with id: %d "+ id + " was not found to return\n");
            throw e;
        }
    }

    @Override
    public List<MobileContact> getAllContacts() {
        return dao.getAll();
    }

    @Override
    public MobileContact getContactByPhoneNumber(String phoneNumber) throws ContactNotFoundException {
        MobileContact mobileContact;

        try {

            mobileContact = dao.getByPhoneNumber(phoneNumber);
            if(mobileContact == null) {
                throw new ContactNotFoundException("Contact with phone number " + phoneNumber + " not found");
            }

            return mobileContact;

        }catch (ContactNotFoundException e){
            System.err.println("Contact with phone number: %s "+ phoneNumber + " was not found to return\n");
            throw e;
        }
    }

    @Override
    public void deleteContactByPhoneNumber(String phoneNumber) throws ContactNotFoundException {
        try{
            if(! dao.phoneNumberExists(phoneNumber)) {
                throw new ContactNotFoundException("Contact with phone number:  " + phoneNumber + " not found for delete.\n");
            }
            System.err.printf("MobileContactServiceImpl Logger: contact with phone number: %s " + phoneNumber + " was deleted\n");
            dao.deleteByPhoneNumber(phoneNumber);
        }catch (ContactNotFoundException e){
            System.err.printf("MobileContactServiceImpl Logger: " + e.getMessage());
            throw e;
        }
    }
}
