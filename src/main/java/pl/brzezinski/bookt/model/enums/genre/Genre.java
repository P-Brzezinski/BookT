package pl.brzezinski.bookt.model.enums.genre;

public enum Genre {

    POLISH("Polish Cuisine"),
    ITALIAN("Italian cuisine"),
    GREEK("Greek cuisine"),
    SUSHI("Sushi");

    private String descirption;

    Genre(String descirption) {
        this.descirption = descirption;
    }

    public String getDescirption() {
        return descirption;
    }
}
