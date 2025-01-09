package ru.stqa.mantis.model;

public record UserData(String username, String name, String email, String password) {
    public UserData() {
        this("", "", "", "");
    }

    public UserData withUsername(String username) {
        return new UserData(username, this.name, username + "@localhost", "password");
    }

    public UserData withRealname(String name) {
        return new UserData(this.username, name, this.username + "@localhost", "password");
    }
}
