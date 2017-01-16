/**
 * Created by Work on 1/15/2017.
 */

import org.sqlite.core.DB;

import java.sql.*;


public class SQLiteDB {

    public final String DBName;
    private Connection c;

    public SQLiteDB(String DBName){
        this.DBName = DBName;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DBName + ".db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public void createTable(){

        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");

            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS LAPTOPS " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " BRANDNAME           TEXT    NOT NULL, " +
                    " PROCSPEED            DOUBLE     NOT NULL, " +
                    " RAM        INT, " +
                    " DISKCAP         INT)";
            stmt.executeUpdate(sql);
            stmt.close();


        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    public void insert(Laptop laptop){

        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");

            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            int maxID = getMaxID(c);

            String query = "INSERT INTO LAPTOPS VALUES(?,?,?,?,?)";

            PreparedStatement prepSt = c.prepareStatement(query);

            prepSt.setInt(1,maxID+1);
            prepSt.setString(2,laptop.getBrand());
            prepSt.setDouble(3,laptop.getProcSpeed());
            prepSt.setInt(4,laptop.getMemory());
            prepSt.setInt(5,laptop.getHdd());

            prepSt.executeUpdate();

            c.commit();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    private int getMaxID(Connection c) throws SQLException {
        int maxId = 1;

        String query = "SELECT MAX(id) as id FROM LAPTOPS";

        PreparedStatement pst = c.prepareStatement(query);
        ResultSet rs = pst.executeQuery();

        while( rs.next() ) {
            maxId = rs.getInt("id");
        }

        return maxId;
    }


    public void select(){

        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");

            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM LAPTOPS;" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  name = rs.getString("name");
                int age  = rs.getInt("age");
                String  address = rs.getString("address");
                float salary = rs.getFloat("salary");
                System.out.println( "ID = " + id );
                System.out.println( "NAME = " + name );
                System.out.println( "AGE = " + age );
                System.out.println( "ADDRESS = " + address );
                System.out.println( "SALARY = " + salary );
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }


    public void selectAll(){

        Statement s = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c.setAutoCommit(false);
            String sql = "SELECT id, brandname, procspeed, ram, diskcap FROM LAPTOPS";


            s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);

                // loop through the result set
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + "\t" +
                            rs.getString("brandname") + "\t" +
                            rs.getDouble("procspeed") + "\t" +
                            rs.getInt("ram") + "\t" +
                            rs.getInt("diskcap"));
                }

            rs.close();
            s.close();

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    public void update(){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DBName + ".db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "UPDATE LAPTOPS set SALARY = 25000.00 where ID=1;";
            stmt.executeUpdate(sql);
            c.commit();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

    public void delete(int ID){

        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");

            c.setAutoCommit(false);
            System.out.println("Opened database successfully");


            stmt = c.createStatement();
            String sql = "DELETE from LAPTOPS where ID=" + ID + ";";
            stmt.executeUpdate(sql);
            c.commit();



        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }


}
