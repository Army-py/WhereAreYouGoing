package fr.army.whereareyougoing.database.exception.impl;

import fr.army.whereareyougoing.database.exception.RepositoryException;

public class EntityNotFoundException extends RepositoryException {

    public EntityNotFoundException(Class<?> entityClass) {
        super("Entity not found", "Entity " + entityClass.getSimpleName() + " not found");
    }
}
