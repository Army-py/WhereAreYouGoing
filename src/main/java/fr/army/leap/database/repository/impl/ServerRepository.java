package fr.army.leap.database.repository.impl;

import fr.army.leap.database.EMFLoader;
import fr.army.leap.database.model.impl.ServerModel;
import fr.army.leap.database.repository.AbstractRepository;
import fr.army.leap.database.repository.callback.AsyncCallBackObject;
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
}
