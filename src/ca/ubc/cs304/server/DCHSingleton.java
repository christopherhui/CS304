package ca.ubc.cs304.server;

import ca.ubc.cs304.database.DatabaseConnectionHandler;

public class DCHSingleton {
    private static DatabaseConnectionHandler databaseConnectionHandler = null;

    private DCHSingleton() {
        databaseConnectionHandler = new DatabaseConnectionHandler();
    }

    public static DatabaseConnectionHandler getDatabaseConnectionHandler() {
        if (databaseConnectionHandler == null) {
            new DCHSingleton();
        }
        return databaseConnectionHandler;
    }
}
