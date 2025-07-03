package src;

import java.util.*;

class Student {
    String id;
    String name;
    int age;
    String grade;

    public Student(String id, String name, int age, String grade) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.grade = grade;
    }
}

public class StudentManagementSystem {
    static List<Student> students = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nStudent Management System");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Search Student");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    searchStudent();
                    break;
                case 6:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void addStudent() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter student grade: ");
        String grade = scanner.nextLine();
        
        students.add(new Student(id, name, age, grade));
        System.out.println("Student added successfully!");
    }

    static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students in the system!");
            return;
        }
        
        System.out.println("\nStudent List:");
        System.out.println("------------------------------------------------------------------");
        System.out.printf("%-10s %-20s %-10s %-10s\n", "ID", "Name", "Age", "Grade");
        System.out.println("------------------------------------------------------------------");
        
        for (Student student : students) {
            System.out.printf("%-10s %-20s %-10d %-10s\n", 
                student.id, student.name, student.age, student.grade);
        }
        
        System.out.println("------------------------------------------------------------------");
    }

    static void updateStudent() {
        viewStudents();
        if (students.isEmpty()) return;
        
        System.out.print("Enter student ID to update: ");
        String id = scanner.nextLine();
        
        Student toUpdate = null;
        for (Student student : students) {
            if (student.id.equals(id)) {
                toUpdate = student;
                break;
            }
        }
        
        if (toUpdate == null) {
            System.out.println("Student not found!");
            return;
        }
        
        System.out.print("Enter new name (current: " + toUpdate.name + "): ");
        String name = scanner.nextLine();
        System.out.print("Enter new age (current: " + toUpdate.age + "): ");
        int age = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new grade (current: " + toUpdate.grade + "): ");
        String grade = scanner.nextLine();
        
        toUpdate.name = name;
        toUpdate.age = age;
        toUpdate.grade = grade;
        System.out.println("Student updated successfully!");
    }

    static void deleteStudent() {
        viewStudents();
        if (students.isEmpty()) return;
        
        System.out.print("Enter student ID to delete: ");
        String id = scanner.nextLine();
        
        Student toDelete = null;
        for (Student student : students) {
            if (student.id.equals(id)) {
                toDelete = student;
                break;
            }
        }
        
        if (toDelete == null) {
            System.out.println("Student not found!");
        } else {
            students.remove(toDelete);
            System.out.println("Student deleted successfully!");
        }
    }

    static void searchStudent() {
        System.out.print("Enter student name or ID to search: ");
        String query = scanner.nextLine().toLowerCase();
        
        List<Student> results = new ArrayList<>();
        for (Student student : students) {
            if (student.id.toLowerCase().contains(query) || 
                student.name.toLowerCase().contains(query)) {
                results.add(student);
            }
        }
        
        if (results.isEmpty()) {
            System.out.println("No matching students found!");
            return;
        }
        
        System.out.println("\nSearch Results:");
        System.out.println("------------------------------------------------------------------");
        System.out.printf("%-10s %-20s %-10s %-10s\n", "ID", "Name", "Age", "Grade");
        System.out.println("------------------------------------------------------------------");
        
        for (Student student : results) {
            System.out.printf("%-10s %-20s %-10d %-10s\n", 
                student.id, student.name, student.age, student.grade);
        }
        
        System.out.println("------------------------------------------------------------------");
    }
}