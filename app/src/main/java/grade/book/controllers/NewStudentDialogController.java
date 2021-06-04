package grade.book.controllers;

import grade.book.CourseInfo;
import grade.book.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewStudentDialogController {
    CourseInfo courseInfo;
    Stage stage;

    @FXML
    private TextField studentIdTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;

    public NewStudentDialogController(){

    }

    @FXML
    private void initialize(){

    }

    @FXML
    private void handleOK(){
        if(isInputValid()){
            String courseName = this.courseInfo.getCourseName();
            String coursePeriod = this.courseInfo.getCoursePeriod();
            String studentId = studentIdTextField.getText();
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();


            // Creates a student from the text field input, information from courseInfo, and a clone of gradedItems
            Student student = new Student(courseName, coursePeriod, studentId, firstName, lastName, null);

            // Adds the student to courseInfo and makes a copy of courseInfo's gradedItem list and gives it to the student
            this.courseInfo.addStudent(student);
            // Closes the window
            this.stage.close();
        }
    }

    @FXML
    private void handleCancel(){
        this.stage.close();
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean isInputValid(){
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
}
