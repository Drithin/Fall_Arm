import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DBHandler {
  private Connection connect = null;
  private Statement statement = null;
  private PreparedStatement preparedStatement = null;
  private ResultSet resultSet = null;

  private static final String USERNAME = "drithin";
  private static final String PASSWORD = "drithinpw";
  private static final String CONN_STRING = "jdbc:mysql://localhost:3306/cs595database";
  
  public DBHandler() {
	  // this will load the MySQL driver, each DB has its own driver
      try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      // setup the connection with the DB.
      try {
		connect = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  public void readDataBase() throws Exception {
    try {
      
      // statements allow to issue SQL queries to the database
      statement = connect.createStatement();
      // resultSet gets the result of the SQL query
      resultSet = statement.executeQuery("select * from PATIENTS");
      writeResultSet(resultSet);

      preparedStatement = connect
              .prepareStatement("SELECT * from STAFF");
          resultSet = preparedStatement.executeQuery();
          writeResultSet(resultSet);
      
      preparedStatement = connect
          .prepareStatement("SELECT * from STAFF");
      resultSet = preparedStatement.executeQuery();
      writeResultSet(resultSet);
      
    } catch (Exception e) {
      throw e;
    } finally {
      close();
    }

  }
  
  public void writeData(int device_id, int ax1, int ax2, int ax3, int gx1, int gx2, int gx3, java.sql.Timestamp date) throws SQLException {
	  // preparedStatements can use variables and are more efficient
      preparedStatement = connect
    		  .prepareStatement("INSERT INTO DEVICE_MOTION VALUES(?, ?, ?, ?, ? , ?, ?, ?, ?, ?)");
      preparedStatement.setInt(1, device_id);
      preparedStatement.setInt(2, 11);
      preparedStatement.setString(3, "name__pid");
      preparedStatement.setInt(4, ax1);
      preparedStatement.setInt(5, ax2);
      preparedStatement.setInt(6, ax3);
      preparedStatement.setInt(7, gx1);
      preparedStatement.setInt(8, gx2);
      preparedStatement.setInt(9, gx3);
      preparedStatement.setTimestamp(10, date);//date);
      preparedStatement.executeUpdate();
  }
  
  public void writeFallData(int fallType, int patientId) throws SQLException {
	  int fallId = 0;
	  
	  preparedStatement = connect
    		  .prepareStatement("INSERT INTO FALL_DATA VALUES(?, ?, ?, ?)");
      preparedStatement.setInt(1, fallId);
      preparedStatement.setString(2, "Adverse");
      preparedStatement.setTimestamp(3, new java.sql.Timestamp(new Date().getTime()));
      preparedStatement.setInt(4, patientId);
      preparedStatement.executeUpdate();
  }

  private void writeMetaData(ResultSet resultSet) throws SQLException {
    // now get some metadata from the database
    System.out.println("The columns in the table are: ");
    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
      System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
    }
  }

  private void writeResultSet(ResultSet resultSet) throws SQLException {
    // resultSet is initialised before the first data set
    while (resultSet.next()) {
      String user = resultSet.getString("myuser");
      String website = resultSet.getString("webpage");
      String summary = resultSet.getString("summary");
      Date date = resultSet.getDate("datum");
      String comment = resultSet.getString("comments");
      System.out.println("User: " + user);
      System.out.println("Website: " + website);
      System.out.println("Summary: " + summary);
      System.out.println("Date: " + date);
      System.out.println("Comment: " + comment);
    }
  }

  // you need to close all three to make sure
  private void close() {
    close((Closeable) resultSet);
    close((Closeable) statement);
    close((Closeable) connect);
  }
  private void close(Closeable c) {
    try {
      if (c != null) {
        c.close();
      }
    } catch (Exception e) {
    	e.printStackTrace();
    }
  }
} 
