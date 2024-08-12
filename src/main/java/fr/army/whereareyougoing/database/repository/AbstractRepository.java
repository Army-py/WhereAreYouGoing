package fr.army.whereareyougoing.database.repository;

import fr.army.whereareyougoing.database.EMFLoader;
import fr.army.whereareyougoing.database.model.AbstractModel;
import fr.army.whereareyougoing.database.repository.callback.AsyncCallBackObject;
import fr.army.whereareyougoing.database.repository.queue.DatabaseTask;
import fr.army.whereareyougoing.database.repository.queue.DatabaseTaskQueueManager;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.function.Consumer;

public abstract class AbstractRepository<T extends AbstractModel> {

    protected final EMFLoader emfLoader;
    protected final Class<T> entityClass;

    public AbstractRepository(EMFLoader emfLoader, Class<T> entityClass) {
        this.entityClass = entityClass;
        this.emfLoader = emfLoader;
    }

    public void get(Object id, AsyncCallBackObject<T> asyncCallBackObject) {
        executeInTransaction(entityManager -> {
            T e = entityManager.find(entityClass, id);
            if (asyncCallBackObject != null) {
                asyncCallBackObject.done(e);
            }
        });
    }

    public void save(T entity) {
        executeInTransaction(entityManager -> entityManager.persist(entity));
    }

    @Transactional
    protected void executeInTransaction(Consumer<EntityManager> action) {
        final EntityManager entityManager = emfLoader.getEntityManager();
        try {
            DatabaseTask task = new DatabaseTask(entityManager, action);
            DatabaseTaskQueueManager.enqueueTask(task);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
