package at.tuwien.ict.commonutils.configbuilder.helper;

public class Devicedriver {
	private String serial ="";
	private String channeladdress="";
	
	public String getSerial() {
		return serial;
	}
	
	public String getChanneladdress() {
		return channeladdress;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Devicedriver [serial=");
		builder.append(serial);
		builder.append(", channeladdress=");
		builder.append(channeladdress);
		builder.append("]");
		return builder.toString();
	}
}

