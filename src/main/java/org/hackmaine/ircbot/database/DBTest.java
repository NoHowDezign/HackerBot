package org.hackmaine.ircbot.database;


public class DBTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DatabaseManagerImpl database = new DatabaseManagerImpl();
		try {
			DatabaseUsageRegistry reg = new DatabaseUsageRegistry();
			reg.setPluginsUsingDatabase("Test", DatabaseManagerImpl.class);
			database.connect();
			database.createAttributeForUser("Test", 1, 1, "testattrib", false);
			System.out.println(database.getAttributeForUser("Test", 1, 1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static class DatabaseManagerImpl extends DatabaseManager {}

}
