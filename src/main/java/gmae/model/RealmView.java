package gmae.model;

public interface RealmView {
    String getId();
    String getName();
    String getDescription();
    String getCoordinates();
    LocalTimeRule getLocalTimeRule();
    DateTime convertFromWorldClock(DateTime worldTime);
    DateTime convertToWorldClock(DateTime localTime);
}