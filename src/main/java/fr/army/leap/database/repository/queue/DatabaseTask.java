package fr.army.leap.database.repository.queue;

import fr.army.leap.LeapPlugin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.Arrays;
import java.util.function.Consumer;

public class DatabaseTask implements Runnable {

    private final EntityManager entityManager;
    private final Consumer<EntityManager> databaseOperation;

    public DatabaseTask(EntityManager entityManager, Consumer<EntityManager> databaseOperation) {
        this.entityManager = entityManager;
        this.databaseOperation = databaseOperation;
    }

    @Override
    public void run() {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            databaseOperation.accept(entityManager);
            transaction.commit();
        } catch (Exception e) {
            LeapPlugin.getPlugin().getLogger().severe("Error while executing database task: " + Arrays.toString(e.getStackTrace()));
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
    }
}