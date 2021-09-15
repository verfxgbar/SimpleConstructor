import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.simpleyaml.configuration.file.YamlConfiguration;

@SuppressWarnings("unchecked")
public class FileReader
{
	public static CustomConstructor getConstructorFromFile(String fileName)
	{
		File file = new File(Main.path, fileName + ".yml");
		if (file.exists())
		{
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			String className = cfg.getString("className");
			Map<String, String> attributes = stringToHashMap(cfg.getString("attributes"));
			Map<String, DatenTyp> attributeDataTypes = new HashMap<>();
			Map<String, String> placeHolder = stringToHashMap(cfg.getString("attributesDateTypes"));
			for (String key : placeHolder.keySet())
				attributeDataTypes.put(key, DatenTyp.getTypeFromUID(Integer.parseInt(placeHolder.get(key))));
			Map<String, String> questionMethods = stringToHashMap(cfg.getString("questionMethods"));
			Map<String, String> doMethods = (cfg.getString("doMethods") == "" ? null : stringToHashMap((cfg.getString("doMethods"))));
			if (doMethods == null)
				doMethods = new HashMap<String, String>();
			Map<String, String> placeHolder2 = (cfg.getString("doMethodTypes") == "" ? null : stringToHashMap((cfg.getString("doMethodTypes"))));
			Map<String, MethodType> doMethodTypes = new HashMap<>();
			if (placeHolder2 != null)
			{
				for (String key : placeHolder2.keySet())
					doMethodTypes.put(key, MethodType.valueOf(placeHolder2.get(key)));
			} else
				placeHolder2 = new HashMap<String, String>();
			CustomConstructor customConstructor = new CustomConstructor(className, attributes, attributeDataTypes, questionMethods, doMethods, doMethodTypes);
			return customConstructor;
		} else
			System.out.println("Die Datei existiert nicht!");
		return null;
	}

	public static CustomConstructor createNewConstructor(String className, Map<String, String> attributes, Map<String, DatenTyp> attributeDataTypes,
	        Map<String, String> questionMethods, Map<String, String> doMethods, Map<String, MethodType> doMethodTypes)
	{
		CustomConstructor newConstructor = new CustomConstructor(className, attributes, attributeDataTypes, questionMethods, doMethods, doMethodTypes);
		File file = new File(Main.path, className + ".yml");
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
				YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
				cfg.set("className", className);
				cfg.set("attributes", hashMapToString(attributes));
				Map<String, String> uIDs = new HashMap<>();
				for (String key : attributeDataTypes.keySet())
					uIDs.put(key, attributeDataTypes.get(key).getuID() + "");
				cfg.set("attributesDateTypes", hashMapToString(uIDs));
				cfg.set("questionMethods", hashMapToString(questionMethods));
				cfg.set("doMethods", hashMapToString(doMethods));
				Map<String, String> doMethodTypesDecoded = new HashMap<>();
				for (String key : doMethodTypes.keySet())
					doMethodTypesDecoded.put(key, String.valueOf(doMethodTypes.get(key).getUID()));
				cfg.set("doMethodTypes", doMethodTypes);
				cfg.save(file);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return newConstructor;
	}

	public static void saveConstructor(CustomConstructor constructor)
	{
		File file = new File(Main.path, constructor.getClassName() + ".yml");

		try
		{
			if (!file.exists())
				file.createNewFile();
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			cfg.set("className", constructor.getClassName());
			cfg.set("attributes", hashMapToString(constructor.getAttributes()));
			Map<String, String> uIDs = new HashMap<>();
			for (String key : constructor.getAttributeDataTypes().keySet())
				uIDs.put(key, String.valueOf(constructor.getAttributeDataTypes().get(key).getuID()));
			cfg.set("attributesDateTypes", hashMapToString(uIDs));
			cfg.set("questionMethods", hashMapToString(constructor.getQuestionMethods()));
			cfg.set("doMethods", hashMapToString(constructor.getDoMethods()));
			Map<String, String> methodTypesDecoded = new HashMap<>();
			for (String key : constructor.getDoMethodTypes().keySet())
				methodTypesDecoded.put(key, String.valueOf(constructor.getDoMethodTypes().get(key).getUID()));
			cfg.set("doMethodTypes", hashMapToString(methodTypesDecoded));
			cfg.save(file);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("Pfad -> " + file.getPath());
	}

	public static String hashMapToString(Map<String, String> map)
	{
		String mapString = map.toString();
		mapString = mapString.replace("{", "");
		mapString = mapString.replace("}", "");
		return mapString;
	}

	public static Map<String, String> stringToHashMap(String string)
	{
		HashMap<String, String> finalMap = new HashMap<String, String>();
		for (String s1 : string.split(","))
		{
			String[] s2 = s1.split("=");
			finalMap.put(s2[0], s2[1]);
		}
		return finalMap;
	}
}
