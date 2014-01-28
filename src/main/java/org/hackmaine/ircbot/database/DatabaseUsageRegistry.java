package org.hackmaine.ircbot.database;

import java.util.HashMap;
import java.util.Map;

import org.hackmaine.ircbot.plugins.PluginLoader;

public class DatabaseUsageRegistry {
	private Map<String, Class<?>> pluginsUsingDatabase = new HashMap<String, Class<?>>();

	public Map<String, Class<?>> getPluginsUsingDatabase() {
		return pluginsUsingDatabase;
	}

	public void setPluginsUsingDatabase(String pluginName, Class<?> databaseImplementer) {
		if(!this.pluginsUsingDatabase.containsKey(pluginName)) {
			this.pluginsUsingDatabase.put(pluginName, databaseImplementer);
		} else {
			System.out.println("A class is trying to put itself into the registry again...\n" +
					"This could mean that a plugin is trying to break into another plugins database.");
		}
	}
	
}
