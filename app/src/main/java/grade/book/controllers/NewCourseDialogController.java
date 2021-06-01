package grade.book.controllers;

import grade.book.CourseInfo;
import grade.book.GradeBookApp;
import grade.book.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class NewCourseDialogController {

    private GradeBookApp app;
    private Stage stage;
    private String semester;

    // external either Fall or Spring courseList from SchoolYearOverview that the new course will be added to.
    // As a side note: course will be added to app studentInformationApp SchoolYearInfo as well
    @FXML
    private ListView<CourseInfo> schoolYearOverviewCourseList;

    @FXML
    private TextField courseNameTextField;

    @FXML
    private TextField coursePeriodTextField;


    @FXML
    private TableView<Student> studentTableView;
    @FXML
    private TableColumn<Student, String> studentIdColumn;
    @FXML
    private TableColumn<Student, String> firstNameColumn;
    @FXML
    private TableColumn<Student, String> lastNameColumn;

    @FXML
    private TextField studentIdTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;

    @FXML
    //container to hold students added to course in this dialog;
    private ObservableList<Student> students = FXCollections.observableArrayList();


    public NewCourseDialogController(){
    }

    @FXML
    private void initialize() {
        studentIdColumn.setCellValueFactory(cellData -> cellData.getValue().studentIdProperty());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        studentTableView.setItems(students);

    }

    @FXML
    private void handleEditStudent() {

        int selectedIndex = this.studentTableView.getSelectionModel().getSelectedIndex();
        //TODO check loaderLocation of each class to see if it matches that of the controller or see if can all set them to StudentInfoSystemApp.class.getResource("name of resource")...etc
        if( selectedIndex > -1) {
            //get student selected in studentTableView
            Student student = studentTableView.getItems().get(selectedIndex);
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(NewCourseDialogController.class.getResource("EditStudentDialog.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                Stage stage = new Stage();
                stage.setTitle("EditStudentDialog");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(app.getPrimaryStage());
                Scene scene = new Scene(page);
                stage.setScene(scene);

                EditStudentDialogController controller = loader.getController();
                controller.setStage(stage);
                controller.setStudent(student);

                stage.showAndWait();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleDeleteStudent() {
        int selectedIndex = studentTableView.getSelectionModel().getSelectedIndex();
        if(selectedIndex > -1){
            studentTableView.getItems().remove(selectedIndex);
        }
    }

    @FXML
    private void handleAddStudent() {
        if(isStudentInputValid()){
            String studentId = studentIdTextField.getText();
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();

            Student student = new Student(null, null, studentId, firstName, lastName, null);
            students.add(student);

            studentIdTextField.clear();
            firstNameTextField.clear();
            lastNameTextField.clear();

            studentIdTextField.requestFocus();
        }


    }

    @FXML
    private void handleOK(){
        if(isCourseInputValid()){
            String courseName = courseNameTextField.getText();
            String coursePeriod = coursePeriodTextField.getText();
            String sem = this.semester;
            String schoolYear = app.getSchoolYearInfo().getSchoolYear();

            for(Student student : students){
                student.setCourseName(courseName);
                student.setCoursePeriod(coursePeriod);
            }

            CourseInfo courseInfo = new CourseInfo(courseName, coursePeriod, sem, schoolYear, null, this.students);


            if (this.semester.equals("Fall")){
                app.getSchoolYearInfo().addToFallCourses(courseInfo);
            } else if (this.semester.equals("Spring")){
                app.getSchoolYearInfo().addToSpringCourses(courseInfo);
            }

            stage.close();
        }
    }

    @FXML
    private void handleCancel(){
        stage.close();
    }

    public boolean isCourseInputValid() {
        String errorMessage = "";

        if(courseNameTextField.getText() == null || courseNameTextField.getText().length() == 0){
            errorMessage += "No valid course name\n";
        }
        if(coursePeriodTextField.getText() == null || coursePeriodTextField.getText().length() == 0){
            errorMessage += "No valid course period\n";
        }

        if(errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    public boolean isStudentInputValid() {
        String errorMessage = "";

        if(studentIdTextField.getText() == null || studentIdTextField.getText().length() == 0){
            errorMessage += "No valid student ID\n";
        }
        if(firstNameTextField.getText() == null || firstNameTextField.getText().length() == 0){
            errorMessage += "No valid first name\n";
        }
        if(lastNameTextField.getText() == null || lastNameTextField.getText().length() == 0){
            errorMessage += "No valid last name\n";
        }
        if(errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    public void setStudentInfoSystemApp(GradeBookApp app){
        this.app = app;
    }

    public  void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setSchoolYearOverviewCourseList(ListView<CourseInfo> schoolYearOverviewCourseList) {
        this.schoolYearOverviewCourseList = schoolYearOverviewCourseList;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
