import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PreparedStmt {
    public static void main(String[] args) {
        String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";        
        String USER = "SYSTEM";
        String PASSWORD = "BCA5C";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Step 1: Establish the DB connection
            Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            if (con != null) {
                System.out.println("Connected to MySQL DB");
            } else {
                System.out.println("Failed to make connection");
                return;
            }

            // Step 2: Prepare SQL for inserting into Department table
            String insertSqlDepartment = "INSERT INTO Department (Did, Dname) VALUES (?, ?)";
            PreparedStatement pstmtDepartment = con.prepareStatement(insertSqlDepartment);

            // Step 3: Prepare SQL for inserting into Employee table
            String insertSqlEmployee = "INSERT INTO Employee (Eid, Ename, SALARY, ADDRESS, Did) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmtEmployee = con.prepareStatement(insertSqlEmployee);

            Scanner scan = new Scanner(System.in);

            // Step 4: Enter Department Details
            System.out.println("Enter Department details (type Q to quit entering department details):");
            while (true) {
                System.out.println("Do you want to continue entering department details? (Y/N or type Q to quit)");
                String input = scan.next();
                if (input.equalsIgnoreCase("Q")) {
                    break;
                }

                // Input Validation for Department ID
                int Did = -1;
                while (true) {
                    try {
                        System.out.println("Enter Department id (numeric):");
                        Did = scan.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid numeric value for Department id.");
                        scan.next(); // Clear the invalid input
                    }
                }

                System.out.println("Enter Department name:");
                String Dname = scan.next();

                // Insert department data
                pstmtDepartment.setInt(1, Did);
                pstmtDepartment.setString(2, Dname);
                int deptResult = pstmtDepartment.executeUpdate();
                System.out.println("Department record inserted, rows affected: " + deptResult);

                pstmtDepartment.clearParameters(); // Clear parameters for re-use
            }

            // Step 5: Enter Employee Details
            System.out.println("\nNow, enter Employee details (type Q to quit entering employee details):");
            while (true) {
                System.out.println("Do you want to continue entering employee details? (Y/N or type Q to quit)");
                String input = scan.next();
                if (input.equalsIgnoreCase("Q")) {
                    break;
                }

                // Input Validation for Employee ID
                int Eid = -1;
                while (true) {
                    try {
                        System.out.println("Enter Employee id (numeric):");
                        Eid = scan.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid numeric value for Employee id.");
                        scan.next(); // Clear the invalid input
                    }
                }

                System.out.println("Enter Employee name:");
                String Ename = scan.next();

                // Input Validation for Employee Salary
                float Esal = -1;
                while (true) {
                    try {
                        System.out.println("Enter Employee salary (numeric):");
                        Esal = scan.nextFloat();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid numeric value for Employee salary.");
                        scan.next(); // Clear the invalid input
                    }
                }

                System.out.println("Enter Employee address:");
                String Eadd = scan.next();

                // Input Validation for Department ID in Employee
                int Did = -1;
                while (true) {
                    try {
                        System.out.println("Enter Department id (numeric, should already exist):");
                        Did = scan.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid numeric value for Department id.");
                        scan.next(); // Clear the invalid input
                    }
                }

                // Insert employee data
                pstmtEmployee.setInt(1, Eid);
                pstmtEmployee.setString(2, Ename);
                pstmtEmployee.setFloat(3, Esal);
                pstmtEmployee.setString(4, Eadd);
                pstmtEmployee.setInt(5, Did);

                int empResult = pstmtEmployee.executeUpdate();
                System.out.println("Employee record inserted, rows affected: " + empResult);

                pstmtEmployee.clearParameters(); // Clear parameters for re-use
            }

            // Step 6: Close resources
            pstmtDepartment.close();
            pstmtEmployee.close();
            con.close();
            scan.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}