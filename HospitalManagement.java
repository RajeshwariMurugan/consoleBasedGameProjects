package src;

import java.util.*;

class Patient {
    String name;
    int age;
    String gender;
    String condition;

    public Patient(String name, int age, String gender, String condition) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.condition = condition;
    }
}

public class HospitalManagement {
    static List<Patient> patients = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nHospital Management System");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patients");
            System.out.println("3. Discharge Patient");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    viewPatients();
                    break;
                case 3:
                    dischargePatient();
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void addPatient() {
        System.out.print("Enter patient name: ");
        String name = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter medical condition: ");
        String condition = scanner.nextLine();
        
        patients.add(new Patient(name, age, gender, condition));
        System.out.println("Patient added successfully!");
    }

    static void viewPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients in the system.");
            return;
        }
        
        System.out.println("\nPatient List:");
        for (int i = 0; i < patients.size(); i++) {
            Patient p = patients.get(i);
            System.out.printf("%d. %s (%d, %s) - %s\n", 
                i+1, p.name, p.age, p.gender, p.condition);
        }
    }

    static void dischargePatient() {
        viewPatients();
        if (patients.isEmpty()) return;
        
        System.out.print("Enter patient number to discharge: ");
        int patientNum = scanner.nextInt() - 1;
        
        if (patientNum < 0 || patientNum >= patients.size()) {
            System.out.println("Invalid patient number!");
        } else {
            Patient discharged = patients.remove(patientNum);
            System.out.printf("%s has been discharged.\n", discharged.name);
        }
    }
}