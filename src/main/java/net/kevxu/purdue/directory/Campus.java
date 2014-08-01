package net.kevxu.purdue.directory;

public enum Campus {

    WEST_LAFAYETTE("west lafayette"),
    CALUMET("calumet"),
    FORT_WAYNE("fort wayne"),
    NORTH_CENTRAL("north central");

    private String name;
    Campus(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

}