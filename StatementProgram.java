import java.sql.*;

public class StatementProgram {
    public static void main(String[] args) {
        String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";        
        String USER = "SYSTEM";
        String PASSWORD = "BCA5C";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Step 1: Open DB Connection
            Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            if (con != null) {
                System.out.println("Connected to MySQL database");
            } else {
                System.out.println("Failed to make connection");
            }

            // Step 2: Create Statement
            Statement stmt = con.createStatement();

            // Step 3: Execute DDL Statements (Creating Tables)
            String sqlD = "CREATE TABLE Department(Did int PRIMARY KEY, Dname VARCHAR(20))";
            String sqlE = "CREATE TABLE Employee(Eid int PRIMARY KEY, Ename VARCHAR(20), Salary FLOAT, Address VARCHAR(20), Did int, FOREIGN KEY (Did) REFERENCES Department(Did))";
            stmt.executeUpdate(sqlD);
            stmt.executeUpdate(sqlE);
            System.out.println("Tables created successfully");

            // Step 4: Insert Data into Tables
            String insertSqlD = "INSERT INTO Department VALUES(1, 'BIG DATA ANALYTICS')";
            String insertSqlE = "INSERT INTO Employee VALUES(140, 'Srushti', 100000.00, 'Hubli', 1)";
            stmt.executeUpdate(insertSqlD);
            stmt.executeUpdate(insertSqlE);
            System.out.println("Data inserted successfully");

            // Step 5: Execute SELECT Queries and Print Results
            String selectSqlE = "SELECT * FROM Employee";
            ResultSet rsE = stmt.executeQuery(selectSqlE);
            System.out.println("Employee Table Data:");
            while (rsE.next()) {
                System.out.println(rsE.getInt("Eid") + ", " + rsE.getString("Ename") + ", " + rsE.getFloat("Salary") + ", " + rsE.getString("Address") + ", " + rsE.getInt("Did"));
            }

            String selectSqlD = "SELECT * FROM Department";
            ResultSet rsD = stmt.executeQuery(selectSqlD);
            System.out.println("Department Table Data:");
            while (rsD.next()) {
                System.out.println(rsD.getInt("Did") + ", " + rsD.getString("Dname"));
            }

            // Step 6: Close the connection
            con.close();
            System.out.println("Connection closed");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}