package fr.army.leap.config;

import org.bukkit.configuration.file.YamlConfiguration;

public class Database {

    private final YamlConfiguration databaseConfig;

    public static String localDatabaseName;

    public Database(YamlConfiguration databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public void load(){
        localDatabaseName = databaseConfig.getString("sqlite.file", "data.db");
    }
}
