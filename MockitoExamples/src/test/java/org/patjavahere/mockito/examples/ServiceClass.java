package org.patjavahere.mockito.examples;

public class ServiceClass {
    private DBDaoClass database;
    private NwDaoClass network;

    public boolean save(String fileName) {
        database.save(fileName);
        System.out.println("Saved in database in Main class");

        network.save(fileName);
        System.out.println("Saved in network in Main class");

        return true;
    }

    public DBDaoClass getDatabase() {
        return database;
    }

    public void setDatabase(DBDaoClass database) {
        this.database = database;
    }

    public NwDaoClass getNetwork() {
        return network;
    }

    public void setNetwork(NwDaoClass network) {
        this.network = network;
    }
}
