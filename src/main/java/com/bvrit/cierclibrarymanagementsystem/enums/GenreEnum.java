package com.bvrit.cierclibrarymanagementsystem.enums;

public enum GenreEnum {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    AUTO_BIOGRAPHY("Auto-biography"),
    BIOGRAPHY("Biography"),
    CRIME("Crime"),
    FANTASY("Fantasy"),
    HISTORY("History"),
    HORROR("Horror"),
    MYSTERY("Mystery"),
    ROMANCE("Romance"),
    SCIENCE_FICTION("Science Fiction"),
    SELF_HELP("Self-help"),
    THRILLER("Thriller");

    private final String displayName;

    GenreEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
