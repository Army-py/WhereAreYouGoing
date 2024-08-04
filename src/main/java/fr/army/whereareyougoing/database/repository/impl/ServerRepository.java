package fr.army.whereareyougoing.database.repository.impl;

import fr.army.whereareyougoing.database.EMFLoader;
import fr.army.whereareyougoing.database.model.impl.ServerModel;
import fr.army.whereareyougoing.database.repository.AbstractRepository;

public class ServerRepository extends AbstractRepository<ServerModel> {

    public ServerRepository(EMFLoader emfLoader, Class<ServerModel> entityClass) {
        super(emfLoader, entityClass);
    }
}
