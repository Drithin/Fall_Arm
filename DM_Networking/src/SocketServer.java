import java.net.*;
import java.sql.SQLException;
import java.util.Date;
import java.io.*;

public class SocketServer extends Thread {
	public final static int defaultPort = 2347;
	static int num_threads = 10;

	ServerSocket theServer;
	DBHandler dbHandler = null;
	FallDetector fallDetector;

	SensorData[] dataReadings = new SensorData[100];

	public static void main(String[] args) {
		int port = defaultPort;
		try {
			if(args.length > 0)
				port = Integer.parseInt(args[0]);
			if (port <= 0 || port >= 65536)
				port = defaultPort;

			ServerSocket ss = new ServerSocket(port);
			for (int i = 0; i < num_threads; i++) {
				SocketServer pes = new SocketServer(ss);
				// call run()
				pes.start();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SocketServer(ServerSocket ss) {
		theServer = ss;
	}

	public void run() {
		dbHandler = new DBHandler();
		fallDetector = new FallDetector();
		while (true) {
			try {
				Socket s = theServer.accept();
				s.setSoTimeout(10000);

				InputStream is = s.getInputStream();
				int i = 0;

				while (true) {
					byte[] b = new byte[256];
					int n = is.read(b);
					if (n == -1)
						break;
					SensorData val = SensorData.ReadByte(b);
					dataReadings[i] = val;
					i = (i + 1) % 100;
					System.out.println("Device:" + val.getDevice_id() + " Acc\tx:" + val.getAccelerator_x() + " y:"
							+ val.getAccelerator_y() + " z:" + val.getAccelerator_z() + "\tGyro\tx:"
							+ val.getGyroscope_x() + " y:" + val.getGyroscope_y() + " z:" + val.getGyroscope_z());
//					dbHandler.writeData(val.getDevice_id(), val.getAccelerator_x(), val.getAccelerator_y(), val.getAccelerator_z(),
//							val.getGyroscope_x(), val.getGyroscope_y(), val.getGyroscope_z(), 
//							new java.sql.Date(val.getTimestamp().getTime()));
					if (fallDetector.Detect(val)) {
//						MailHandler.sendEmail(dataReadings[i].getDevice_id(), false);
//						MailHandler.sendEmail(dataReadings[i].getDevice_id(), true);
						dbHandler.writeFallData(new Date().getSeconds(), 123);
					}
				}
			}
			catch (IOException | SQLException e) {
				e.printStackTrace();
			}
		}
	}
}