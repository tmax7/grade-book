package grade.book.controllers;

import grade.book.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class EditStudentDialogController {
    private Stage stage;
    private Student student;

    @FXML
    private TextField studentIdTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private  TextField lastNameTextField;

    public EditStudentDialogController(){

    }

    @FXML
    private void initialize(){

    }

    @FXML
    private void handleOK() {
        if(isInputValid()) {
            student.setStudentId(this.studentIdTextField.getText());
            student.setFirstName(this.firstNameTextField.getText());
            student.setLastName(this.lastNameTextField.getText());
            stage.close();
        }
    }

    @FXML
    private void handleCancel(){
       stage.close();
    }

    public boolean isInputValid() {
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

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    // Sets the student and sets the initial text of TextFields to the value of the student's attributes
    public void setStudent(Student student) {
        this.student = student;
        this.studentIdTextField.setText(student.getStudentId());
        this.firstNameTextField.setText(student.getFirstName());
        this.lastNameTextField.setText(student.getLastName());
    }
}
