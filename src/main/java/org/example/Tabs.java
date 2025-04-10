package org.example;

public enum Tabs {
    Jobs("Jobs"),
    Trainings("Trainings"),
    Companies("Companies"),
    All_Categories("All Categories"),
    Information_Technologies("Information technologies"),
    Filter_Sport("Sport");

    private final String displayName;

    Tabs(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
