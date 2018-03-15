package at.tuwien.ict.commonutils.simpleuserconsole;

public interface GeneralUserConsole {
	public void init(FunctionPointer functions);
	public void registerFunction(String command, String description);
	public void startUserConsole();
}
