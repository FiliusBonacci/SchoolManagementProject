package admin;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClassCourseData {

    StringProperty classCourse_ClassID, classCourse_Course_ID;
    StringProperty classCourse_ClassName, classCourse_CourseName;


    public ClassCourseData(String classCourse_ClassID, String classCourse_Course_ID,
                    String classCourse_ClassName, String classCourse_CourseName){

        this.classCourse_ClassID = new SimpleStringProperty(classCourse_ClassID);
        this.classCourse_Course_ID = new SimpleStringProperty(classCourse_Course_ID);
        this.classCourse_Course_ID = new SimpleStringProperty(classCourse_ClassName);
        this.classCourse_CourseName = new SimpleStringProperty(classCourse_CourseName);
    }


    public String getClassCourse_ClassID() {
        return classCourse_ClassID.get();
    }

    public StringProperty classCourse_ClassIDProperty() {
        return classCourse_ClassID;
    }

    public void setClassCourse_ClassID(String classCourse_ClassID) {
        this.classCourse_ClassID.set(classCourse_ClassID);
    }

    public String getClassCourse_Course_ID() {
        return classCourse_Course_ID.get();
    }

    public StringProperty classCourse_Course_IDProperty() {
        return classCourse_Course_ID;
    }

    public void setClassCourse_Course_ID(String classCourse_Course_ID) {
        this.classCourse_Course_ID.set(classCourse_Course_ID);
    }

    public String getClassCourse_ClassName() {
        return classCourse_ClassName.get();
    }

    public StringProperty classCourse_ClassNameProperty() {
        return classCourse_ClassName;
    }

    public void setClassCourse_ClassName(String classCourse_ClassName) {
        this.classCourse_ClassName.set(classCourse_ClassName);
    }

    public String getClassCourse_CourseName() {
        return classCourse_CourseName.get();
    }

    public StringProperty classCourse_CourseNameProperty() {
        return classCourse_CourseName;
    }

    public void setClassCourse_CourseName(String classCourse_CourseName) {
        this.classCourse_CourseName.set(classCourse_CourseName);
    }
}
