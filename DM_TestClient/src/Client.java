import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

public class Client {

	public static void main(String args[]) {
		Socket client;
		DataInputStream input;
		DataOutputStream output;
		DataGenerator gen = new DataGenerator();
		StringBuilder display = new StringBuilder();

		try {
			// Step 1: Create a Socket to make connection.
			client = new Socket(InetAddress.getLocalHost(), 2347);

			display.append("Connected to: " + client.getInetAddress().getHostName());

			// Step 2: Get the input and output streams.
			input = new DataInputStream(client.getInputStream());
			output = new DataOutputStream(client.getOutputStream());
			display.append("\nGot I/O Streams\n");

			// Step 3: Process connection.
			for(int i = 0; i < 10; i++) {
				//System.out.println("i:" + i);
				SensorData data = gen.generate();
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutput out = null;
				out = new ObjectOutputStream(bos);
				out.writeObject(data);
				byte[] dataBytes = bos.toByteArray();
	
				output.write(dataBytes);
				Thread.sleep(100);
			}
			// Step 4: Close connection.
			display.append("Transmission complete. " + "Closing connection.\n");
			System.out.println(display.toString());
			client.close();
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
