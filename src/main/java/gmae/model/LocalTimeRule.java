package gmae.model;

public class LocalTimeRule {
    private int offsetMinutes;
    private float dayLengthMultiplier;

    public LocalTimeRule(int offsetMinutes) {
        this.offsetMinutes = offsetMinutes;
    }

    public LocalTimeRule(float dayLengthMultiplier) {
        this.dayLengthMultiplier = dayLengthMultiplier;
    }

    public DateTime applyRule(DateTime worldTime) {
        // Implementation for applying the local time rule to the given world time
        // This would involve adjusting the world time based on the offset and day length multiplier
        // Currently Use offsetMinutes to calculate the local time based on the world time
        return worldTime.plusMinutes(offsetMinutes);
    }

    // ==================================
    // Getters and setters for the fields
    // ==================================

    public void displayRule(){
        if (offsetMinutes != 0) {
            System.out.println("Local Time Rule: Offset of " + offsetMinutes + " minutes from world time.");
        } else if (dayLengthMultiplier != 0) {
            System.out.println("Local Time Rule: Day length is multiplied by " + dayLengthMultiplier + " compared to world time.");
        } else {
            System.out.println("Local Time Rule: No specific rule applied.");
        }
    }

    public int getOffsetMinutes() {
        return offsetMinutes;
    }

    public float getDayLengthMultiplier() {
        return dayLengthMultiplier;
    }
}

