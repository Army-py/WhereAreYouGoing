package fr.army.whereareyougoing.database;

import fr.army.whereareyougoing.config.Database;
import fr.army.whereareyougoing.database.exception.impl.DatabaseConnectionException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.util.Properties;

public class EMFLoader {

    private static EntityManagerFactory entityManagerFactory = null;

    private final String localDatabaseName = Database.localDatabaseName;

    public void setupEntityManagerFactory(@NotNull String dataFolderPath) throws DatabaseConnectionException {
        if (entityManagerFactory == null || !entityManagerFactory.isOpen()) {
            Properties properties = new Properties();

            properties.put("hibernate.hbm2ddl.auto", "update");
            properties.put("hibernate.order_inserts", "true");

            properties.put("jakarta.persistence.jdbc.driver", "org.sqlite.JDBC");
            properties.put("jakarta.persistence.jdbc.url", "jdbc:sqlite:" + dataFolderPath + "/" + localDatabaseName);
            properties.put("hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect");

            properties.put("hibernate.connection.isolation", String.valueOf(Connection.TRANSACTION_SERIALIZABLE));
            properties.put("jakarta.persistence.sharedCache.mode", "NONE");
            properties.put("hibernate.connection.release_mode", "after_transaction");

            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

            try {
                entityManagerFactory = Persistence.createEntityManagerFactory("persistence-unit", properties);
            } catch (Exception e) {
                throw new DatabaseConnectionException("Unable to connect to the database", e);
            }
        }
    }

    public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
