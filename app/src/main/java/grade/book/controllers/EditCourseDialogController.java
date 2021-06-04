package grade.book.controllers;

import grade.book.CourseInfo;
import grade.book.GradeBookApp;
import grade.book.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditCourseDialogController {
    private GradeBookApp app;
    private CourseInfo courseInfo;
    private Stage stage;

    @FXML
    private TextField courseNameTextField;
    @FXML
    private TextField coursePeriodTextField;

    public EditCourseDialogController(){

    }

    @FXML
    private void intialize(){

    }

    @FXML
    private void handleOK(){
        if(isInputValid()){
            String newCourseName = this.courseNameTextField.getText();
            String newCoursePeriod = this.coursePeriodTextField.getText();

            //Sets courseInfo with new info
            this.courseInfo.setCourseName(newCourseName);
            this.courseInfo.setCoursePeriod(newCoursePeriod);

            // Updates each student's info
            for(Student student: this.courseInfo.getStudents()){
                student.setCourseName(newCourseName);
                student.setCoursePeriod(newCoursePeriod);
            }

            this.stage.close();
        }
    }

    @FXML
    private void handleCancel(){
        this.stage.close();
    }


    private boolean isInputValid(){
        String errorMessage = "";

        if(courseNameTextField.getText() == null || courseNameTextField.getText().length() == 0){
            errorMessage += "No valid course name\n";
        }
        if(coursePeriodTextField.getText() == null || coursePeriodTextField.getText().length() == 0){
            errorMessage += "No valid course period\n";
        }
        if(errorMessage.length() == 0){
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

    public void setApp(GradeBookApp app) {
        this.app = app;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
        courseNameTextField.setText(this.courseInfo.getCourseName());
        coursePeriodTextField.setText(this.courseInfo.getCoursePeriod());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
