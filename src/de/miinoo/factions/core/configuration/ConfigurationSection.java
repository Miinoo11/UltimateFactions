package de.miinoo.factions.core.configuration;

/**
 * @author DotClass
 *
 */
import java.util.Map;

public interface ConfigurationSection {

	boolean contains(String path);
	
	<T> T get(String path);
	
	<T> T get(String path, T def);

	<T> T get(String path, Class<T> c);
	
	void set(String path, Object obj);
	
	ConfigurationSection getSection(String path);
	
	Map<String, Object> getValues();
	
	void save();
	
}
