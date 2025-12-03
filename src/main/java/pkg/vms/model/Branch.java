package pkg.vms.model;

import javafx.beans.property.*;

public class Branch {

    private IntegerProperty branchId;
    private StringProperty location;
    private StringProperty responsibleUser;
    private IntegerProperty refCompany;
    private StringProperty address;
    private StringProperty phone;

    public Branch(int branchId, String location, String responsibleUser) {
        this.branchId = new SimpleIntegerProperty(branchId);
        this.location = new SimpleStringProperty(location);
        this.responsibleUser = new SimpleStringProperty(responsibleUser);

        // Optionally, initialize other properties to default values
        this.refCompany = new SimpleIntegerProperty();
        this.address = new SimpleStringProperty("");
        this.phone = new SimpleStringProperty("");
    }

    public Branch(int branchId, String location, String responsibleUser, int refCompany, String addressBranch, String phoneBranch) {
    }


    public int getBranchId() { return branchId.get(); }
    public IntegerProperty branchIdProperty() { return branchId; }

    public String getLocation() { return location.get(); }
    public StringProperty locationProperty() { return location; }

    public String getResponsibleUser() { return responsibleUser.get(); }
    public StringProperty responsibleUserProperty() { return responsibleUser; }

    public int getRefCompany() { return refCompany.get(); }
    public IntegerProperty refCompanyProperty() { return refCompany; }

    public String getAddress() { return address.get(); }
    public StringProperty addressProperty() { return address; }

    public String getPhone() { return phone.get(); }
    public StringProperty phoneProperty() { return phone; }


    // Setters
    public void setLocation(String location) {
        this.location.set(location);
    }

    public void setResponsibleUser(String responsibleUser) {
        this.responsibleUser.set(responsibleUser);
    }

    public void setRefCompany(int refCompany) {
        this.refCompany.set(refCompany);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }
}
