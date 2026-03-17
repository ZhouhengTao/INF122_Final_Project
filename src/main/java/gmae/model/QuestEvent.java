package gmae.model;

import java.util.ArrayList;

public class QuestEvent {
    private static int nextEventId = 1; // Static counter for generating unique event IDs
    private String eventId;
    private String title;
    private DateTime startTime;
    private DateTime endTime = null;
    private String realmId;
    private ArrayList<Character> participants = new ArrayList<>();

    public QuestEvent(String title, DateTime startTime, DateTime endTime, String realmId) {
        this.eventId = "E" + nextEventId++;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.realmId = realmId;
    }

    public void addParticipant(Character character) {
        // Implementation for adding a participant to the quest event
        this.participants.add(character);
    }

    public void removeParticipant(Character character) {
        // Implementation for removing a participant from the quest event
        this.participants.remove(character);
    }

    public void grantLoot(Item item, Character character) {
        // Implementation for granting loot to a character
    }

    // ==================================
    // Getters and setters for the fields
    // ==================================

    public String getEventId() {
        return eventId;
    }

    public String getTitle() {
        return title;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public String getRealmId() {
        return realmId;
    }

    public ArrayList<Character> getParticipants() {
        return participants;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public void updateEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    public void updateRealm(Realm realm) {
        this.realmId = realm.getRealmId();
    }
}
