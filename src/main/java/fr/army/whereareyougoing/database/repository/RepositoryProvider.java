package fr.army.whereareyougoing.database.repository;

import fr.army.whereareyougoing.database.EMFLoader;
import fr.army.whereareyougoing.database.repository.impl.ServerRepository;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RepositoryProvider {

    private final EMFLoader emfLoader;
    private final Map<Class<? extends AbstractRepository<?>>, AbstractRepository<?>> repositoryCache;

    public RepositoryProvider(@NotNull EMFLoader emfLoader) {
        this.emfLoader = emfLoader;
        this.repositoryCache = new ConcurrentHashMap<>();
        initRepositories();
    }

    public EMFLoader getEmfLoader() {
        return emfLoader;
    }

    public <T extends AbstractRepository<?>> T getRepository(Class<T> repositoryClass) {
        return repositoryClass.cast(repositoryCache.get(repositoryClass));
    }

    private void initRepositories() {
        repositoryCache.put(ServerRepository.class, new ServerRepository(emfLoader));
    }
}
