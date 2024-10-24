package fr.army.leap.database.exception.impl;

import fr.army.leap.database.exception.RepositoryException;

public class EntityNotFoundException extends RepositoryException {

    public EntityNotFoundException(Class<?> entityClass) {
        super("Entity not found", "Entity " + entityClass.getSimpleName() + " not found");
    }
}
