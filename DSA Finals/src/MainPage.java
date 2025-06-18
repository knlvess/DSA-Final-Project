import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
// import java.time.LocalDate;
import java.time.LocalDateTime;
// import java.time.LocalTime;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;

public class MainPage {
    static Scanner input = new Scanner(System.in);
    static public final String RESET = "\u001B[0m";
    static public final String RED = "\u001B[31m";
    static public final String GREEN = "\u001B[32m";
    static PatientRecordManagement prm = new PatientRecordManagement();
    static BST patientBST = new BST(); 
    static DoctorLogin doctorLogin = new DoctorLogin();
    static int role = 0;
    static int doctorID = 0;
    static AppointmentMap appointment = new AppointmentMap(6);
    static String[][] schedules = new String[6][6];
    static int patientID = 0;
    static String[] finalSchedule = new String[6];

    static void readCSV(){
        try(BufferedReader reader = new BufferedReader(new FileReader("PatientRecords.csv"))){
            reader.readLine();
            String line;
            while((line = reader.readLine()) != null){
                String[] p = line.split("\\|");
                prm.addPatient(new Patient(Integer.parseInt(p[0].trim()), p[1].trim(), Integer.parseInt(p[2].trim()), p[3].trim(), p[4].trim()));
                patientBST.insertPatient(new Patient(Integer.parseInt(p[0].trim()), p[1].trim(), Integer.parseInt(p[2].trim()), p[3].trim(), p[4].trim()));
            }
            // System.out.println("Data loaded");
        }
        catch(Exception e){
            System.out.println("Error.");
        }

        try(BufferedReader reader = new BufferedReader(new FileReader("DoctorRecords.csv"))){
            reader.readLine();
            String line;
            while((line = reader.readLine()) != null){
                String[] data = line.split("\\|");
                if(data.length == 5){
                    int id = Integer.parseInt(data[0]);
                    String name = data[1];
                    String specialty = data[2];
                    String loginTime = data[3];
                    String logoutTime = data[4];
                    doctorLogin.addDoctor(new Doctor(id, name, specialty, loginTime, logoutTime));
                }
            }
        }
        catch(IOException e){
            System.out.println("Error reading file.");
        }
        try(BufferedReader reader = new BufferedReader(new FileReader("Schedule.csv"))){
            String line;
            int i = 0;
            while((line = reader.readLine()) != null && i < 6){
                String[] schedule = line.split("\\|");
                for(int j = 0; j < schedule.length; j++){
                    schedules[i][j] = schedule[j].trim();
                }
                i++;
            }
        }
        catch(IOException e){
            System.out.println("Error reading file.");
        }
        try(BufferedReader reader = new BufferedReader(new FileReader("AppointmentRecords.csv"))){
            reader.readLine();
            String line;
            while((line = reader.readLine()) != null){
                String[] data = line.split("\\|");
                if(data.length == 4){
                    int appointmentID = Integer.parseInt(data[0].trim());
                    int patientID = Integer.parseInt(data[1].trim());
                    int doctorID = Integer.parseInt(data[2].trim());
                    String time = data[3].trim();
                    int key = doctorID % 1000 - 1;
                    AppointmentManagement appointmentManagement = appointment.get(key);
                    if(appointmentManagement == null){
                        appointmentManagement = new AppointmentManagement(6);
                    }
                    appointmentManagement.scheduleAppointment(new Appointment(appointmentID, patientID, doctorID, time));
                    appointment.put(key, appointmentManagement);
                }
            }
        }
        catch(IOException e){
            System.out.println("Error reading file.");
        }
    }

    static void appendCSV(String patient){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("PatientRecords.csv", true))){
            writer.write(patient);
            writer.newLine();
        }
        catch(IOException e){
            System.out.println("Error.");
        }
    }

    static void rewriteCSV(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("PatientRecords.csv"))){
            writer.write("ID|Name|Age|Address|Phone");
            writer.newLine();
            PatientRecordManagement.Node current = prm.getHead();
            while(current != null){
                writer.write(current.patient.id + "|" + current.patient.name + "|" + current.patient.age + "|" + current.patient.address + "|" + current.patient.phone);
                writer.newLine();
                current = current.next;
            }
        }
        catch(IOException e){
            System.out.println("Error.");
        }
    }

    static void rewriteAppointmentCSV(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("AppointmentRecords.csv"))){
            writer.write("Appointment ID|Patient ID|Doctor ID|Time");
            writer.newLine();
            for (int i = 0; i < 6; i++){
                AppointmentManagement am = appointment.get(i);
                if (am != null){
                    for (int j = 0; j < am.size; j++){
                        Appointment app = am.appointment[(am.front + j) % am.capacity];
                        writer.write(app.appointmentID + "|" + app.patientID + "|" + app.doctorID + "|" + app.time);
                        writer.newLine();
                    }
                }
            }
        } 
        catch (IOException e){
            System.out.println("Error writing appointment file: " + e.getMessage());
        }
    }

    static void rewriteScheduleCSV(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("Schedule.csv"))){
            for(int i = 0; i < schedules.length; i++){
                for(int j = 0; j < schedules[i].length; j++){
                    if(j == schedules[i].length - 1){
                        writer.write(schedules[i][j]);
                    }
                    else{
                        writer.write(schedules[i][j] + "|");
                    }
                }
                writer.newLine();
            }
        }
        catch(IOException e){
            System.out.println("Error writing schedule file.");
        }
    }

    static void initialize(){
        readCSV();
        finalSchedule[0] = "08:00 - 08:30";
        finalSchedule[1] = "08:30 - 09:00";
        finalSchedule[2] = "09:00 - 09:30";
        finalSchedule[3] = "17:00 - 17:30";
        finalSchedule[4] = "17:30 - 18:00";
        finalSchedule[5] = "18:00 - 18:30";
    }

    static void redirect(int code){
        try{
            System.out.print("Redirecting to ");
            switch(code){
                case 1: 
                    System.out.println("Main Menu...");
                    doubleLine();
                    Thread.sleep(3000);
                    mainPage(); 
                    break;
                case 2:
                    System.out.println("Login Page...");
                    doubleLine();
                    Thread.sleep(3000);
                    loginPage();
                    break;
                default: defaultOutput(); return;
            }
            
        }
        catch(InterruptedException e){}
    }

    static void doubleLine(){
        System.out.println("=======================================================");
    }

    static void singleLine(){
        System.out.println("-------------------------------------------------------");
    }

    static void printTab(String str){
        System.out.print(str);
        for(int i = 0; i < 15 - str.length(); i++){
            System.out.print(" ");
        }
        System.out.print(": ");
    }

    static void printTitle(String title){
        doubleLine();
        for(int i = 0; i < (55 - title.length()) / 2; i++){
            System.out.print(" ");
        }
        System.out.println(title);
        doubleLine();
    }

    static void defaultOutput(){
        System.out.print("\033[H\033[2J");
        printTitle("INVALID INPUT");
        System.out.println(RED + "Invalid input." + RESET);
        if(role == 0){
            redirect(2);
        }
        else{
            redirect(1);
        }
    }

    static void patientFound(int id){
        PatientRecordManagement.Node patient = prm.findPatientById(id);
        System.out.println("Patient found: ");
        printTab("ID");
        System.out.println(patient.patient.id);
        printTab("Full Name");
        System.out.println(patient.patient.name);
        printTab("Age");
        System.out.println(patient.patient.age);
        printTab("Address");
        System.out.println(patient.patient.address);
        printTab("Phone Number");
        System.out.println(patient.patient.phone);
        singleLine();
    }

     // New method to display patient found from BST
    static void patientFoundBST(Patient patient){
        System.out.println("Patient found: ");
        printTab("ID");
        System.out.println(patient.id);
        printTab("Full Name");
        System.out.println(patient.name);
        printTab("Age");
        System.out.println(patient.age);
        printTab("Address");
        System.out.println(patient.address);
        printTab("Phone Number");
        System.out.println(patient.phone);
        singleLine();
    }

    static void addNewPatient(){
        System.out.print("\033[H\033[2J");
        printTitle("ADD NEW PATIENT");
        printTab("Full Name");
        String name = input.nextLine();
        printTab("Age");
        int age = input.nextInt();
        input.nextLine();
        printTab("Address");
        String address = input.nextLine();
        printTab("Phone Number");
        String phone = input.nextLine();
        int lastID = 0;;
        PatientRecordManagement.Node current = prm.getHead();
        while(current != null){
            if(current.patient.id > lastID){
                lastID = current.patient.id;
            }
            current = current.next;
        }
        singleLine();
        System.out.println("[1] Add Patient\n[2] Cancel");
        singleLine();
        System.out.print("Please select an option: ");
        int menu = input.nextInt();
        input.nextLine();
        System.out.print("\033[H\033[2J");
        switch(menu){
            case 1: 
                printTitle("SUCCESS");
                Patient newPatient = new Patient(lastID + 1, name, age, address, phone);
                prm.addPatient(newPatient);
                patientBST.insertPatient(newPatient);
                appendCSV(lastID + 1 + "|" + name + "|" + age + "|" + address + "|" + phone);
                System.out.println(GREEN + "Patient added successfully." + RESET);
                break;
            case 2: 
                printTitle("CANCELLED");
                System.out.println("Patient registration cancelled.");
                break;
            default: defaultOutput(); return;
        }
        redirect(1);
    }

    static void removePatient(){
        System.out.print("\033[H\033[2J");
        printTitle("REMOVE PATIENT");
        System.out.print("Patient ID: ");
        int id = input.nextInt();
        input.nextLine();
        // prm.displayAllPatients();
        if(prm.findPatientById(id) != null){
            patientFound(id);
            System.out.println("[1] Remove Patient\n[2] Cancel");
            singleLine();
            System.out.print("Please select an option: ");
            int menu = input.nextInt();
            input.nextLine();
            System.out.print("\033[H\033[2J");
            switch(menu){
                case 1: 
                    printTitle("SUCCESS");
                    prm.removePatientById(id);
                    patientBST.removePatient(id);
                    rewriteCSV();
                    System.out.println(GREEN + "Patient removed successfully." + RESET);
                    break;
                case 2: 
                    printTitle("CANCELLED");
                    System.out.println("Patient removal cancelled.");
                    break;
                default: defaultOutput(); return;
            }
            redirect(1);
        }
        else{
            System.out.println(RED + "Patient not found." + RESET);
        }
    }

    static void searchPatient(int id){
        if(prm.findPatientById(id) != null){
            patientFound(id);
            System.out.println("[1] Search Again\n[2] Main Menu");
            singleLine();
            System.out.print("Please select an option: ");
            int menu = input.nextInt();
            input.nextLine();
            switch(menu){
                case 1: searchPatientPage(); break;
                case 2: mainPage(); break;
                default: defaultOutput(); return;
            }
        }
        else{
            System.out.println(RED + "Patient not found." + RESET);
        }
    }

    static void searchPatientBST(int id){
        Patient patient = patientBST.searchPatient(id);
        if(patient != null){
            patientFoundBST(patient);
            System.out.println("[1] Search Again\n[2] Main Menu");
            singleLine();
            System.out.print("Please select an option: ");
            int menu = input.nextInt();
            input.nextLine();
            switch(menu){
                case 1: searchPatientBSTPage(); break;
                case 2: mainPage(); break;
                default: defaultOutput(); return;
            }
        }
        else{
            System.out.println(RED + "Patient not found." + RESET);
            System.out.println("[1] Search Again\n[2] Main Menu");
            singleLine();
            System.out.print("Please select an option: ");
            int menu = input.nextInt();
            input.nextLine();
            switch(menu){
                case 1: searchPatientBSTPage(); break;
                case 2: mainPage(); break;
                default: defaultOutput(); return;
            }
        }
    }

    static void searchByName(){
        System.out.print("\033[H\033[2J");
        printTitle("SEARCH PATIENT");
        System.out.print("Patient Name: ");
        String name = input.nextLine();
        prm.findPatientByName(name);
        // singleLine();
        System.out.println("[1] Search Again\n[2] Main Menu");
        singleLine();
        System.out.print("Please select an option: ");
        int menu = input.nextInt();
        input.nextLine();
        switch(menu){
            case 1: searchByName(); break;
            case 2: mainPage(); break;
            default: defaultOutput(); return;
        }
    }

    static void searchPatientPage(){
        System.out.print("\033[H\033[2J");
        printTitle("SEARCH PATIENT");
        System.out.print("Patient ID: ");
        int id = input.nextInt();
        input.nextLine();
        searchPatient(id);
    }

    static void searchPatientBSTPage(){
        System.out.print("\033[H\033[2J");
        printTitle("SEARCH PATIENT (BST - FAST SEARCH)");
        System.out.print("Patient ID: ");
        int id = input.nextInt();
        input.nextLine();
        searchPatientBST(id);
    }

    static void displayAllPatients(){
        System.out.print("\033[H\033[2J");
        printTitle("REGISTERED PATIENTS");
        prm.displayAllPatients();
        // singleLine();
        System.out.println("[1] Search Patient by ID\n[2] Search Patient by Name\n[3] Main Menu");
        singleLine();
        System.out.print("Please select an option: ");
        int menu = input.nextInt();
        input.nextLine();
        switch(menu){
            case 1: searchPatientPage(); break;
            case 2: searchByName(); break;
            case 3: mainPage(); break;
            default: defaultOutput(); return;
        }
    }

    static void displayAllPatientsBST(){
        System.out.print("\033[H\033[2J");
        printTitle("REGISTERED PATIENTS (BST - SORTED BY ID)");
        patientBST.inOrderDisplay();
        // singleLine();
        System.out.println("[1] Search Patient by ID\n[2] Search Patient by Name\n[3] Main Menu");
        singleLine();
        System.out.print("Please select an option: ");
        int menu = input.nextInt();
        input.nextLine();
        switch(menu){
            case 1: searchPatientPage(); break;
            case 2: searchByName(); break;
            case 3: mainPage(); break;
            default: defaultOutput(); return;
        }
    }

    static void doctorFound(int id){
        Doctor doctor = doctorLogin.findDoctorByID(id);
        if(doctor != null){
            System.out.println(GREEN  + "Doctor found: " + RESET);
            printTab("ID");
            System.out.println(doctor.id);
            printTab("Full Name");
            System.out.println(doctor.name);
            printTab("Specialty");
            System.out.println(doctor.specialty);
            singleLine();
        }
        else{
            System.out.println(RED + "Doctor not found." + RESET);
        }
    }

    static void doctorLogin(){
        System.out.print("\033[H\033[2J");
        printTitle("DOCTOR LOGIN");
        System.out.print("Doctor ID: ");
        doctorID = input.nextInt();
        input.nextLine();
        Doctor doctor = doctorLogin.findDoctorByID(doctorID);
        if(doctor != null){
            doctorFound(doctorID);
            System.out.println("[1] Login\n[2] Cancel");
            singleLine();
            System.out.print("Please select an option: ");
            int menu = input.nextInt();
            input.nextLine();
            System.out.print("\033[H\033[2J");
            switch(menu){
                case 1:
                    printTitle("SUCCESS");
                    LocalDateTime currentDateTime = LocalDateTime.now();
                    String dateTime = currentDateTime.toLocalDate() + " " + currentDateTime.toLocalTime();
                    dateTime = dateTime.substring(0, 19);
                    doctor.loginTime = dateTime;
                    doctorLogin.loginDoctor(doctor);
                    break;
                case 2: 
                    printTitle("CANCELLED");    
                    System.out.println("Doctor login cancelled.");
                    redirect(2);
                    break;
                default: defaultOutput(); return;
            }
            
        }
        else{
            System.out.println(RED + "Doctor not found." + RESET);
        }
    }

    static void doctorLogout(){
        System.out.print("\033[H\033[2J");
        printTitle("DOCTOR LOGOUT");
        System.out.println("[1] Logout\n[2] Cancel");
        singleLine();
        System.out.print("Please select an option: ");
        int menu = input.nextInt();
        input.nextLine();
        System.out.print("\033[H\033[2J");
        switch(menu){
            case 1: 
                printTitle("SUCCESS");
                role = 0;
                doctorLogin.logoutDoctor(doctorID);
                break;
            case 2: 
                printTitle("CANCELLED");
                System.out.println("Doctor logout cancelled.");
                redirect(2);
                break;
            default: defaultOutput(); return;
        }
    }

    static void viewLastLoggedInDoctor(){
        System.out.print("\033[H\033[2J");
        printTitle("LAST LOGGED-IN DOCTOR");
        doctorLogin.getAllLoggedInDoctors();
        singleLine();
        System.out.println("[1] Main Menu");
        singleLine();
        System.out.print("Please select an option: ");
        int menu = input.nextInt();
        input.nextLine();
        switch(menu){
            case 1: mainPage(); break;
            default: defaultOutput(); return;
        }
    }

    static void scheduleAppointment(){
        System.out.print("\033[H\033[2J");
        printTitle("POLYCLINIC");
        // System.out.println("Polyclinic:");
        System.out.println("[1] General Clinic\n[2] Dental Clinic");
        singleLine();
        System.out.print("Please select a polyclinic: ");
        int polyclinic = input.nextInt();
        input.nextLine();
        String specialty;
        switch(polyclinic){
            case 1: specialty = "GC"; break;
            case 2: specialty = "DC"; break;
            default: defaultOutput(); return; 
                // System.out.println(RED + "Invalid polyclinic." + RESET);
                // return;
        }
        System.out.print("\033[H\033[2J");
        printTitle("AVAILABLE DOCTORS");
        // System.out.println("Available Doctors: ");
        doctorLogin.getAvailableDoctors(specialty);
        singleLine();
        System.out.print("Please select a doctor by ID: ");
        int doctorID = input.nextInt();
        input.nextLine();
        Doctor doctor = doctorLogin.findDoctorByID(doctorID);
        if(doctor == null){
            System.out.println(RED + "Doctor not found." + RESET);
            return;
        }
        int key = doctorID % 1000;
        System.out.print("\033[H\033[2J");
        printTitle("SCHEDULE APPOINTMENT");
        System.out.println("Available Schedules:");
        // System.out.println(key);
        for(int i = 0; i < schedules.length; i++){
            if(!schedules[key - 1][i].equals("0")){
                System.out.println("[" + (i+1) + "] " + schedules[key - 1][i]);
            }
            else{
                System.out.println("[" + (i+1) + "] " + RED + finalSchedule[i] + " (Unavailable)" + RESET);
            }
            
        }
        singleLine();
        System.out.print("Please select a schedule: ");
        int schedule = input.nextInt();
        input.nextLine();
        if(schedule < 1 || schedule > 6){
            System.out.println(RED + "Invalid schedule." + RESET);
            return;
        }
        if(schedules[key - 1][schedule - 1].equals("0")){
            System.out.println(RED + "Schedule not available." + RESET);
            return;
        }
        
        System.out.print("\033[H\033[2J");
        printTitle("SCHEDULE APPOINTMENT");
        printTab("Patient ID");
        System.out.println(patientID);
        printTab("Polyclinic");
        System.out.println(polyclinic == 1 ? "General Clinic" : "Dental Clinic");
        printTab("Doctor");
        System.out.println(doctor.name);
        printTab("Schedule");
        System.out.println(finalSchedule[schedule - 1]);
        singleLine();
        System.out.println("[1] Confirm Appointment\n[2] Cancel");
        singleLine();
        System.out.print("Please select an option: ");
        int menu = input.nextInt();
        input.nextLine();
        System.out.print("\033[H\033[2J");
        switch(menu){
            case 1: 
                printTitle("SUCCESS");
                int number = 0;
                for(int i = 0; i < 6; i++){
                    if(schedules[key - 1][i].equals("0")){
                        number++;
                        // break;
                    }
                }
                int appointmentID = key * 1000 + number + 1;
                AppointmentManagement appointmentManagement = appointment.get(key - 1);
                if(appointmentManagement == null){
                    appointmentManagement = new AppointmentManagement(6);
                }
                appointmentManagement.scheduleAppointment(new Appointment(appointmentID, patientID, doctorID, schedules[key - 1][schedule - 1]));
                appointment.put(key - 1, appointmentManagement);
                rewriteAppointmentCSV();
                // appointmentManagement.rewriteCSV();
                schedules[key - 1][schedule - 1] = "0";
                rewriteScheduleCSV();
                System.out.println(GREEN + "Appointment scheduled successfully." + RESET);
                redirect(1);
                break;
            case 2: 
                printTitle("CANCELLED");
                System.out.println("Appointment scheduling cancelled.");
                redirect(1);
                break;
            default: defaultOutput(); return;
        }
    }

    static void processAppointment(){
        System.out.print("\033[H\033[2J");
        printTitle("PROCESS APPOINTMENT");
        int key = doctorID % 1000 - 1;
        AppointmentManagement appointmentManagement = appointment.get(key);
        if(appointmentManagement == null || appointmentManagement.size == 0){
            System.out.println(RED + "No appointments to process." + RESET);
            redirect(1);
            return;
        }
        String time = appointmentManagement.appointment[appointmentManagement.front].time;
        LocalDateTime currentDateTime = LocalDateTime.now();
        String currentTime = currentDateTime.toLocalDate() + " " + currentDateTime.toLocalTime();
        currentTime = currentTime.substring(10, 16).replace(":", "").trim();
        int currentHour = Integer.parseInt(currentTime.startsWith("0") ? currentTime.substring(1) : currentTime);
        String startTimeStr = time.substring(0, 5).replace(":", "");
        int startHour = Integer.parseInt(startTimeStr.startsWith("0") ? startTimeStr.substring(1) : startTimeStr);
        String endTimeStr = time.substring(7, 13).replace(":", "").trim();
        int endHour = Integer.parseInt(endTimeStr.startsWith("0") ? endTimeStr.substring(1) : endTimeStr);
        // System.out.println(currentHour);
        // System.out.println(startHour);
        // System.out.println(endHour);
        if(currentHour < startHour || currentHour > endHour){
            System.out.println(RED + "Cannot process appointment at this time." + RESET);
            redirect(1);
            return;
        }
        appointmentManagement.processNextAppointment();
        rewriteAppointmentCSV();
        for(int i = 0; i < 6; i++){
            if(finalSchedule[i].equals(time)){
                // System.out.println(finalSchedule[i]);
                schedules[key][i] = finalSchedule[i];
                break;
            }
        }
        rewriteScheduleCSV();
        System.out.print("\033[H\033[2J");
        printTitle("SUCCESS");
        System.out.println(GREEN + "Appointment processed successfully." + RESET);
        singleLine();
        System.out.println("[1] Process Next Appointment\n[2] Main Menu");
        singleLine();
        System.out.print("Please select an option: ");
        int menu = input.nextInt();
        input.nextLine();
        switch(menu){
            case 1: processAppointment(); break;
            case 2: mainPage(); break;
            default: defaultOutput(); return;
        }
        // System.out.println("Success");
    }

    static void displayAppointments(){
        System.out.print("\033[H\033[2J");
        printTitle("UPCOMING APPOINTMENTS");
        if(role == 1){
            // System.out.println("Upcoming Appointments for Patient ID: " + patientID);
            // singleLine();
            System.out.println("Appointment ID | Doctor ID | Time");   
            singleLine();
            for(int i = 0; i < 6; i++){
                AppointmentManagement appointmentManagement = appointment.get(i);
                if(appointmentManagement != null){
                    appointmentManagement.viewPatientAppointments(patientID);
                }
            }
        }
        else if(role == 2){
            int key = doctorID % 1000 - 1;
            AppointmentManagement appointmentManagement = appointment.get(key);
            appointmentManagement.viewUpcomingAppointments(doctorID);
        }
        else{

        }
        singleLine();
        System.out.println("[1] Main Menu");
        singleLine();
        System.out.print("Please select an option: ");
        int menu = input.nextInt();
        input.nextLine();
        switch(menu){
            case 1: mainPage(); break;
            default: defaultOutput(); return;
        }
    }

    static void mainPage(){
        System.out.print("\033[H\033[2J");
        printTitle("AMBALABU CLINIC");
        switch(role){
            case 1: 
                System.out.println("[1] Schedule Appointment\n[2] Display Upcoming Appointments\n[3] Login Menu\n[0] Exit");
                break;
            case 2:
                System.out.println("[1] Process Appointment\n[2] Display Upcoming Appointments\n[3] Login Menu\n[4] Logout\n[0] Exit");
                break;
            case 3: 
                System.out.println("[1] Add New Patient\n[2] Remove Patient by ID\n[3] Search Patient by Name\n[4] Display All Patients");
                System.out.println("[5] View Last Logged-in Doctor\n[6] Search Patient by ID\n[7] Display All Patients\n[8] Login Menu\n[0] Exit");
        }
        // System.out.println("[1] Add New Patient\n[2] Remove Patient by ID\n[3] Search Patient by Name");
        // System.out.println("[4] Display All Patients\n[5] Doctor login\n[6] Doctor Logout");
        // System.out.println("[7] View Last Logged-in Doctor\n[8] Schedule Appointment\n[9] Process Appointment");
        // System.out.println("[10] Display Upcoming Appointments\n[11] Search Patient by ID\n[12] Display All Patients\n[0] Exit");
        singleLine();
        System.out.print("Please select an option: ");
        int menu = input.nextInt();
        input.nextLine();
        switch(menu){
            case 0: System.exit(0); break;
            case 1: 
                switch(role){
                    case 1: scheduleAppointment(); break;
                    case 2: processAppointment(); break;
                    case 3: addNewPatient(); break;
                    default: defaultOutput(); return;
                } break;
            case 2: 
                switch(role){
                    case 1: displayAppointments(); break;
                    case 2: displayAppointments(); break;
                    case 3: removePatient(); break;
                    default: defaultOutput(); return;
                } break;
            case 3: 
                switch(role){
                    case 1: role = 0; loginPage(); break;
                    case 2: role = 0; loginPage(); break;
                    case 3: searchByName(); break;
                    default: defaultOutput(); return;
                } break;
            case 4: 
                switch(role){
                    case 2: doctorLogout(); break;
                    case 3: displayAllPatients(); break;
                    default: defaultOutput(); return;
                } break;
            case 5: 
                switch(role){
                    case 3: viewLastLoggedInDoctor(); break;
                    default: defaultOutput(); return;
                }
            case 6: 
                switch(role){
                    case 3: searchPatientBSTPage(); break;
                    default: defaultOutput(); return;
                } break;
            case 7: 
                switch(role){
                    case 3: displayAllPatientsBST(); break;
                    default: defaultOutput(); return;
                } break;
            case 8:
                switch(role){
                    case 3: loginPage(); break;
                    default: defaultOutput(); return;
                } break;
            default: defaultOutput(); return;
        }
    }

    static void login(){
        switch(role){
            case 1: 
                System.out.print("\033[H\033[2J");
                printTitle("PATIENT LOGIN");
                printTab("Patient ID");
                patientID = input.nextInt();
                input.nextLine();
                printTab("Phone Number");
                String phone = input.nextLine();
                PatientRecordManagement.Node patientNode = prm.findPatientById(patientID);
                System.out.print("\033[H\033[2J");
                if(patientNode != null && patientNode.patient.phone.equals(phone)){
                    printTitle("SUCCESS");
                    System.out.println(GREEN + "Login successful." + RESET);
                    redirect(1);
                }
                else{
                    printTitle("LOGIN FAILED");
                    System.out.println(RED + "Invalid ID or Phone Number." + RESET);
                    redirect(2);
                }
                break;
            case 2: 
                doctorLogin();
                break;
            case 3:
                System.out.print("\033[H\033[2J");
                printTitle("ADMIN LOGIN");
                redirect(1);
                break;
            default: role = 0; defaultOutput(); return;
        }
    }

    static void loginPage(){
        System.out.print("\033[H\033[2J");
        printTitle("AMBALABU CLINIC (LOGIN PAGE)");
        // System.out.println("Welcome to Ambalabu Clinic!");
        // singleLine();
        System.out.println("Login as:");
        System.out.println("[1] Patient\n[2] Doctor\n[3] Admin");
        singleLine();
        System.out.print("Please select an option: ");
        role = input.nextInt();
        input.nextLine();
        login();
    }
}
