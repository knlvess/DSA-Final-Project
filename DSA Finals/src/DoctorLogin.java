// import java.io.BufferedReader;
// import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;

class Doctor {
    int id;
    String name;
    String specialty;
    String loginTime;
    String logoutTime;

    public Doctor(int id, String name, String specialty, String loginTime, String logoutTime) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
    }
}

public class DoctorLogin {
    static public final String RESET = "\u001B[0m";
    static public final String RED = "\u001B[31m";
    static public final String GREEN = "\u001B[32m";

    static class Node{
        Doctor doctor;
        Node next;

        Node(Doctor doctor){
            this.doctor = doctor;
            this.next = null;
        }
    }

    private Node head;

    public void addDoctor(Doctor doctor){
        Node newNode = new Node(doctor);
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

    public void rewriteCSV(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("DoctorRecords.csv"))){
            writer.write("ID|Name|Specialty|Login|Logout");
            writer.newLine();
            Node current = head;
            while(current != null){
                Doctor doctor = current.doctor;
                writer.write(doctor.id + "|" + doctor.name + "|" + doctor.specialty + "|" + doctor.loginTime + "|" + doctor.logoutTime);
                writer.newLine();
                current = current.next;
            }
        }
        catch(IOException e){
            System.out.println("Error writing file.");
        }
    }

    public Doctor findDoctorByID(int id){
        Node current = head;
        while(current != null){
            if(current.doctor.id == id){
                return current.doctor;
            }
            current = current.next;
        }
        return null;
    }

    public void loginDoctor(Doctor doctor){
        if(doctor != null){
            System.out.println(GREEN + doctor.name + " logged in successfully." + RESET);
            rewriteCSV();
            MainPage.redirect(1);
        } else {
            System.out.println(RED + "Doctor not found." + RESET);
        }
        
    }

    public void logoutDoctor(int id){
        Doctor doctor = findDoctorByID(id);
        if(doctor != null && doctor.loginTime != "0"){
            LocalDateTime currentDateTime = LocalDateTime.now();
            String dateTime = currentDateTime.toLocalDate() + " " + currentDateTime.toLocalTime();
            doctor.logoutTime = dateTime.substring(0,19);
            doctor.loginTime = "0";
            rewriteCSV();
            System.out.println(GREEN + doctor.name + " logged out successfully." + RESET);
            MainPage.redirect(2);
        } else {
            System.out.println(RED + "Doctor not found." + RESET);
        }
        
    }

    public void printTab(String str){
        System.out.print(str);
        for(int i = 0; i < 15 - str.length(); i++){
            System.out.print(" ");
        }
        System.out.print("| ");
    }

    public void getAllLoggedInDoctors(){
        Node current = head;
        if(current == null){
            System.out.println("No doctors logged in.");
            return;
        }
        System.out.println("ID   | Name           | Specialty");
        MainPage.singleLine();
        while(current != null){
            if(!current.doctor.loginTime.equals("0")){
                System.out.print(current.doctor.id + " | ");
                printTab(current.doctor.name);
                System.out.println(current.doctor.specialty);
            }
            current = current.next;
        }
    }

    public void getAvailableDoctors(String specialty){
        Node current = head;
        if(current == null){
            System.out.println("No doctors available.");
            return;
        }
        System.out.println("ID   | Name           | Specialty");
        MainPage.singleLine();
        while(current != null){
            if(current.doctor.specialty.equals(specialty)){
                System.out.print(current.doctor.id + " | ");
                printTab(current.doctor.name);
                // System.out.println(current.doctor.specialty);
                if(specialty == "GC"){
                    System.out.println("General Practitioner");
                }
                else{
                    System.out.println("Dentist");
                }
            }
            current = current.next;
        }
    }
}
