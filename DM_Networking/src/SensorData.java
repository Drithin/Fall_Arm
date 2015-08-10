import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

public class SensorData implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8508130011683677620L;
	private int device_id;
	private int accelerator_x;
	private int accelerator_y;
	private int accelerator_z;
	private int gyroscope_x;
	private int gyroscope_y;
	private int gyroscope_z;
	private Date timestamp;
	
	public int getDevice_id() {
		return device_id;
	}
	public void setDevice_id(int device_id) {
		this.device_id = device_id;
	}
	
	public int getAccelerator_x() {
		return accelerator_x;
	}
	public void setAccelerator_x(int accelerator_x) {
		this.accelerator_x = accelerator_x;
	}
	public int getAccelerator_y() {
		return accelerator_y;
	}
	public void setAccelerator_y(int accelerator_y) {
		this.accelerator_y = accelerator_y;
	}
	public int getAccelerator_z() {
		return accelerator_z;
	}
	public void setAccelerator_z(int accelerator_z) {
		this.accelerator_z = accelerator_z;
	}
	public int getGyroscope_x() {
		return gyroscope_x;
	}
	public void setGyroscope_x(int gyroscope_x) {
		this.gyroscope_x = gyroscope_x;
	}
	public int getGyroscope_y() {
		return gyroscope_y;
	}
	public void setGyroscope_y(int gyroscope_y) {
		this.gyroscope_y = gyroscope_y;
	}
	public int getGyroscope_z() {
		return gyroscope_z;
	}
	public void setGyroscope_z(int gyroscope_z) {
		this.gyroscope_z = gyroscope_z;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public static SensorData ReadByte(byte[] bytes) {
		SensorData data = null;
		
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInput in = null;
		try {
		  in = new ObjectInputStream(bis);
		  data = (SensorData) in.readObject(); 
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
		  try {
		    bis.close();
		  } catch (IOException ex) {
		    // ignore close exception
		  }
		  try {
		    if (in != null) {
		      in.close();
		    }
		  } catch (IOException ex) {
		    // ignore close exception
		  }
		}
		
		return data;
	}
	
	public byte[] ToByte() {
		byte[] byteData = null;
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		try {
		  out = new ObjectOutputStream(bos);   
		  out.writeObject(this);
		  byteData = bos.toByteArray();
		} 
		catch(Exception e) {
			e.printStackTrace();			
		}
		finally {
		  try {
		    if (out != null) {
		      out.close();
		    }
		  } catch (IOException ex) {
		    // ignore close exception
		  }
		  try {
		    bos.close();
		  } catch (IOException ex) {
		    // ignore close exception
		  }
		}
		return byteData;
	}
}
