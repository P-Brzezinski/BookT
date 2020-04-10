package pl.brzezinski.bookt.model.enums.daysOfWeek;

public enum  DaysOfWeek {

    MONDAY("Poniedziałek"),
    TUESDAY("Wtorek"),
    WEDNSDAY("Środa"),
    THURSDAY("Czwartek"),
    FRIDAY("Piątek"),
    SATURDAY("Sobota"),
    SUNDAY("Niedziela");

    String polishName;

    DaysOfWeek(String polishName) {
        this.polishName = polishName;
    }

    public String getPolishName() {
        return polishName;
    }
}
