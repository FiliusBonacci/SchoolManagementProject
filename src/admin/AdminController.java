package admin;

import dbConnection.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Created by pawel on 15.04.2017.
 */
public class AdminController implements Initializable {

    // dla studentow
    @FXML
    private TextField idField, teacher_idField, courseIDField, couseNameField;
    @FXML
    private TextField firstNameField, teacher_firstNameField;
    @FXML
    private TextField lastNameField, teacher_lastNameField;
    @FXML
    private DatePicker birthDayField, teacher_birthDayField;
    @FXML
    private TextField emailField, teacher_emailField;
    @FXML
    private PasswordField passwordField , teacher_passwordField;
    @FXML
    TextArea courseDescriptionField;


    ObservableList gCombo =  FXCollections.observableArrayList();
    @FXML
    ComboBox courseGrade_combobox ;
    @FXML
    private TableView<StudentData> listOfStudents;
    @FXML
    private TableView<TeacherData> listOfTeachers;
    @FXML
    private TableColumn<StudentData, String> idColumn;
    @FXML
    private TableColumn<TeacherData, String> t_idColumn;
    @FXML
    private TableColumn<StudentData, String> birthDayColumn;
    @FXML
    private TableColumn<TeacherData, String> t_birthDayColumn;
    @FXML
    private TableColumn<StudentData, String> firstNameColumn;
    @FXML
    private TableColumn<TeacherData, String> t_firstNameColumn;
    @FXML
    private TableColumn<StudentData, String> lastNameColumn;
    @FXML
    private TableColumn<TeacherData, String> t_lastNameColumn;
    @FXML
    private TableColumn<StudentData, String> emailColumn;
    @FXML
    private TableColumn<TeacherData, String> t_emailColumn;
    @FXML
    private TableColumn<StudentData, String> passwordColumn;
    @FXML
    private TableColumn<TeacherData, String> t_passwordColumn;
    // kolumny kursów
    @FXML
    private TableColumn<StudentData, String> course_LP_column;
    @FXML
    private TableColumn<StudentData, String> courseNameColumn;
    @FXML
    private TableColumn<StudentData, String> courseDescColumn;
    @FXML
    private TableColumn<StudentData, String> coursePriceColumn;

    // ./
    @FXML
    private Button teacher_loadListBtn,listAllStudentsBtn, course_loadListBtn;

    @FXML
    Tab students_tab, teacher_tab, course_tab, class_tab;



    private dbConnection db;
    @FXML
    private ObservableList<StudentData> studentList;
    @FXML
    private ObservableList<TeacherData> teacherList;
    @FXML
    private TableView<CourseData> listOfCourses;
    @FXML
    private ObservableList<CourseData> courseList;
    @FXML
    private TextField coursePriceField;


    // dla Klas

    @FXML
    TextField classIDField, classNameField, classYearField, classRemarksField,classStatusField;
    @FXML
    TableColumn class_LP_column, classNameColumn, classYearColumn, classRemarksColumn,classStatusColumn;
    @FXML
    TableView<ClassData> listOfClasses;
    ObservableList<ClassData> classesList;
    //-------------------------------------



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.db = new dbConnection();

            ObservableList<StudentData> selectedRow = listOfStudents.getSelectionModel().getSelectedItems();
            selectedRow.addListener(new ListChangeListener() {
                @Override
                public void onChanged(Change c) throws NullPointerException {

                    try {

                        StudentData selectedR = listOfStudents.getSelectionModel().getSelectedItem();

                        // zanim ustawi poprawne ID znajdz w tabeli
                        String ssid = findProperID();
                        idField.setText(ssid);


                        firstNameField.setText(selectedR.getFirstName());
                        lastNameField.setText(selectedR.getLastName());

                        // ustawianie pola daty
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                        try {
                            birthDayField.setValue(LocalDate.parse(selectedR.getBirthDay(), formatter));
                        } catch (DateTimeParseException | NullPointerException e) {
                            birthDayField.setValue(null);
                        }

                        emailField.setText(selectedR.getEmail());
                        passwordField.setText(selectedR.getPassword());


                        System.out.println(selectedR.getFirstName());

                    } catch (NullPointerException e) {
                        firstNameField.setText(null);
                        lastNameField.setText(null);
                        idField.setText(null);
                        emailField.setText(null);
                        birthDayField.setValue(null);
                        passwordField.setText(null);
                    }

                }
            });


    }


    private int idCounter = 1;

    @FXML
    public void loadStudentData(ActionEvent actionEvent) {
        String query = "SELECT * from students;";
        try {

            Connection conn = dbConnection.getConnection();
            this.studentList = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(query);

            while (rs.next()) {
                this.studentList.add(new StudentData(
                        String.valueOf(idCounter++),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                ));
            }
        } catch (SQLException er) {
            er.printStackTrace();
        }

        this.idColumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("ID"));
        this.firstNameColumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("firstName"));
        this.lastNameColumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("lastName"));
        this.birthDayColumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("birthDay"));
        this.emailColumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("email"));
        this.passwordColumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("password"));

        this.listOfStudents.setItems(null);
        this.listOfStudents.setItems(this.studentList);

        idCounter = 1;
    }

    @FXML
    public void addStudent(ActionEvent actionEvent) {
        String insertQuery = "insert into students(student_id, firstname, lastname, birthDay, email, password) " +
                "values (?,?,?,?,?,?)";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement stm = conn.prepareStatement(insertQuery);

            stm.setString(1, String.valueOf(this.idField.getText()));
            stm.setString(2, String.valueOf(AdminController.this.firstNameField.getText()));
            stm.setString(3, String.valueOf(AdminController.this.lastNameField.getText()));
            stm.setString(4, String.valueOf(AdminController.this.birthDayField.getEditor().getText()));
            stm.setString(5, String.valueOf(AdminController.this.emailField.getText()));
            stm.setString(6, String.valueOf(AdminController.this.passwordField.getText()));

            stm.execute();
            conn.close();

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clearFields(ActionEvent event) {
        this.idField.setText("");
        this.firstNameField.setText("");
        this.lastNameField.setText("");
        this.birthDayField.setValue(null);
    }

    @FXML
    public void deleteStudent(ActionEvent event) {

        StudentData getSelectedRow = listOfStudents.getSelectionModel().getSelectedItem();
        try {
            Connection conn = dbConnection.getConnection();

            if (!getSelectedRow.toString().equals("")) {


                String fn = getSelectedRow.getFirstName();
                String ln = getSelectedRow.getLastName();
                String sID = null;

                PreparedStatement ps = conn.prepareStatement("SELECT student_id FROM students where firstName = ? AND lastName = ? ;");


                ps.setString(1, fn);
                ps.setString(2, ln);
//                ps.execute();
                ResultSet res = ps.executeQuery();

                while (res.next()) {
                    sID = res.getString(1);
                }
                System.out.println(sID);

                String deleteQuery = "delete from students where student_id=" + idField.getText() + ";";

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Potwierdzenie usunięcia");
                alert.setHeaderText("Chcesz usunąć studenta?");
//            alert.setContentText("Are you ok with this?");
                alert.setContentText("" + getSelectedRow.getFirstName() + " " + getSelectedRow.getLastName());

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    PreparedStatement stm = conn.prepareStatement(deleteQuery);
                    stm.execute();

                    listAllStudentsBtn.fire();

                } else {
                    // ... user chose CANCEL or closed the dialog
                }

            }

//            stm.execute();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void editStudent(ActionEvent event) {

        String updateQuery = "update students set firstname=? , lastname=? , birthDay=? , email=?, password=?" +
                "where student_id = ?";
//                "where (firstname=? and lastname=?)";

        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement stm = conn.prepareStatement(updateQuery);


            stm.setString(1, String.valueOf(AdminController.this.firstNameField.getText()));
            stm.setString(2, String.valueOf(AdminController.this.lastNameField.getText()));
            stm.setString(3, String.valueOf(AdminController.this.birthDayField.getEditor().getText()));
            stm.setString(4, String.valueOf(AdminController.this.emailField.getText()));
            stm.setString(5, String.valueOf(AdminController.this.passwordField.getText()));
            stm.setString(6, String.valueOf(AdminController.this.idField.getText()));

            stm.execute();


            conn.close();

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }


    public String findProperID() {
        try {

            StudentData getSelectedRow = this.listOfStudents.getSelectionModel().getSelectedItem();
            String fn = getSelectedRow.getFirstName();
            String ln = getSelectedRow.getLastName();
            String sID = null;


            try {
                Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT student_id FROM students where firstName = ? AND lastName = ? ;");


                ps.setString(1, fn);
                ps.setString(2, ln);

                ResultSet res = ps.executeQuery();

                while (res.next()) {
                    sID = res.getString(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return sID;
        } catch (NullPointerException e){

        }
        return null;
    }

    /*******************************
     ***** zarządzanie nauczycielami
     ********************************/

    @FXML
    public void loadTeacherData(ActionEvent actionEvent) {
        String query = "SELECT * from teacher;";
        try {

            Connection conn = dbConnection.getConnection();
            this.teacherList = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(query);

            while (rs.next()) {
                this.teacherList.add(new TeacherData(
                        String.valueOf(idCounter++),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                ));
            }
        } catch (SQLException er) {
            er.printStackTrace();
        }

        this.t_idColumn.setCellValueFactory(new PropertyValueFactory<TeacherData, String>("ID"));
        this.t_firstNameColumn.setCellValueFactory(new PropertyValueFactory<TeacherData, String>("firstName"));
        this.t_lastNameColumn.setCellValueFactory(new PropertyValueFactory<TeacherData, String>("lastName"));
        this.t_birthDayColumn.setCellValueFactory(new PropertyValueFactory<TeacherData, String>("birthDay"));
        this.t_emailColumn.setCellValueFactory(new PropertyValueFactory<TeacherData, String>("email"));
        this.t_passwordColumn.setCellValueFactory(new PropertyValueFactory<TeacherData, String>("password"));

        //this.listOfTeachers.setItems(null);
        this.listOfTeachers.setItems(this.teacherList);

        idCounter = 1;
    }
    @FXML
    private void clearTeacherFields(ActionEvent event) {
        this.teacher_idField.setText("");
        this.teacher_firstNameField.setText("");
        this.teacher_lastNameField.setText("");
        this.teacher_birthDayField.setValue(null);
    }

    @FXML
    public void deleteTeacher(ActionEvent event) {

        TeacherData getSelectedRow = listOfTeachers.getSelectionModel().getSelectedItem();
        try {
            Connection conn = dbConnection.getConnection();

            if (!getSelectedRow.toString().equals("")) {


                String fn = getSelectedRow.getFirstName();
                String ln = getSelectedRow.getLastName();
                String sID = null;

                PreparedStatement ps = conn.prepareStatement("SELECT teacher_id FROM teacher where firstName = ? AND lastName = ? ;");


                ps.setString(1, fn);
                ps.setString(2, ln);
//                ps.execute();
                ResultSet res = ps.executeQuery();

                while (res.next()) {
                    sID = res.getString(1);
                }
                System.out.println(sID);

                String deleteQuery = "delete from teacher where teacher_id=" + teacher_idField.getText() + ";";

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Potwierdzenie usunięcia");
                alert.setHeaderText("Chcesz usunąć nauczyciela?");

                alert.setContentText("" + getSelectedRow.getFirstName() + " " + getSelectedRow.getLastName());

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    PreparedStatement stm = conn.prepareStatement(deleteQuery);
                    stm.execute();

                    teacher_loadListBtn.fire();

                } else {
                    // ... user chose CANCEL or closed the dialog
                }

            }

//            stm.execute();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void editTeacher(ActionEvent event) {

        String updateQuery = "update teacher set firstName=? , lastName=? , birthDay=?, email=?, password=? " +
                "where teacher_id = ? ;";
//                "where (firstname=? and lastname=?)";

        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement stm = conn.prepareStatement(updateQuery);


            stm.setString(1, String.valueOf(this.teacher_firstNameField.getText()));
            stm.setString(2, String.valueOf(this.teacher_lastNameField.getText()));
            stm.setString(3, String.valueOf(this.teacher_birthDayField.getEditor().getText()));
            stm.setString(4, String.valueOf(this.teacher_emailField.getText()));
            stm.setString(5, String.valueOf(this.teacher_passwordField.getText()));
            stm.setString(6, String.valueOf(this.teacher_idField.getText()));



            stm.execute();

            conn.close();

            teacher_loadListBtn.fire();

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }


    public String findProperIDForTeachers() {
        try {
            TeacherData getSelectedRow = this.listOfTeachers.getSelectionModel().getSelectedItem();
            String fn = getSelectedRow.getFirstName();
            String ln = getSelectedRow.getLastName();
            String sID = null;


            try {
                Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT teacher_id FROM teacher where firstName = ? AND lastName = ? ;");


                ps.setString(1, fn);
                ps.setString(2, ln);

                ResultSet res = ps.executeQuery();

                while (res.next()) {
                    sID = res.getString(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return sID;
        } catch (NullPointerException e){

        }
        return null;
    }

    @FXML
    public void addTeacher(ActionEvent actionEvent) {
        String insertQuery = "insert into teacher(teacher_id, firstname, lastname, birthDay, email, password) " +
                "values (?,?,?,?,?,?)";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement stm = conn.prepareStatement(insertQuery);

            stm.setString(1, String.valueOf(this.teacher_idField.getText()));
            stm.setString(2, String.valueOf(this.teacher_firstNameField.getText()));
            stm.setString(3, String.valueOf(this.teacher_lastNameField.getText()));
            stm.setString(4, String.valueOf(this.teacher_birthDayField.getEditor().getText()));
            stm.setString(5, String.valueOf(this.teacher_emailField.getText()));
            stm.setString(6, String.valueOf(this.teacher_passwordField.getText()));

            stm.execute();
            conn.close();

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void selectedTab(){
        if (teacher_tab.isSelected()){
            ObservableList<TeacherData> selectedRow = listOfTeachers.getSelectionModel().getSelectedItems();
            // ---------------

            selectedRow.addListener(new ListChangeListener() {
                @Override
                public void onChanged(Change c) throws NullPointerException {

                    try {
                        TeacherData selectedR = listOfTeachers.getSelectionModel().getSelectedItem();

                        // zanim ustawi poprawne ID znajdz w tabeli
                        String ssid = findProperIDForTeachers();
                        teacher_idField.setText(ssid);
                        teacher_lastNameField.setText(selectedR.getLastName());
                        teacher_firstNameField.setText(selectedR.getFirstName());

                        // ustawianie pola daty
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                        try {
                            teacher_birthDayField.setValue(LocalDate.parse(selectedR.getBirthDay(), formatter));
                        } catch (DateTimeParseException | NullPointerException e) {
                            teacher_birthDayField.setValue(null);
                        }

                        teacher_emailField.setText(selectedR.getEmail());
                        teacher_passwordField.setText(selectedR.getPassword());

                        System.out.println(selectedR.getFirstName());

                    } catch (NullPointerException e) {
                        teacher_idField.setText(null);
                        teacher_firstNameField.setText(null);
                        teacher_birthDayField.setValue(null);
                        teacher_lastNameField.setText(null);
                        teacher_emailField.setText(null);
                        teacher_passwordField.setText(null);
                    }

                }
            });
        }
        
        if (course_tab.isSelected()){
            loadGradeCombo();

            ObservableList<CourseData> selectedRow = listOfCourses.getSelectionModel().getSelectedItems();
            selectedRow.addListener(new ListChangeListener() {
                @Override
                public void onChanged(Change c) throws NullPointerException {

                    try {
                        CourseData selectedR = listOfCourses.getSelectionModel().getSelectedItem();

                        // zanim ustawi poprawne ID znajdz w tabeli
                        String ssid = findProperIDForCourses();
                        courseIDField.setText(ssid);
                        couseNameField.setText(selectedR.getName());
                        courseDescriptionField.setText(selectedR.getDescription());
                        coursePriceField.setText(selectedR.getPrice());


                    } catch (NullPointerException e) {
                        courseIDField.setText(null);
                        couseNameField.setText(null);
                        courseDescriptionField.setText(null);
                    }

                }
            });

        }
    }



    /*******************************
     ***** zarządzanie kursami
     ********************************/


    private String findProperIDForCourses() {
        String sID = null;
        try {
            CourseData getSelectedRow = this.listOfCourses.getSelectionModel().getSelectedItem();
            String fn = getSelectedRow.getName();
            String ln = getSelectedRow.getDescription();

            try {
                Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT course_id FROM course where name = ? AND description = ? ;");

                ps.setString(1, fn);
                ps.setString(2, ln);

                ResultSet res = ps.executeQuery();

                while (res.next()) {
                    sID = res.getString(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return sID;
        } catch (NullPointerException e){

        }
        return null;
    }

    @FXML
    public void clearCourseFields(ActionEvent event) {
    }
    @FXML
    public void editCourse(ActionEvent event) {
    }
    @FXML
    public void deleteCourse(ActionEvent event) {
    }
    @FXML
    public void loadCourseData(ActionEvent event) {

        String query = "SELECT * from course;";
        try {
            Connection conn = dbConnection.getConnection();
            this.courseList = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(query);

            while (rs.next()) {
                this.courseList.add(new CourseData(
                        String.valueOf(idCounter++),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                ));
            }
        } catch (SQLException er) {
            er.printStackTrace();
        }

        this.course_LP_column.setCellValueFactory(new PropertyValueFactory<>("course_id"));
        this.courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.courseDescColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.coursePriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        this.listOfCourses.setItems(this.courseList);

        idCounter = 1;


    }

    public void addCourse(ActionEvent event) {
    }



    // ------- załaduj comboboxa z bazy
    @FXML
    public void loadGradeCombo(){

        String query = "SELECT * from grade;";
        try{
            Connection conn = dbConnection.getConnection();
            ResultSet rs = conn.prepareStatement(query).executeQuery();

            while(rs.next()){
                String gradeName = rs.getString("name");
                gCombo.add(gradeName);
            }
            courseGrade_combobox.setItems(gCombo);

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void addClass(ActionEvent event) {
    }

    public void clearClassFields(ActionEvent event) {
    }

    public void editClass(ActionEvent event) {
    }

    public void deleteClass(ActionEvent event) {
    }

    @FXML
    public void loadClassData(ActionEvent event) {

        int idCounter = 1;
        String query = "SELECT * from class;";
        try {
            Connection conn = dbConnection.getConnection();
            this.classesList = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(query);

            while (rs.next()) {
                this.classesList.add(new ClassData(
                        String.valueOf(idCounter++),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                ));
            }
        } catch (SQLException er) {
            er.printStackTrace();
        }

        this.class_LP_column.setCellValueFactory(new PropertyValueFactory<ClassData, String>("ID"));
        this.classNameColumn.setCellValueFactory(new PropertyValueFactory<ClassData, String>("name"));
        this.classYearColumn.setCellValueFactory(new PropertyValueFactory<ClassData, String>("year"));
        this.classRemarksColumn.setCellValueFactory(new PropertyValueFactory<ClassData, String>("remarks"));
        this.classStatusColumn.setCellValueFactory(new PropertyValueFactory<ClassData, String>("status"));

        this.listOfClasses.setItems(this.classesList);

        idCounter = 1;


    }
}
