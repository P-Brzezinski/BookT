package pl.brzezinski.bookt.model.enums.genre;

public enum Genre {

    POLISH("Kuchnia polska"),
    ITALIAN("Kuchnia włoska"),
    GREEK("Kuchnia grecka"),
    SUSHI("Sushi");

    private String polishName;

    Genre(String polishName) {
        this.polishName = polishName;
    }

    public String getPolishName() {
        return polishName;
    }
}
