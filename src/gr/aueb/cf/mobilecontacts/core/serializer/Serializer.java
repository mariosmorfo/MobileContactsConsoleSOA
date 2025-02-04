package gr.aueb.cf.mobilecontacts.core.serializer;


import gr.aueb.cf.mobilecontacts.dto.MobileContactReadOnlyDTO;

/**
 *  No instances
 * */
public class Serializer {


    private Serializer(){

    }
    public static String serializeDTO(MobileContactReadOnlyDTO readOnlyDTO){
        return "ID: " + readOnlyDTO.getId() + " Όνομα: " + readOnlyDTO.getFirstname() + " Επώνυμο: "+ readOnlyDTO.getLastname() + " Τηλ Αριθμός: "+ readOnlyDTO.getPhoneNumber();
    }
}
