package org.hackmaine.ircbot.plugins;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipException;

import org.hackmaine.ircbot.commandsystem.CommandHandlerRegistry;
import org.hackmaine.ircbot.commandsystem.annotations.CommandLogic;
import org.hackmaine.ircbot.eventsystem.EventHandlerRegistry;

import com.google.gson.Gson;

public class ModLoader {
	private File modDir = new File("./plugins");
	private Map<PluginInfo, JarEntry> pluginList = new HashMap<PluginInfo, JarEntry>();
	private URLClassLoader classLoader;
	
	public void loadPlugins() throws ZipException, IOException {
		Gson gson = new Gson();
		
		if(modDir.listFiles() != null) {
			for(File pluginFile : modDir.listFiles()) {
				if(pluginFile.exists()) {
					if(!pluginFile.isDirectory()) {
						//Begin finding JAR file plugins
						if(pluginFile.getName().endsWith(".jar")) {
							System.out.println("We found a plugin!");
							JarFile plugin = new JarFile(pluginFile);
							JarEntry pluginInfo = new JarEntry("plugin.json");
							Enumeration<JarEntry> jarEnum = plugin.entries();
							
							URL[] urls = { new URL("jar:file:" + pluginFile.getAbsolutePath() + "!/") };
							
							classLoader = URLClassLoader.newInstance(urls);
							
							
							while(jarEnum.hasMoreElements()) {
								PluginInfo pInfo = gson.fromJson(new InputStreamReader(plugin.getInputStream(pluginInfo)), PluginInfo.class);
								
								JarEntry jar = (JarEntry) jarEnum.nextElement();
								
								if(jar.getName().endsWith(".class")) {
									if(pInfo != null) {
										pluginList.put(pInfo, jar); //Put the plugin into the list - FINALLY!
										System.out.println("Just put a plugin into the map.");
									}
									continue;
								} else {
									System.out.println("Not a class. Skipping...");
									continue;
								}
								
								
							}
						}
					} else {
						System.out.println("Your 'Plugin' is actually a directory! LOL! Evaluating as such...");
					}
				} else {
					System.out.println("No plugins :(");
				}
			}
		} else {
			System.out.println("No plugins :(");
		}
	}
	
	public void parsePlugins() {
		for(Map.Entry<PluginInfo, JarEntry> pgins : pluginList.entrySet()) {
			System.out.println("Starting plugin evaluation...");
			JarEntry plugin = pgins.getValue();
			
			String className = plugin.getName().substring(0, plugin.getName().length() - 6);
			className = className.replace("/", ".");
			try {
				System.out.println("Scanning and loading class.");
				Class<?> classBeingScanned = classLoader.loadClass(className);
				System.out.println("Processing annotations.");
				processAnnotation(classBeingScanned);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void processAnnotation(Class<?> classToProcess) {
		if(classToProcess.getAnnotation(PluginLogic.class) != null) {
			System.out.println("Class is a logic class.");
			EventHandlerRegistry.register(classToProcess); //Add the class in question to the handlers
		} else if(classToProcess.getAnnotation(CommandLogic.class) != null) {
			System.out.println("Class is a command logic class.");
			CommandHandlerRegistry.register(classToProcess);
		} else {
			System.out.println("Not a class w/ an annotation.");
		}
	}
	
}