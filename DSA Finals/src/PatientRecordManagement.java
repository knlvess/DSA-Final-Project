// import java.io.IOException;
// import java.io.BufferedWriter;
// import java.io.FileWriter;
class Patient {
    int id;
    String name;
    int age;
    String address;
    String phone;

    public Patient(int id, String name, int age, String address, String phone){
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.phone = phone;
    }
}

public class PatientRecordManagement {
    static public final String RESET = "\u001B[0m";
    static public final String RED = "\u001B[31m";
    static public final String GREEN = "\u001B[32m";
    
    static class Node{
        Patient patient;
        Node next;

        Node(Patient patient){
            this.patient = patient;
            this.next = null;
        }
    }

    private Node head;

    public Node getHead(){
        return head;
    }

    public void addPatient(Patient patient){
        Node newNode = new Node(patient);
        if(head == null){
            head = newNode;
            return;
        }
        Node current = head;
        while(current.next != null){
            current = current.next;
        }
        current.next = newNode;
    }

    public void removePatientById(int id){
        Node current = head;
        Node previous = null;
        while(current != null && current.patient.id != id){
            previous = current;
            current = current.next;
        }
        if(current == null){
            System.out.println("Patient not found.");
            return;
        }
        if(previous == null){
            head = current.next;
        }
        else{
            previous.next = current.next;
        }
    }

    public Node findPatientById(int id){
        Node current = head;
        while(current != null){
            if(current.patient.id == id){
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public void findPatientByName(String name){
        Node current = head;
        while(current != null){
            if(current.patient.name.equalsIgnoreCase(name)){
                System.out.println("Patient found:");
                MainPage.printTab("ID");
                System.out.println(current.patient.id);
                MainPage.printTab("Full Name");
                System.out.println(current.patient.name);
                MainPage.printTab("Age");
                System.out.println(current.patient.age);
                MainPage.printTab("Address");
                System.out.println(current.patient.address);
                MainPage.printTab("Phone Number");
                System.out.println(current.patient.phone);
                MainPage.singleLine();
                return;
            }
            current = current.next;
        }
        System.out.println(RED + "Patient not found." + RESET);
    }

    public void displayAllPatients(){
        if(head == null){
            System.out.println("No patients found.");
            return;
        }
        Node current = head;
        while(current != null){
            MainPage.printTab("ID");
            System.out.println(current.patient.id);
            MainPage.printTab("Full Name");
            System.out.println(current.patient.name);
            MainPage.printTab("Age");
            System.out.println(current.patient.age);
            MainPage.printTab("Address");
            System.out.println(current.patient.address);
            MainPage.printTab("Phone Number");
            System.out.println(current.patient.phone);
            MainPage.singleLine();
            current = current.next;
        }
    }
}
