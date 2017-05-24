package admin;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClassCourseData {

    SimpleIntegerProperty classCourse_ClassID, classCourse_Course_ID;
    SimpleStringProperty classCourse_ClassName, classCourse_CourseName;


    public ClassCourseData(int classCourse_ClassID, int classCourse_Course_ID,
                    String classCourse_ClassName, String classCourse_CourseName){

        this.classCourse_ClassID = new SimpleIntegerProperty(classCourse_ClassID);
        this.classCourse_Course_ID = new SimpleIntegerProperty(classCourse_Course_ID);
        this.classCourse_ClassName = new SimpleStringProperty(classCourse_ClassName);
        this.classCourse_CourseName = new SimpleStringProperty(classCourse_CourseName);
    }


    @Override
    public String toString() {
        return this.classCourse_ClassID + " " + this.classCourse_Course_ID + " " + this.classCourse_ClassName+ " "+this.classCourse_CourseName;
    }

    public int getClassCourse_ClassID() {
        return classCourse_ClassID.get();
    }

    public SimpleIntegerProperty classCourse_ClassIDProperty() {
        return classCourse_ClassID;
    }

    public void setClassCourse_ClassID(int classCourse_ClassID) {
        this.classCourse_ClassID.set(classCourse_ClassID);
    }

    public int getClassCourse_Course_ID() {
        return classCourse_Course_ID.get();
    }

    public SimpleIntegerProperty classCourse_Course_IDProperty() {
        return classCourse_Course_ID;
    }

    public void setClassCourse_Course_ID(int classCourse_Course_ID) {
        this.classCourse_Course_ID.set(classCourse_Course_ID);
    }

    public String getClassCourse_ClassName() {
        return classCourse_ClassName.get();
    }

    public SimpleStringProperty classCourse_ClassNameProperty() {
        return classCourse_ClassName;
    }

    public void setClassCourse_ClassName(String classCourse_ClassName) {
        this.classCourse_ClassName.set(classCourse_ClassName);
    }

    public String getClassCourse_CourseName() {
        return classCourse_CourseName.get();
    }

    public SimpleStringProperty classCourse_CourseNameProperty() {
        return classCourse_CourseName;
    }

    public void setClassCourse_CourseName(String classCourse_CourseName) {
        this.classCourse_CourseName.set(classCourse_CourseName);
    }
}
