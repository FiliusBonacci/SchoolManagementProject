package admin;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CourseData {

    private final StringProperty course_id;
    private final StringProperty name;
    private final StringProperty description;
    private final StringProperty price;


    public CourseData(String course_id, String name, String description, String price) {
        this.course_id = new SimpleStringProperty(course_id);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleStringProperty(price);
    }

    public String getCourse_id() {
        return course_id.get();
    }

    public StringProperty course_idProperty() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id.set(course_id);
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

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getPrice() {
        return price.get();
    }

    public StringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }
}
