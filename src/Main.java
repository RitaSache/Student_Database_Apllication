/*
 * Rita Sachechelashvili
 * 1928162
 * sache100@mail.chapman.edu
 * CPSC408
 * Purpose of this file is to serve as main file
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.*;

public class Main {
    Connection con;
    public static void displayStudent(Connection con) throws SQLException {
        PreparedStatement pst = con.prepareStatement("SELECT * FROM Student;");
        ResultSet rs2 = pst.executeQuery();

        PrintStudent(rs2);
    }
    //creates student with attributes from user inputs, passes parameters to the prepared MySQL statement and executes query
    public static void createStudent(Connection con) throws SQLException, IOException {
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        PreparedStatement pst = con.prepareStatement("Insert Into Student(FirstName, LastName, GPA, Major, FacultyAdvisor) Values(?,?,?,?,?)");
        System.out.println("What is the first name of the student? ");
        String studentFName = inFromUser.readLine();
        System.out.println("What is the last name of the student? ");
        String studentLName = inFromUser.readLine();
        System.out.println("What is the gpa of the student? ");
        String gpa = inFromUser.readLine();
        System.out.println("What is the major of the student? ");
        String major = inFromUser.readLine();
        System.out.println("Who is the faculty advisor for the student? ");
        String fAdvisor = inFromUser.readLine();

        pst.setString(1, studentFName);
        pst.setString(2, studentLName);
        pst.setBigDecimal(3, new BigDecimal(gpa));
        pst.setString(4, major);
        pst.setString(5, fAdvisor);

        pst.executeUpdate();
        System.out.println("Query successfully executed");

    }
    //updates student with attributes from user inputs, passes parameters to the prepared MySQL statement and executes query
    public static void updateStudent(Connection con) throws SQLException, IOException {
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        PreparedStatement pst = con.prepareStatement("UPDATE Student SET Major = ?, FacultyAdvisor = ? WHERE StudentId = ?;");
        System.out.println("What is the ID of the student to update?");
        String id = inFromUser.readLine();
        System.out.println("What is their new major?");
        String major = inFromUser.readLine();
        System.out.println("Who is their new faculty advisor?");
        String fAdvisor = inFromUser.readLine();
        pst.setString(1, major);
        pst.setString(2, fAdvisor);
        pst.setInt(3, Integer.parseInt(id));

        pst.executeUpdate();
        System.out.println("Query successfully executed");

    }
    //deletes student with student ID from user input, passes parameter to the prepared MySQL statement and executes query
    public static void deleteStudent(Connection con) throws SQLException, IOException {
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        PreparedStatement pst = con.prepareStatement("DELETE FROM Student WHERE StudentId = ?");
        System.out.println("What is the ID of the student to delete?");
        String id = inFromUser.readLine();
        pst.setInt(1, Integer.parseInt(id));

        pst.executeUpdate();
        System.out.println("Query successfully executed");

    }
    //searches and displays student with attributes from user inputs, passes parameters to the prepared MySQL statement and executes query
    public static void searchStudent(Connection con) throws SQLException, IOException {
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        String response;
        System.out.println("Select number from 3 options: \n1. Search students by major \n2. Search students by GPA \n3. Search students by faculty advisor");
        response = inFromUser.readLine();
        PreparedStatement pst = null;
        ResultSet rs2 = null;
        if(response.equals("1")){
            pst = con.prepareStatement("SELECT * FROM Student WHERE Major = ?");
            System.out.println("What is their major?");
            String major = inFromUser.readLine();
            pst.setString(1, major);
        }
        else if(response.equals("2")){
            pst = con.prepareStatement("SELECT * FROM Student WHERE GPA = ?");
            System.out.println("What is their GPA?");
            String gpa = inFromUser.readLine();
            pst.setBigDecimal(1, new BigDecimal(gpa));
        }
        else if(response.equals("3")){
            pst = con.prepareStatement("SELECT * FROM Student WHERE FacultyAdvisor = ?");
            System.out.println("Who is their faculty advisor?");
            String fAdvisor = inFromUser.readLine();
            pst.setString(1, fAdvisor);
        }
        else {
            System.out.println("Invalid input");
        }

        rs2 = pst.executeQuery();
        PrintStudent(rs2);
    }
    //prints student
    public static void PrintStudent(ResultSet rs2) throws SQLException {
        while (rs2.next()) {

            System.out.println("Student Id is " + rs2.getString("StudentId"));
            System.out.println("First name is " + rs2.getString("FirstName"));
            System.out.println("Last name is " + rs2.getString("LastName"));
            System.out.println("GPA is " + rs2.getString("GPA"));
            System.out.println("Major is " + rs2.getString("Major"));
            System.out.println("Faculty Advisor is " + rs2.getString("FacultyAdvisor"));
            System.out.println();
            System.out.println();
        }
    }
    //presents a menu with options that let a user manipulate the database
    public static void main(String[] argv) throws SQLException, IOException {
        Connection con = DbConfig.getMySqlConnection();
        System.out.println("Pick a number from the following options: \n1. Display all students and their attributes \n2. Create Student \n3. Update Student \n4. Delete Student by ID \n5. Search/Display students by Major, GPA and Advisor");
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        String response = inFromUser.readLine();
        if(response.equals("1")) {
            displayStudent(con);
        }
        else if(response.equals("2")){
            createStudent(con);
        }
        else if(response.equals("3")){
            updateStudent(con);
        }
        else if(response.equals("4")){
            deleteStudent(con);
        }
        else if(response.equals("5")){
            searchStudent(con);
        }
        else{
            System.out.println("Input provided is invalid");
        }
    }
}
