package de.robinkrestel.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Main
{
	public static String path = "";

	public static void main(String[] args)
	{
		String className = "";
		Map<String, String> attributes = new HashMap<>();
		Map<String, DatenTyp> attributeDataTypes = new HashMap<>();
		Map<String, String> questionMethods = new HashMap<>();
		Map<String, String> doMethods = new HashMap<>();
		Map<String, MethodType> doMethodTypes = new HashMap<>();

		Scanner scanner = new Scanner(System.in);
		System.out.println("In welchem Pfad sind deine Constructor oder sollen sie gespeichert werden?");
		path = scanner.nextLine();
		System.out.println("Das Programm wird gestartet...");
		try
		{
			Thread.sleep(500);
		} catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}

		// Hier beginnt der Anfang
		System.out.println("Hallo! Was möchtest du machen? [create, edit, get, list]");
		String type = scanner.nextLine();
		switch (type.toLowerCase())
		{
		case "create":
			System.out.println("Wie soll der Constructor heißen?");
			className = scanner.nextLine();

			// Hier werden die Attribute erstellt
			String datentypNames = "[ ";
			for (int i = 0; i < DatenTyp.values().length; i++)
				datentypNames += DatenTyp.values()[i].getNormalName() + (i >= DatenTyp.values().length ? " ]" : ", ");
			System.out.println("Wie soll das erste Attribut heißen?");
			String NEW_Attributes = "", NEW_Value = "";
			NEW_Attributes = scanner.nextLine();
			while (!NEW_Attributes.equalsIgnoreCase("stop"))
			{
				if (NEW_Attributes.equalsIgnoreCase("stop"))
					break;
				System.out.println("Welchen Wert soll das Attribut haben?");
				NEW_Value = scanner.nextLine();
				System.out.println("Welchen DatenTypen soll das Attribut haben?" + datentypNames);
				String newDatenTyp = scanner.nextLine();
				while (DatenTyp.getTypeFromName(newDatenTyp) == null)
				{
					System.out.println("Der DatenTyp ist falsch!");
					System.out.println("Welchen DatenTypen soll das Attribut haben?" + datentypNames);
					newDatenTyp = scanner.nextLine();
				}
				attributes.put(NEW_Attributes, NEW_Value);
				attributeDataTypes.put(NEW_Attributes, DatenTyp.getTypeFromName(newDatenTyp));
				System.out.println("Wie soll das nächste Attribut heißen? (Falls du keins mehr haben möchtest schreibe einfach \"stop\")");
				NEW_Attributes = scanner.nextLine();
				continue;
			}

			// Hier werden die ersten Methoden erstellt
			System.out.println("Wie soll die erste Methode heißen?");
			String NEW_MethodName = scanner.nextLine();
			String methodNames = "[ ";
			for (int i = 0; i < MethodType.values().length; i++)
				methodNames += "" + MethodType.values()[i] + (i >= MethodType.values().length ? " ]" : ", ");
			while (!NEW_MethodName.equalsIgnoreCase("stop"))
			{
				if (NEW_MethodName.equalsIgnoreCase("stop"))
					break;
				String NEW_MethodAttribute = "", NEW_MethodType = "";
				System.out.println("Welchen Typ soll die Methode haben? " + methodNames);
				NEW_MethodType = scanner.nextLine();
				System.out.println("Welches Attribut soll die Methode haben?");
				NEW_MethodAttribute = scanner.nextLine();
				if (MethodType.getTypeFromName(NEW_MethodType) != null)
				{
					MethodType mType = MethodType.getTypeFromName(NEW_MethodType);
					switch (mType)
					{
					case DECREASE_COUNTER:
						doMethods.put(NEW_MethodName, NEW_MethodAttribute);
						doMethodTypes.put(NEW_MethodName, mType);
						System.out.println("Methoden-Name -> " + NEW_MethodName + " | Methoden-Typ ->" + mType.getName());
						break;
					case EXECUTABLE:
						doMethods.put(NEW_MethodName, NEW_MethodAttribute);
						doMethodTypes.put(NEW_MethodName, mType);
						System.out.println("Methoden-Name -> " + NEW_MethodName + " | Methoden-Typ ->" + mType.getName());
						break;
					case INCREASE_COUNTER:
						doMethods.put(NEW_MethodName, NEW_MethodAttribute);
						doMethodTypes.put(NEW_MethodName, mType);
						System.out.println("Methoden-Name -> " + NEW_MethodName + " | Methoden-Typ ->" + mType.getName());
						break;
					case QUESTION:
						questionMethods.put(NEW_MethodName, NEW_MethodAttribute);
						System.out.println("Methoden-Name -> " + NEW_MethodName + " | Methoden-Rückgabewert -> " + NEW_MethodAttribute);
						break;
					}
					System.out.println("Wie soll die nächste Methode heißen? (Falls du keins mehr haben möchtest schreibe einfach \"stop\")");
					NEW_MethodName = scanner.nextLine();
					continue;
				} else
					System.out.println("Du hast den Methoden-Typ falsch eingegeben! Das Programm wird beendet...");
				System.out.println("SYSTEM_SHUTDOWN...");
				return;
			}
			System.out.println("Attribute -> " + attributes + "\n" + "Question-Methods -> " + questionMethods.keySet() + "\n" + "DoMethods -> " + doMethods.keySet() + "\n"
			        + "DoMethodTypes -> " + doMethodTypes.keySet() + "\n");
			CustomConstructor constructor = new CustomConstructor(className, attributes, attributeDataTypes, questionMethods, doMethods, doMethodTypes);
			System.out.println(constructor);
			System.out.println("Der Constructor wurde erfolgreich erstellt! \n\n");
			try
			{
				System.out.println("-x-   Werte   -x-");
				Thread.sleep(100);
				System.out.println(" ");
				Thread.sleep(100);
				System.out.println("Constructor-Name => " + constructor.getClassName());
				Thread.sleep(100);
				System.out.println("Attribute => " + constructor.getAttributes().size());
				System.out.println("DatenTypen => " + constructor.getAttributeDataTypes().size());
				Thread.sleep(100);
				System.out.println("Question-Methods => " + constructor.getQuestionMethods().size());
				Thread.sleep(100);
				System.out.println("DoMethods => " + constructor.getDoMethods().size());
				Thread.sleep(100);
				System.out.println("DoMethodTypes => " + constructor.getDoMethodTypes().size());
				Thread.sleep(100);
				System.out.println(" ");
				Thread.sleep(100);
				System.out.println("-x-   Werte   -x-");
				Thread.sleep(100);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			FileReader.saveConstructor(constructor);
			System.out.println("Der Constructor wurde in einer Datei erstellt!");
			break;

		// CustomConstructor editieren
		case "edit":
			System.out.println("Es gibt folgende Constructor:");
			for (String s : getConstructorNames())
				System.out.println(s);
			System.out.println("Welchen möchtest du editieren?");
			String constructorName = scanner.nextLine();
			CustomConstructor EDIT_constructor = FileReader.getConstructorFromFile(constructorName);
			while (EDIT_constructor == null)
			{
				System.out.println("Der Constructor konnte nicht gefunden werden. Bitte schreibe den Namen richtig!");
				System.out.println("Wie lautet der Name des Constructors? (Falls du Probleme hast, schreibe einfach \"liste\")");
				String needsHelpWithConstructor = scanner.nextLine();
				if (needsHelpWithConstructor.equalsIgnoreCase("liste"))
					for (String cons : getConstructorNames())
						System.out.println(cons);
				else
					EDIT_constructor = FileReader.getConstructorFromFile(needsHelpWithConstructor);
				continue;
			}
			System.out.println("Der Constructor wurde gefunden!");
			System.out.println("Was möchtest du editieren? [attribute, methoden]");
			String editType = scanner.nextLine();
			switch (editType.toLowerCase())
			{
			case "attribute":
				EDIT_constructor.updateAttribute();
				break;
			case "methoden":
				System.out.println("Was möchtest du mit den Methoden machen? [create, edit, remove]");
				String METHOD_type = scanner.nextLine();
				switch (METHOD_type.toLowerCase())
				{
				case "create":
					newMethod(scanner, EDIT_constructor);
					break;
				case "edit":
					System.out.println("Was möchtest du ändern? [method_name, method_attribute]");
					switch (scanner.nextLine().toLowerCase())
					{
					case "method_name":
						System.out.println("Wie heißt die Methode die du umbenennen möchtest?");
						String EDIT_oldMethodName = "", EDIT_newMethodName;
						EDIT_oldMethodName = scanner.nextLine();
						System.out.println("Wie soll die Methode denn heißen?");
						EDIT_newMethodName = scanner.nextLine();
						EDIT_constructor.editMethodName(EDIT_oldMethodName, EDIT_newMethodName);
						break;
					case "method_attribute":
						System.out.println("Welcher Methode möchtest du denn ein neues Attribut hinzufügen?");
						String EDIT_methodName = "", EDIT_oldAttribut = "", EDIT_newAttribut = "";
						EDIT_methodName = scanner.nextLine();
						System.out.println("Wie soll das neue Attribut heißen? (falls du eine Liste brauchst schreibe einfach \"attribute\")");
						EDIT_oldAttribut = scanner.nextLine();
						while (!EDIT_oldAttribut.equalsIgnoreCase("stop"))
						{
							if (EDIT_oldAttribut.equalsIgnoreCase("stop"))
								break;
							else if (EDIT_oldAttribut.equalsIgnoreCase("attribute"))
								for (String s : EDIT_constructor.getAttributes().keySet())
									System.out.println(s + " hat den Wert -> " + EDIT_constructor.getAttributes().get(s));
							else
							{
								EDIT_constructor.editMethod(EDIT_methodName, EDIT_newAttribut);
								break;
							}
							continue;
						}
						FileReader.saveConstructor(EDIT_constructor);
						break;
					}
					break;
				case "remove":
					System.out.println("Wie heißt die Methode die du löschen möchtest?");
					String REMOVE_methodName = "";
					REMOVE_methodName = scanner.nextLine();
					EDIT_constructor.deleteMethod(REMOVE_methodName);
					break;
				}
				break;
			}
			break;
		case "get":
			System.out.println("Welchen Constructor möchtest du fragen?");
			String GET_constructorName = "";
			CustomConstructor GET_constructor = FileReader.getConstructorFromFile(GET_constructorName);
			GET_constructorName = scanner.nextLine();
			while (GET_constructor == null)
			{
				System.out.println("Der Constructor konnte nicht gefunden werden!");
				System.out.println("Falls du den Namen nicht mehr weißt, schreibe einfach \"list\"");
				String GET_loop1Answer = scanner.nextLine();
				if (GET_loop1Answer.equalsIgnoreCase("list"))
				{
					System.out.println("Es gibt folgende Constructor:");
					for (String s : getConstructorNames())
						System.out.println(s);
					System.out.println("Welchen Constructor möchtest du fragen?");
					GET_constructorName = scanner.nextLine();
					GET_constructor = FileReader.getConstructorFromFile(GET_constructorName);
				} else
				{
					GET_constructorName = scanner.nextLine();
					GET_constructor = FileReader.getConstructorFromFile(GET_constructorName);
				}
				continue;
			}
			System.out.println("Der Constructor wurde gefunden!");
			System.out.println("Es gibt insgesammt " + GET_constructor.getQuestionMethods().size() + " Frage-Methoden!");
			for (String s : GET_constructor.getQuestionMethods().keySet())
				System.out.println("Methode => \"" + s + "\" | Daten-Wert => " + GET_constructor.getAttributeDataTypes().get(GET_constructor.getQuestionMethods().get(s)));
			System.out.println("Welche Methode möchtest du aufrufen?");
			String GET_method = scanner.nextLine();
			System.out.println("[CONSOLE] " + GET_constructor.getAttribute(GET_constructor.getQuestionMethods().get(GET_method)));
			break;
		case "list":
			System.out.println("Es gibt folgende Constructor:");
			for (String s : getConstructorNames())
				System.out.println(s);
			break;
		}
	}

	private static void newMethod(Scanner scanner, CustomConstructor constructor)
	{
		System.out.println("Wie soll die neue Methode heißen?");
		String NEW_MethodName = scanner.nextLine();
		String methodNames = "[ ";
		for (int i = 0; i < MethodType.values().length; i++)
			methodNames += "" + MethodType.values()[i] + (i >= MethodType.values().length ? " ]" : ", ");
		while (!NEW_MethodName.equalsIgnoreCase("stop"))
		{
			if (NEW_MethodName.equalsIgnoreCase("stop"))
				break;
			String NEW_MethodAttribute = "", NEW_MethodType = "";
			System.out.println("Welchen Typ soll die Methode haben? " + methodNames);
			NEW_MethodType = scanner.nextLine();
			System.out.println("Welches Attribut soll die Methode haben?");
			NEW_MethodAttribute = scanner.nextLine();
			if (MethodType.getTypeFromName(NEW_MethodType) != null)
			{
				MethodType mType = MethodType.getTypeFromName(NEW_MethodType);
				switch (mType)
				{
				case DECREASE_COUNTER:
					constructor.getDoMethods().put(NEW_MethodName, NEW_MethodAttribute);
					constructor.getDoMethodTypes().put(NEW_MethodName, mType);
					System.out.println("Methoden-Name -> " + NEW_MethodName + " | Methoden-Typ ->" + mType.getName());
					break;
				case EXECUTABLE:
					constructor.getDoMethods().put(NEW_MethodName, NEW_MethodAttribute);
					constructor.getDoMethodTypes().put(NEW_MethodName, mType);
					System.out.println("Methoden-Name -> " + NEW_MethodName + " | Methoden-Typ ->" + mType.getName());
					break;
				case INCREASE_COUNTER:
					constructor.getDoMethods().put(NEW_MethodName, NEW_MethodAttribute);
					constructor.getDoMethodTypes().put(NEW_MethodName, mType);
					System.out.println("Methoden-Name -> " + NEW_MethodName + " | Methoden-Typ ->" + mType.getName());
					break;
				case QUESTION:
					constructor.getQuestionMethods().put(NEW_MethodName, NEW_MethodAttribute);
					System.out.println("Methoden-Name -> " + NEW_MethodName + " | Methoden-Rückgabewert -> " + NEW_MethodAttribute);
					break;
				}
				System.out.println("Wie soll die nächste Methode heißen? (Falls du keins mehr haben möchtest schreibe einfach \"stop\")");
				NEW_MethodName = scanner.nextLine();
				continue;
			} else
				System.out.println("Du hast den Methoden-Typ falsch eingegeben! Das Programm wird beendet...");
			System.out.println("SYSTEM_SHUTDOWN...");
			FileReader.saveConstructor(constructor);
			return;
		}

	}

	private static String[] getConstructorNames()
	{
		File dir = new File(Main.path);
		dir.mkdirs();
		String[] names = new String[dir.listFiles().length];
		for (int i = 0; i < dir.listFiles().length; i++)
			names[i] = dir.listFiles()[i].getName().replace(".yml", "");
		return names;
	}
}
