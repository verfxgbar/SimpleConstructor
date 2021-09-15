import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings({ "unchecked", "unused" })
public class CustomConstructor
{
	private String className = "";
	private Map<String, String> attributes = new HashMap<>();
	private Map<String, DatenTyp> attributeDataTypes = new HashMap<>();
	private Map<String, String> questionMethods = new HashMap<>();
	private Map<String, String> doMethods = new HashMap<>();
	private Map<String, MethodType> doMethodTypes = new HashMap<>();

	public CustomConstructor(String className, Map<String, String> attributes, Map<String, DatenTyp> attributeDataTypes, Map<String, String> questionMethods,
	        Map<String, String> doMethods, Map<String, MethodType> doMethodTypes)
	{
		this.className = className;
		this.attributes = attributes;
		this.attributeDataTypes = attributeDataTypes;
		this.questionMethods = questionMethods;
		this.doMethods = doMethods;
		this.doMethodTypes = doMethodTypes;
	}

	public String getClassName()
	{
		return className;
	}

	public Map<String, String> getAttributes()
	{
		return attributes;
	}

	public Map<String, String> getDoMethods()
	{
		return doMethods;
	}

	public Map<String, DatenTyp> getAttributeDataTypes()
	{
		return attributeDataTypes;
	}

	public void editMethodName(String oldMethodName, String newMethodName)
	{

		boolean changed = false;
		for (String qKey : getQuestionMethods().keySet())
			if (qKey.equalsIgnoreCase(oldMethodName))
			{
				getQuestionMethods().put(newMethodName, getQuestionMethods().get(qKey));
				getQuestionMethods().remove(qKey);
				changed = true;
			}
		for (String dKey : getDoMethods().keySet())
			if (dKey.equalsIgnoreCase(oldMethodName))
			{
				getDoMethods().put(newMethodName, getDoMethods().get(dKey));
				getDoMethods().remove(dKey);
				changed = true;
			}
		for (String dtKey : getDoMethodTypes().keySet())
			if (dtKey.equalsIgnoreCase(oldMethodName))
			{
				getDoMethodTypes().put(newMethodName, getDoMethodTypes().get(dtKey));
				getDoMethodTypes().remove(dtKey);
				changed = true;
			}
		if (changed)
			System.out.println("Die Methode \"" + oldMethodName + "\" wurde erfolgreich zu der Methode \"" + newMethodName + "\" umbennant!");
		else
			System.out.println("Es konnte keine Methode mit dem Namen \"" + oldMethodName + "\" gefunden werden!");
		FileReader.saveConstructor(this);
	}

	public void editMethod(String method, String newAttribut)
	{
		boolean changed = false;
		for (String qKey : getQuestionMethods().keySet())
			if (qKey.equalsIgnoreCase(method))
			{
				getQuestionMethods().put(qKey, newAttribut);
				changed = true;
			}
		for (String dKey : getDoMethods().keySet())
			if (dKey.equalsIgnoreCase(method))
			{
				getQuestionMethods().put(dKey, newAttribut);
				changed = true;
			}
		if (changed)
			System.out.println("Die Methode \"" + method + "\" wurde erfolgreich mit dem neuem Attribut \"" + newAttribut + "\" verbunden!");
		FileReader.saveConstructor(this);
	}

	public void deleteMethod(String method)
	{
		boolean changed = false;
		if (getQuestionMethods().containsKey(method))
		{
			getQuestionMethods().remove(method);
			changed = true;
		}
		if (getDoMethods().containsKey(method))
		{
			getDoMethods().remove(method);
			changed = true;
		}
		if (getDoMethodTypes().containsKey(method))
		{
			getDoMethodTypes().remove(method);
			changed = true;
		}
		if (changed)
			System.out.println("Die Methode \"" + method + "\" wurde erfolgreich gelöscht!");
		else
			System.out.println("Die Methode \"" + method + "\" konnte nicht gefunden werden!");
		FileReader.saveConstructor(this);
	}

	public Map<String, MethodType> getDoMethodTypes()
	{
		return doMethodTypes;
	}

	public Map<String, String> getQuestionMethods()
	{
		return questionMethods;
	}

	public String getAttribute(String attribute)
	{
		for (String key : attributes.keySet())
			if (key.equalsIgnoreCase(attribute))
				return attributes.get(key);
		return "";
	}

	public void createAttribut(String attribute, String value, DatenTyp type)
	{
		getAttributes().put(attribute, value);
		getAttributeDataTypes().put(attribute, type);
		System.out.println("Das Attribut \"" + attribute + "\" wurde erfolgreich mit dem Wert \"" + value + "\" erstellt!");
		FileReader.saveConstructor(this);
		return;
	}

	public String executeCustomMethod(String method)
	{
		if (questionMethods.containsKey(method))
		{
			String attribute = questionMethods.get(method);
			return getAttribute(attribute);
		} else if (doMethods.keySet().contains(method))
			updateAttribute();
		return "";
	}

	public void updateAttribute()
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Wie soll das neue Attribut verändert werden? [update, add, remove]");
		String type = scanner.nextLine();
		switch (type.toLowerCase())
		{
		case "update":
			System.out.println("Was möchtest du an dem Attribut verändern? [datatype, value]");
			String updateAttributeType = scanner.nextLine();
			while (true)
			{
				if (updateAttributeType.toLowerCase().equalsIgnoreCase("datatype") || updateAttributeType.toLowerCase().equalsIgnoreCase("value"))
					break;
				System.out.println("Du hast eine Falsche Antwort eingegeben!");
				System.out.println("Was möchtest du an dem Attribut verändern? [datatype, value]");
				updateAttributeType = scanner.nextLine();
			}
			switch (updateAttributeType.toLowerCase())
			{
			case "datatype":
				System.out.println("Folgende Attribute können verändert werden:");
				getAttributes().keySet().forEach(e -> System.out.println(e + " hat momentan den Wert -> " + getAttributes().get(e)));
				System.out.println("\n Welches möchtest du verändern?");
				String updateAttribute = scanner.nextLine();
				String datentypNames = "[ ";
				for (int i = 0; i < DatenTyp.values().length; i++)
					datentypNames += DatenTyp.values()[i].getNormalName() + (i >= DatenTyp.values().length ? " ]" : ", ");
				System.out.println("Was soll der neue Datentyp sein?" + datentypNames);
				String newDatenTypName = scanner.nextLine();
				getAttributeDataTypes().put(updateAttribute, DatenTyp.getTypeFromName(newDatenTypName));
				break;
			case "value":
				System.out.println("Folgende Attribute können verändert werden:");
				getAttributes().keySet().forEach(e -> System.out.println(e + " hat momentan den Wert -> " + getAttributes().get(e)));
				System.out.println("\n Welches möchtest du verändern?");
				updateAttribute = scanner.nextLine();
				System.out.println("Okay! Wie soll der neue Wert sein?");
				String newValueUPDATE = scanner.nextLine();
				getAttributes().put(updateAttribute, newValueUPDATE);
				System.out.println(updateAttribute + " hat jetzt den Wert -> " + newValueUPDATE);
				break;
			}
			break;
		case "add":
			System.out.println("Wie soll das neue Attribut heißen?");
			String newAttribute = scanner.nextLine();
			System.out.println("Welchen Wert soll das Attribut \"" + newAttribute + "\" haben? (Wenn es keinen Wert haben soll schreibe einfach \"nein\")");
			String newValueADD = scanner.nextLine();
			if (newValueADD.equalsIgnoreCase("nein"))
			{
				System.out.println("Alles klar! Das Attribut wurde neu hinzugefügt!");
//				getAttributes().put(newAttribute, "null");
				createAttribut(newAttribute, "null", DatenTyp.keins);
				break;
			} else
			{
				String datentypNames = "[ ";
				for (int i = 0; i < DatenTyp.values().length; i++)
					datentypNames += DatenTyp.values()[i].getNormalName() + (i >= DatenTyp.values().length ? " ]" : ", ");
				System.out.println("Welchen DatenTyp soll das Attribut haben? " + datentypNames);
				String newDatenTypName = scanner.nextLine();
				while (DatenTyp.getTypeFromName(newDatenTypName) == null)
				{
					System.out.println("Der DatenTyp existiert nicht!");
					System.out.println("Welchen DatenTyp soll das Attribut haben? " + datentypNames);
				}
				System.out.println("Alles klar! \n Das Attribut \"" + newAttribute + "\" mit dem Wert \"" + newValueADD + "\" wurde hinzugefügt!");
				getAttributes().put(newAttribute, newValueADD);
				createAttribut(newAttribute, newValueADD, DatenTyp.getTypeFromName(newDatenTypName));
				break;
			}
		case "remove":
			System.out.println("Folgende Attribute können gelöscht werden:");
			getAttributes().keySet().forEach(e -> System.out.println(e + " hat momentan den Wert -> " + getAttributes().get(e)));
			System.out.println("\n Welches soll gelöscht werden?");
			String attributeREMOVE = scanner.nextLine();
			if (getAttributes().containsKey(attributeREMOVE))
			{
				System.out.println("Das Attribut wurde erfolgreich gelöscht!");
				deleteAttribute(attributeREMOVE);
			} else
			{
				System.out.println("Das Attribut existiert nicht! Der Vorgang wird abgebrochen...");
				break;
			}
		}
		scanner.close();
		FileReader.saveConstructor(this);
		return;
	}

	public void deleteAttribute(String attribute)
	{
		if (getQuestionMethods().containsValue(attribute))
			for (String key : this.questionMethods.keySet())
				if (getQuestionMethods().get(key).equalsIgnoreCase(attribute))
					getQuestionMethods().put(key, "null");
		if (getDoMethods().containsValue(attribute))
			for (String key : this.doMethods.keySet())
				if (getDoMethods().get(key).equalsIgnoreCase(attribute))
					getDoMethods().put(key, "null");

		getAttributes().remove(attribute);
		getAttributeDataTypes().remove(attribute);
		FileReader.saveConstructor(this);
	}

}
