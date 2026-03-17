package gmae.model;

public class Realm {
    private static int nextRealmId = 1; // Static counter for generating unique realm IDs
    private String realmId;
    private String name;
    private String description;
    private String coordinates;
    private LocalTimeRule localTimeRule;

    public Realm(String realmName, LocalTimeRule localTimeRule) {
        this.realmId = "R" + nextRealmId++;
        this.name = realmName;
        this.localTimeRule = localTimeRule;
    }

    public Realm(String realmName, String description, String coordinates, LocalTimeRule localTimeRule) {
        this.realmId = "R" + nextRealmId++;
        this.name = realmName;
        this.description = description;
        this.coordinates = coordinates;
        this.localTimeRule = localTimeRule;
    }

    public Realm(String realmName, String description, LocalTimeRule localTimeRule) {
        this.realmId = "R" + nextRealmId++;
        this.name = realmName;
        this.description = description;
        this.localTimeRule = localTimeRule;
    }

    public DateTime convertFromWorldClock(DateTime worldTime) {
        // Implementation for calculating the local time in the realm based on the world time and the local time rule
        return localTimeRule.applyRule(worldTime);
    }

    public DateTime convertToWorldClock(DateTime localTime) {
        // Implementation for calculating the world time based on the local time in the realm and the local time rule
        // This would involve reversing the application of the local time rule to get back to world time
        // TO DO
        return localTime; // Placeholder return statement, replace with actual conversion logic
    }

    // ==================================
    // Getters and setters for the fields
    // ==================================

    public String getRealmId() {
        return realmId;
    }

    public LocalTimeRule getLocalTimeRule() {
        return localTimeRule;
    }

    public String getRealmName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCoordinates() {
        return coordinates;
    }
}