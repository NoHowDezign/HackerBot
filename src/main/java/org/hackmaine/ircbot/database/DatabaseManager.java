package org.hackmaine.ircbot.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public abstract class DatabaseManager {
	private Connection sqlConnection;
	
	/**
	 * Connects to the database for querys.
	 */
	public void connect() throws ClassNotFoundException, SQLException {
		Class.forName("org.hsqldb.jdbc.JDBCDriver");
		sqlConnection = DriverManager.getConnection("jdbc:hsqldb:file:./database/hackerbot", "SA", "");
	}

	private void generateTableForMod(String modname) throws Exception {
		connect();
		Statement statement = sqlConnection.createStatement();
	    
		//Create a sql table for the mod, with just a default field
	    String sql = "CREATE TABLE IF NOT EXISTS " + modname +
	                 "(id INTEGER IDENTITY, " +
	                 "attribute_id INTEGER, " +
	                 "attribute_value VARCHAR(256));"; //Required for ANY mod, for user association
	    
		statement.executeUpdate(sql);
	}
	
	/**
	 * Gets an attribute for a user from the database.
	 * @param modname is the name of the mod that you are creating.
	 * @param userId is the users unique ID
	 * @param attribute is the ID of the attribute
	 * @return the attribute value as a string
	 */
	public String getAttributeForUser(String modname, int userId, int attribute) throws Exception {
		connect();
		generateTableForMod(modname);
		PreparedStatement attributeQuery = sqlConnection.prepareStatement("SELECT * FROM " + modname + " WHERE id = ? AND attribute_id = ?");

		//Get the results of the table with the attribute provided
		attributeQuery.setInt(1, userId);
		attributeQuery.setInt(2, attribute);

		ResultSet resultsOfQuery = attributeQuery.executeQuery();
		
		DatabaseUsageRegistry registry = new DatabaseUsageRegistry();
		
		//Sandboxing!
		if(registry.getPluginsUsingDatabase().containsKey(modname) && 
				registry.getPluginsUsingDatabase().get(modname).isAssignableFrom(this.getClass())) {
			
			if(resultsOfQuery.next())
				return resultsOfQuery.getString("attribute_value");

			return null;
		} else if(resultsOfQuery.next()) {
			if(resultsOfQuery.getString("attribute_value").endsWith("-public")) {
				sqlConnection.close();
				return resultsOfQuery.getString("attribute_value");
			} else {
				sqlConnection.close();
				System.out.println("A module is trying to access a database that is not it's own. It is NOT a public table.");
				return null;
			}
		}

		sqlConnection.close();
		return null;
	}
	
	/**
	 * Adds an attribute to the database for a specific user.
	 * @param userId is the ID of the user you are assigning the attribute to.
	 * @param attributeId is the ID of the attribute for storage.
	 * @param attributeValue is the value of the attribute, as a string.
	 * @param isPublic tells whether the attribute is public for other plugins or not.
	 */
	public void createAttributeForUser(String modname, int userId, int attributeId, String attributeValue, boolean isPublic) throws Exception {
		connect();
		generateTableForMod(modname);
		PreparedStatement attributeInsert = sqlConnection.prepareStatement("INSERT INTO " + modname + " (id, attribute_id, attribute_value) VALUES (?, ?, ?)");
		attributeInsert.setInt(1, userId);
		attributeInsert.setInt(2, attributeId);
		
		if(isPublic)
			attributeInsert.setString(3, attributeValue + "-public");
		else
			attributeInsert.setString(3, attributeValue);

		attributeInsert.execute();

		sqlConnection.close();
	}
}

