package fr.army.whereareyougoing.database.repository.impl;

import fr.army.whereareyougoing.database.EMFLoader;
import fr.army.whereareyougoing.database.model.impl.ServerModel;
import fr.army.whereareyougoing.database.repository.AbstractRepository;
import fr.army.whereareyougoing.database.repository.callback.AsyncCallBackObject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class ServerRepository extends AbstractRepository<ServerModel> {

    public ServerRepository(EMFLoader emfLoader) {
        super(emfLoader, ServerModel.class);
    }

    public void getFromServerName(String serverName, AsyncCallBackObject<ServerModel> asyncCallBackObject) {
        executeInTransaction(entityManager -> {
            final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            final CriteriaQuery<ServerModel> query = criteriaBuilder.createQuery(entityClass);
            final Root<ServerModel> root = query.from(entityClass);

            query.select(root);
            query.where(criteriaBuilder.equal(root.get("name"), serverName));

            ServerModel e = entityManager.createQuery(query).getResultStream().findFirst().orElse(null);

            if (asyncCallBackObject != null) {
                asyncCallBackObject.done(e);
            }
        });
    }

    public void updateMaintenance(String serverName, AsyncCallBackObject<ServerModel> asyncCallBackObject) {
        executeInTransaction(entityManager -> {
            final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            final CriteriaQuery<ServerModel> query = criteriaBuilder.createQuery(entityClass);
            final Root<ServerModel> root = query.from(entityClass);

            query.select(root);
            query.where(criteriaBuilder.equal(root.get("name"), serverName));

            ServerModel e = entityManager.createQuery(query).getSingleResult();
            e.setMaintenance(!e.isMaintenance());

            ServerModel model = entityManager.merge(e);
            if (asyncCallBackObject != null) {
                asyncCallBackObject.done(model);
            }
        });
    }
}
