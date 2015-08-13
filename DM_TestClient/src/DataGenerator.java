import java.util.Date;

public class DataGenerator {
	public DataGenerator(){}
	
	public SensorData generate() {
		SensorData data = new SensorData();
		int val;
		
		val = (int) (Math.random() * 6) + 1;
		data.setDevice_id(val);
		val = (int) (Math.random() * 8) + 1;
		data.setAccelerator_x(val);
		val = (int) (Math.random() * 8) + 1;
		data.setAccelerator_y(val);
		val = (int) (Math.random() * 8) + 1;
		data.setAccelerator_z(val);
		val = (int) (Math.random() * 8) + 1;
		data.setGyroscope_x(val);
		val = (int) (Math.random() * 8) + 1;
		data.setGyroscope_y(val);
		val = (int) (Math.random() * 8) + 1;
		data.setGyroscope_z(val);
		data.setTimestamp(new Date());
		
		return data;
	}
	
	public static void main(String[] args) {
		DataGenerator gen = new DataGenerator();
		SensorData val = gen.generate();
		System.out.println("Device:" + val.getDevice_id() + " Acc_x:" + val.getAccelerator_x() + " Acc_y:" + val.getAccelerator_y() +
					" Acc_z:" + val.getAccelerator_z() + " Gyro_x:" + val.getGyroscope_x() + 
					" Gyro_y:" + val.getGyroscope_y() + " Gyro_z:" + val.getGyroscope_z());
	}
}
