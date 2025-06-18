import java.io.FileWriter;
import java.io.IOException;
// import java.io.BufferedReader;
// import java.io.FileReader;
import java.io.BufferedWriter;
import java.time.LocalDateTime;

class Appointment {
    int appointmentID;
    int patientID;
    int doctorID;
    String time;

    public Appointment(int appointmentID, int patientID, int doctorID, String time) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.time = time;
    }
}

public class AppointmentManagement {
    static String diagnosis;
    static String treatment;
    Appointment[] appointment;
    int front;
    int rear;
    int size;
    int capacity;

    public AppointmentManagement(int capacity){
        this.capacity = capacity;
        this.appointment = new Appointment[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    // public void readCSV(){
    //     try (BufferedReader reader = new BufferedReader(new FileReader("AppointmentRecords.csv"))){
    //         String line;
    //         reader.readLine();
    //         while ((line = reader.readLine()) != null){
    //             String[] parts = line.split("\\|");
    //             if (parts.length == 4){
    //                 int appointmentID = Integer.parseInt(parts[0]);
    //                 int patientID = Integer.parseInt(parts[1]);
    //                 int doctorID = Integer.parseInt(parts[2]);
    //                 String time = parts[3];
    //                 Appointment app = new Appointment(appointmentID, patientID, doctorID, time);
    //             }
    //         }
    //     } 
    //     catch (IOException e){
    //         System.out.println("Error reading file: " + e.getMessage());
    //     }
    // }

    // public void rewriteCSV(){
    //     readCSV();
    //     try(BufferedWriter writer = new BufferedWriter(new FileWriter("AppointmentRecords.csv"))){
    //         writer.write("Appointment ID|Patient ID|Doctor ID|Time");
    //         writer.newLine();
    //         for(int i = 0; i < size; i++){
    //             Appointment app = appointment[(front + i) % capacity];
    //             writer.write(app.appointmentID + "|" + app.patientID + "|" + app.doctorID + "|" + app.time);
    //             writer.newLine();
    //         }
    //     } 
    //      catch(IOException e){
    //         System.out.println("Error writing to file: " + e.getMessage());
    //     }
    // }

    public void scheduleAppointment(Appointment element){
        if (size == capacity){
            System.out.println("Appointment queue is full");
            return;
        }
        int pos = size;
        String newStart = element.time.substring(0, 5);
        for (int i = size - 1; i >= 0; i--){
            String currStart = appointment[(front + i) % capacity].time.substring(0, 5);
            if (currStart.compareTo(newStart) > 0){
                appointment[(front + i + 1) % capacity] = appointment[(front + i) % capacity];
                pos = i;
            } 
            else{
                break;
            }
        }
        appointment[(front + pos) % capacity] = element;
        size++;
        rear = (front + size - 1) % capacity;
    }

    public void processNextAppointment(){
        if(size == 0){
            System.out.println("No appointments to process");
            return;
        }
        MainPage.printTab("Appointment ID");
        System.out.println(appointment[front].appointmentID);
        MainPage.printTab("Diagnosis");
        diagnosis = MainPage.input.nextLine();
        MainPage.printTab("Treatment");
        treatment = MainPage.input.nextLine();
        LocalDateTime currentDateTime = LocalDateTime.now();
        String dateTime = currentDateTime.toLocalDate() + " " + currentDateTime.toLocalTime();
        dateTime = dateTime.substring(0, 19);
        MainPage.singleLine();
        System.out.println("[1] Process Appointment\n[2] Go Back to Main Page");
        MainPage.singleLine();
        System.out.print("Please select an option: ");
        int menu = MainPage.input.nextInt();
        MainPage.input.nextLine();
        switch(menu){
            case 1: 
                // int key = appointment[front].doctorID % 1000 - 1;
                // System.out.println(key);
                // for(int i = 0; i < 6; i++){
                //     if(MainPage.finalSchedule[i].equals(appointment[front].time)){
                //         System.out.println(MainPage.finalSchedule[i]);
                //         System.out.println(appointment[front].time);
                //         MainPage.schedules[key][i] = MainPage.finalSchedule[i];
                //         break;
                //     }
                // }
                try(BufferedWriter writer = new BufferedWriter(new FileWriter("CompletedAppointments.csv", true))){
                    writer.write(appointment[front].patientID + "|" + appointment[front].doctorID + "|" + dateTime + "|" + diagnosis + "|" + treatment);
                    writer.newLine();
                } 
                catch(IOException e){
                    System.out.println("Error writing to file: " + e.getMessage());
                }
                Appointment nextAppointment = appointment[front];
                System.out.print("\033[H\033[2J");
                MainPage.printTitle("PROCESSING APPOINTMENT");
                System.out.println("Processing appointment ID: " + nextAppointment.appointmentID + "\nPlease wait...");
                MainPage.doubleLine();
                try{
                    Thread.sleep(2000);
                } 
                catch(InterruptedException e){
                    System.out.println("Error during processing: " + e.getMessage());
                }
                front = (front + 1) % capacity;
                size--;
                // rewriteCSV();
                // appendCSV();
                break;
            case 2: MainPage.mainPage(); break;
            default:
        }
    }

    // public int peek(){
    //     if (size == 0){
    //         System.out.println("No appointments to peek");
    //         return 1;
    //     }
    //     return appointment[front].appointmentID;
    // }

    public void printTab(String str, int space){
        System.out.print(str);
        for (int i = 0; i < space - str.length(); i++) {
            System.out.print(" ");
        }
    }

    public void viewPatientAppointments(int patientID){
        // if (size == 0) {
        //     System.out.println("No upcoming appointments");
        //     return;
        // }
        for (int i = 0; i < size; i++) {
            Appointment app = appointment[(front + i) % capacity];
            if (app.patientID == patientID) {
                printTab(String.valueOf(app.appointmentID), 15);
                System.out.print("| ");
                printTab(String.valueOf(app.doctorID), 10);
                System.out.print("| ");
                System.out.println(app.time);
            }
        }
    }

    public void viewUpcomingAppointments(int doctorID) {
        if (size == 0) {
            System.out.println("No upcoming appointments");
            return;
        }
        // System.out.println("Upcoming Appointments for Doctor ID: " + doctorID);
        System.out.println("Appointment ID | Patient ID | Time");
        MainPage.singleLine();
        for (int i = 0; i < size; i++) {
            Appointment app = appointment[(front + i) % capacity];
            if (app.doctorID == doctorID) {
                printTab(String.valueOf(app.appointmentID), 15);
                System.out.print("| ");
                printTab(String.valueOf(app.patientID), 11);
                System.out.print("| ");
                System.out.println(app.time);
            }
        }
    }
}
