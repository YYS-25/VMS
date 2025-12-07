package pkg.vms.model;

import javafx.beans.property.*;

public class Company {

    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty email;
    private final StringProperty industry;

    public Company(int id, String name, String email, String industry) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.industry = new SimpleStringProperty(industry);
    }

    // --- Getters ---
    public int getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getEmail() { return email.get(); }
    public String getIndustry() { return industry.get(); }

    public int getCompanyId() { return getId(); }
    public String getNameCompany() { return getName(); }
    public String getEmailCompany() { return getEmail(); }
    public String getIndustryType() { return getIndustry(); }

    // --- Property Getters (JavaFX requires these!) ---
    public IntegerProperty companyIdProperty() {
        return id;
    }

    public StringProperty nameCompanyProperty() {
        return name;
    }

    public StringProperty emailCompanyProperty() {
        return email;
    }

    public StringProperty industryTypeProperty() {
        return industry;
    }

    @Override
    public String toString() {
        return name.get();  // Show the company name in lists
    }
}
