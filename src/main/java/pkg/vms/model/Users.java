package pkg.vms.model;

import javafx.beans.property.*;

public class Users {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String password;
    private String ddl;
    private String titre;
    private String status;

    // Constructors
    public Users() {
        // empty constructor
    }

    public Users(String username, String firstName, String lastName, String email, String role, String password,
                 String ddl, String titre, String status) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.password = password;
        this.ddl = ddl;
        this.titre = titre;
        this.status = status;
    }

    // ===== JavaFX properties for TableView =====
    public StringProperty usernameProperty() { return new SimpleStringProperty(username); }
    public StringProperty firstNameProperty() { return new SimpleStringProperty(firstName); }
    public StringProperty lastNameProperty() { return new SimpleStringProperty(lastName); }
    public StringProperty emailProperty() { return new SimpleStringProperty(email); }
    public StringProperty roleProperty() { return new SimpleStringProperty(role); }
    public StringProperty statusProperty() { return new SimpleStringProperty(status); }

    // ===== Getters and Setters =====
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDdl() { return ddl; }
    public void setDdl(String ddl) { this.ddl = ddl; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
