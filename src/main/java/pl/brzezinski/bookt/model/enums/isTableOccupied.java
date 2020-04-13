package pl.brzezinski.bookt.model.enums;

public enum isTableOccupied {

    TRUE("Table is occupied"),
    FALSE("Table if free");

    String description;

    isTableOccupied(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
