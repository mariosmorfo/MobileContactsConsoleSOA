package gr.aueb.cf.mobilecontacts;

import gr.aueb.cf.mobilecontacts.controller.MobileContactController;
import gr.aueb.cf.mobilecontacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactUpdateDTO;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner in = new Scanner(System.in);
    private static final MobileContactController controller = new MobileContactController();
    public static void main(String[] args) {
        String choice;

        while (true) {
            printMenu();
            choice = getToken();

                if(choice.equals("Q") || (choice.equals("q"))) {
                    System.out.println("Έξοδος...");
                    break;
                }
                handleChoice(choice);
        }
        System.out.println();
    }

    public static void handleChoice(String choice){
        String firstname;
        String lastname;
        String phoneNumber;
        String response;

        switch (choice) {
            case "1":
                System.out.println("Παρακαλώ εισάγετε Όνομα, Επώνυμο, Αριθμό Τηλ.");
                firstname = getToken();
                lastname = getToken();
                phoneNumber = getToken();
                MobileContactInsertDTO insertDTO = new MobileContactInsertDTO(firstname, lastname, phoneNumber);
                response = controller.insertContact(insertDTO);

                if(response.startsWith("OK")) {
                    System.out.println("Επιτυχής εισαγωγή");
                    System.out.println(response.substring(3));
                } else {
                    System.out.println("Ανεπιτυχής εισαγωγή");
                    System.out.println(response.substring(7));
                }
                break;

            case "2" :
                System.out.println("Εισάγεται Αριθμό τηλεφώνου");
                phoneNumber = getToken();
                response = controller.getContactByPhoneNumber(phoneNumber);
                if(response.startsWith("Error")){
                    System.out.println("Η επαφή δεν βρέθηκε");
                    System.out.println(response.substring(3));
                    return;
                }

                System.out.println("Ανεπιτυχής εισαγωγή");
                System.out.println(response.substring(6));
                System.out.println("Εισάγεται το υπάρχον ID");
                long oldId = Long.parseLong(getToken());
                System.out.println("Παρακαλώ εισάγετε νέο όνομα");
                firstname = getToken();
                System.out.println("Παρακαλώ εισάγετε νέο επίθετο");
                lastname = getToken();
                System.out.println("Παρακαλώ εισάγετε νέο τηλεφωνικό αριθμό");
                phoneNumber = getToken();
                MobileContactUpdateDTO mobileContactUpdateDTO = new MobileContactUpdateDTO(oldId, firstname, lastname, phoneNumber);
                response = controller.updateContact(mobileContactUpdateDTO);
                System.out.println(response);
                break;

            case "3":
                System.out.println("Εισάγετε κωδικό επαφής");
                Long id = Long.parseLong(getToken());
                response = controller.deleteContactById(id);
                if(response.startsWith("OK")){
                    System.out.println("Επιτυχής διαγραφή");
                    System.out.println(response.substring(3));
                }else{
                    System.out.println("Ανεπιτυχής Διαγραφή");
                    System.out.println(response.substring(6));
                }
                break;

            case "4" :
                System.out.println("Εισάγετε κωδικό επαφής");
                id = Long.parseLong(getToken());
                response = controller.getContactById(id);
                if(response.startsWith("OK")){
                    System.out.println("Επιτυχής αναζήτηση");
                    System.out.println(response.substring(3));
                }else{
                    System.out.println("Αποτυχής αναζήτηση");
                    System.out.println(response.substring(6));
                }

                break;

            case "5" :
                List<String> mobileContacts = controller.getAllContacts();
                if(mobileContacts.isEmpty()){
                    System.out.println("Κενή λίστα");
                }
                mobileContacts.forEach(System.out::println);
                break;

            default:
                System.out.println("Λάθος Επιλογή");
                break;
        }
    }

    public static void printMenu(){
        System.out.println("Επιλέξτε ένα απο τα παρακάτω");
        System.out.println("1. Εισαγωγή επαφής");
        System.out.println("2. Ενημέρωση επαφής");
        System.out.println("3. Διαγραφή επαφής");
        System.out.println("4. Αναζήτηση επαφης");
        System.out.println("5. Προβολή επαφής");
        System.out.println("Q.. Έξοδος");
    }

    public static String getToken(){
        return in.nextLine().trim();
    }
}
