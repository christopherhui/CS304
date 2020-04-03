package ca.ubc.cs304.model;

public class Platform {
    private final String name;
    private final String type;

    public Platform(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
