package admin;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClassData {

    private final StringProperty ID;
    private final StringProperty name;
    private final StringProperty year;
    private final StringProperty remarks;
    private final StringProperty status;

    public ClassData(String ID, String name, String year, String remarks, String status) {
        this.ID = new SimpleStringProperty(ID);
        this.name = new SimpleStringProperty(name);
        this.year = new SimpleStringProperty(year);
        this.remarks = new SimpleStringProperty(remarks);
        this.status = new SimpleStringProperty(status);
    }

    public String getID() {
        return ID.get();
    }

    public StringProperty IDProperty() {
        return ID;
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getYear() {
        return year.get();
    }

    public StringProperty yearProperty() {
        return year;
    }

    public void setYear(String year) {
        this.year.set(year);
    }

    public String getRemarks() {
        return remarks.get();
    }

    public StringProperty remarksProperty() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks.set(remarks);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
