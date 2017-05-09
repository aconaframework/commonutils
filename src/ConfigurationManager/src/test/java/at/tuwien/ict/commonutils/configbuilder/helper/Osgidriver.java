package at.tuwien.ict.commonutils.configbuilder.helper;

public class Osgidriver {
	private String deviceaddress ="";
	private String driverid = "";
	private String drivername="";
	
	public String getDeviceaddress() {
		return deviceaddress;
	}
	public String getDriverid() {
		return driverid;
	}
	public String getDrivername() {
		return drivername;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Osgidriver [deviceaddress=");
		builder.append(deviceaddress);
		builder.append(", driverid=");
		builder.append(driverid);
		builder.append(", drivername=");
		builder.append(drivername);
		builder.append("]");
		return builder.toString();
	}
}
