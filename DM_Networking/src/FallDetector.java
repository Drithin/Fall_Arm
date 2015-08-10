enum FallType {
	Adverse, NotAdverse, 
}


public class FallDetector {
	public FallType Detect(SensorData data) {
		FallType result = FallType.NotAdverse;
		
		double x = data.getAccelerator_x();
		double y = data.getAccelerator_y();
		double z = data.getAccelerator_z();
		double vectorSum = Math.round(x * x + y * y + z * z);
		System.out.println("vectorSum:" + vectorSum);
		if(vectorSum > 9)
			result = FallType.Adverse;
			
		return result;
	}
}
