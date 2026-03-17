package gmae.model;

import java.util.Objects;

public class RealmAdapter implements RealmView {
    private final Realm adaptee;

    public RealmAdapter(Realm adaptee) {
        if (adaptee == null) {
            throw new IllegalArgumentException("Realm must not be null");
        }
        this.adaptee = adaptee;
    }

    @Override
    public String getId() {
        return adaptee.getRealmId();
    }

    @Override
    public String getName() {
        return adaptee.getRealmName();
    }

    @Override
    public String getDescription() {
        return adaptee.getDescription() == null ? "" : adaptee.getDescription();
    }

    @Override
    public String getCoordinates() {
        return adaptee.getCoordinates() == null ? "" : adaptee.getCoordinates();
    }

    @Override
    public LocalTimeRule getLocalTimeRule() {
        return adaptee.getLocalTimeRule();
    }

    @Override
    public DateTime convertFromWorldClock(DateTime worldTime) {
        if (worldTime == null) {
            throw new IllegalArgumentException("World time must not be null");
        }
        if (adaptee.getLocalTimeRule() == null) {
            return worldTime;
        }
        return adaptee.getLocalTimeRule().applyRule(worldTime);
    }

    @Override
    public DateTime convertToWorldClock(DateTime localTime) {
        if (localTime == null) {
            throw new IllegalArgumentException("Local time must not be null");
        }
        return adaptee.convertToWorldClock(localTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RealmView other)) return false;
        return getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return getName();
    }

    public Realm getAdaptee() {
        return adaptee;
    }
}
