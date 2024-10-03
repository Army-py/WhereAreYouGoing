package fr.army.whereareyougoing.database.exception.impl;

import fr.army.whereareyougoing.database.exception.RepositoryException;

public class EntityManagerNotInitializedException extends RepositoryException {

    public EntityManagerNotInitializedException() {
        super("Entity manager not initialized", "Entity manager not initialized");
    }
}
