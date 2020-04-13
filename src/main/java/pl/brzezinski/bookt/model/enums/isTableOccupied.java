package pl.brzezinski.bookt.model.enums;

public enum isTableOccupied {

    TRUE("Table is free"),
    FALSE("Table if occupied");

    String description;

    isTableOccupied(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
