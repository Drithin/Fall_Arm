import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class DBHandler {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;

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

	public ResultSet getStaffEmail() throws Exception {
		ResultSet resultSet = null;
		try {

			// statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// resultSet gets the result of the SQL query
			preparedStatement = connect.prepareStatement("SELECT * from STAFF");
			resultSet = preparedStatement.executeQuery();
			return resultSet;
		} catch (Exception e) {
			throw e;
		}
	}

	public ResultSet getPatientInfo(int deviceId) throws Exception {
		ResultSet resultSet = null;
		try {
			// statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// resultSet gets the result of the SQL query
			preparedStatement = connect
					.prepareStatement("SELECT Patient_id, Patient_Name, ADDRESS, Phone from PATIENT WHERE Device_id=?");
			preparedStatement.setInt(1, deviceId);
			resultSet = preparedStatement.executeQuery();
			return resultSet;
		} catch (Exception e) {
			throw e;
		}
	}

	public void writeData(int device_id, int ax1, int ax2, int ax3, int gx1, int gx2, int gx3, java.sql.Timestamp date)
			throws Exception {
		ResultSet patientInfo = getPatientInfo(device_id);
		if (!patientInfo.next()) {
			System.out.println("No patient found!");
			return;
		}
		String patientName = patientInfo.getString("Patient_Name");
		int patientId = patientInfo.getInt("Patient_id");
		preparedStatement = connect.prepareStatement("INSERT INTO DEVICE_MOTION VALUES(?, ?, ?, ?, ? , ?, ?, ?, ?, ?)");
		preparedStatement.setInt(1, device_id);
		preparedStatement.setInt(2, patientId);
		preparedStatement.setString(3, patientName);
		preparedStatement.setInt(4, ax1);
		preparedStatement.setInt(5, ax2);
		preparedStatement.setInt(6, ax3);
		preparedStatement.setInt(7, gx1);
		preparedStatement.setInt(8, gx2);
		preparedStatement.setInt(9, gx3);
		preparedStatement.setTimestamp(10, date);// date);
		preparedStatement.executeUpdate();
	}

	public void writeFallData(int fallType, int patientId) throws SQLException {
		Date date = new Date();
		long fallId = date.getTime();

		preparedStatement = connect.prepareStatement("INSERT INTO FALL_DATA VALUES(?, ?, ?, ?)");
		preparedStatement.setLong(1, fallId);
		preparedStatement.setString(2, "Adverse");
		preparedStatement.setTimestamp(3, new java.sql.Timestamp(new Date().getTime()));
		preparedStatement.setInt(4, patientId);
		preparedStatement.executeUpdate();
	}

	// cleanup resources
	private void close() {
		close((java.lang.AutoCloseable) statement);
		close((java.lang.AutoCloseable) connect);
	}

	private void close(java.lang.AutoCloseable c) {
		try {
			if (c != null) {
				c.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
