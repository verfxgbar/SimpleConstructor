public enum DatenTyp
{
	keins("null", "keins", 23582),
	buchstabe("char", "buchstabe", 243568),
	text("string", "text", 23529),
	zahl("integer", "zahl", 238523),
	wahrheitswert("boolean", "wahrheitswert", 3463);

	private String dataName;
	private String normalName;
	private int uID;

	DatenTyp(String dataName, String normalName, int uID)
	{
		this.dataName = dataName;
		this.normalName = normalName;
		this.uID = uID;
	}

	public String getDataName()
	{
		return dataName;
	}

	public String getNormalName()
	{
		return normalName;
	}

	public int getuID()
	{
		return uID;
	}

	public static DatenTyp getTypeFromUID(int uID)
	{
		for (int i = 0; i < DatenTyp.values().length; i++)
			if (DatenTyp.values()[i].getuID() == uID)
				return DatenTyp.values()[i];
		return null;
	}

	public static DatenTyp getTypeFromName(String name)
	{
		for (int i = 0; i < DatenTyp.values().length; i++)
			if (DatenTyp.values()[i].getNormalName().equalsIgnoreCase(name))
				return DatenTyp.values()[i];
		return null;
	}
}
