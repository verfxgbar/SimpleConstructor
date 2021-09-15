public enum MethodType
{
	QUESTION("QUESTION", " [QUESTION]", 98124),
	INCREASE_COUNTER("INCREASE_COUNTER", " [INCREASE_COUNTER]", 943598),
	DECREASE_COUNTER("DECREASE_COUNTER", " [DECREASE_COUNTER]", 893459),
	EXECUTABLE("EXECUTABLE", " [EXECUTABLE]", 43968);

	private String name = "";
	private String suffix = "";
	private int uID;

	MethodType(String name, String suffix, int uID)
	{
		this.name = name;
		this.suffix = suffix;
		this.uID = uID;
	}

	public String getName()
	{
		return name;
	}

	public String getSuffix()
	{
		return suffix;
	}
	
	public int getUID()
	{
		return uID;
	}

	public static MethodType getTypeFromName(String name)
	{
		for (int i = 0; i < MethodType.values().length; i++)
			if (MethodType.values()[i].getName().equalsIgnoreCase(name))
				return MethodType.values()[i];
		return null;
	}
}
