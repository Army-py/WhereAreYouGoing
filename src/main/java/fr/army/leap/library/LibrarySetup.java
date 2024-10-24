package fr.army.leap.library;

import com.alessiodp.libby.BukkitLibraryManager;
import com.alessiodp.libby.Library;
import fr.army.leap.LeapPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LibrarySetup {


    private final BukkitLibraryManager bukkitLibraryManager;

    public LibrarySetup(LeapPlugin plugin) {
        this.bukkitLibraryManager = new BukkitLibraryManager(plugin);
    }

    public List<Library> initLibraries() {
        final ArrayList<Library> libraries = new ArrayList<>();
        Arrays.stream(Libraries.values()).forEach(library -> libraries.add(createLibrary(library)));
        return libraries;
    }

    public void loadLibraries() {
        bukkitLibraryManager.addMavenCentral();
        initLibraries().forEach(bukkitLibraryManager::loadLibrary);
    }

    public BukkitLibraryManager getLibraryManager() {
        return bukkitLibraryManager;
    }

    private Library createLibrary(Libraries library) {
        return Library.builder()
                .groupId(library.getGroupID())
                .artifactId(library.getArtifactID())
                .version(library.getVersion())
                .resolveTransitiveDependencies(true)
                .build();
    }
}
