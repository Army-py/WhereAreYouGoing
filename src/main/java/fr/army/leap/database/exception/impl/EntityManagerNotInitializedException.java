package fr.army.leap.database.exception.impl;

import fr.army.leap.database.exception.RepositoryException;

public class EntityManagerNotInitializedException extends RepositoryException {

    public EntityManagerNotInitializedException() {
        super("Entity manager not initialized", "Entity manager not initialized");
    }
}
