
public class FallDetector {
	public Boolean Detect(SensorData data) {
		int x = data.getAccelerator_x();
		int y = data.getAccelerator_y();
		int z = data.getAccelerator_z();
		double vectorSum = Math.sqrt(x * x + y * y + z * z);
		System.out.println("vectorSum:" + vectorSum);
		if(vectorSum < 5)
			return true;
			
		return false;
	}
}
