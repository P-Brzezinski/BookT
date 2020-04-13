package pl.brzezinski.bookt.model.enums;

public enum Genre {

    POLISH("Polish Cuisine"),
    ITALIAN("Italian cuisine"),
    GREEK("Greek cuisine"),
    SUSHI("Sushi");

    private String description;

    Genre(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
