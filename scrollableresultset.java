import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class scrollableresultset{
    public static void main(String[] args) {
        String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";        
        String USER = "SYSTEM";
        String PASSWORD = "BCA5C";
        //step:1 Register the driver class
        try{
        Class.forName("oracle.jdbc.driver.OracleDriver");
        //step:2 Open DB Connection
        Connection con = DriverManager.getConnection(DB_URL,USER,PASSWORD);
        if(con != null){
            System.out.println("Connected to oracle db");
        }else{
            System.out.println("Failed to make connection");
        }
    // step3 
    Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    ResultSet rs = stmt.executeQuery("SELECT * FROM Employee");
    System.out.println(rs);

    System.out.println("------------------Move forwords ----------------");
    while(rs.next()){
System.out.println("Printing employee data in forward direction");
System.out.println(rs.getInt(1)+ " "+ rs.getString(2)+ " "+ rs.getFloat(3) +" "+ rs.getString(4) +" "+ rs.getInt(5));
    }
    
    System.out.println("------------------Move backwards ----------------");
    rs.last(); // move to last record
    while(rs.previous()){
System.out.println("Printing employee data in backward direction");
System.out.println(rs.getInt(1)+ " "+ rs.getString(2)+ " "+ rs.getFloat(3) +" "+ rs.getString(4) +" "+ rs.getInt(5));
    }
    rs.absolute(2);
    System.out.println(rs.getInt(1)+ " "+ rs.getString(2)+ " "+ rs.getFloat(3) +" "+ rs.getString(4) +" "+ rs.getInt(5));
     con.close();
}   
    catch(Exception e){
        System.out.println(e);
    }
}
}