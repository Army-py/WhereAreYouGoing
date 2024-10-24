package fr.army.leap.library;

public enum Libraries {

    SQLITE("org{}xerial", "sqlite-jdbc", "3.44.1.0"),
    HIBERNATE("org{}hibernate{}orm", "hibernate-core", "6.4.0.Final"),
    HIBERNATE_HIKARI("org.hibernate.orm", "hibernate-hikaricp", "6.4.0.Final"),
    HIBERNATE_DIALECTS("org{}hibernate{}orm", "hibernate-community-dialects", "6.4.0.Final"),
    CAFFEINE("com{}github{}ben-manes{}caffeine", "caffeine", "3.1.8"),
    ;

    private final String groupID;
    private final String artifactID;
    private final String version;

    Libraries(String groupID, String artifactID, String version) {
        this.groupID = groupID;
        this.artifactID = artifactID;
        this.version = version;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getArtifactID() {
        return artifactID;
    }

    public String getVersion() {
        return version;
    }
}
