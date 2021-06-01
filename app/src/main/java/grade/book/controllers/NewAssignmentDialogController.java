package grade.book.controllers;

import grade.book.CourseInfo;
import grade.book.GradeBookApp;
import grade.book.GradedItem;
import grade.book.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewAssignmentDialogController {

    private GradeBookApp app;
    private CourseInfo courseInfo;
    private String semester;
    private Stage stage;


    @FXML
    private TextField nameTextField;
    @FXML
    private TextArea descriptionTextField;
    @FXML
    private TextField pointsTextField;


    public NewAssignmentDialogController(){
    }

    @FXML
    private void initialize(){
        descriptionTextField.setText("none");
    }

    @FXML
    private void handleOK(){
        if(isInputValid()) {
            String id = Integer.toString(this.courseInfo.getIndexOfGradedItems());
            String name = nameTextField.getText();
            String description = descriptionTextField.getText();
            String points = pointsTextField.getText();

            GradedItem assignment = new GradedItem(id, name, null, points, null, description );

            //add assignment to list of graded items in courseInfo and increment course info's gradedItemIndex
            this.courseInfo.getGradedItems().add(assignment);
            this.courseInfo.incrementGradedItemIndex();

            //create a copy for each student that can be independently edited for each of them
            for(Student student : courseInfo.getStudents()){
                    GradedItem studentCopyOfAssignment = new GradedItem(id, name, null, points, null, description );
                    //adds gradedItem and recalculates students grade
                    student.addGradedItem(studentCopyOfAssignment);

            }

            stage.close();

        }
    }

    @FXML
    private void handleCancel(){
        stage.close();
    }

    public void setStudentInfoSystemApp(GradeBookApp app){
        this.app = app;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private boolean isInputValid(){
        String errorMessage = "";

        if(nameTextField.getText() == null || nameTextField.getText().length() == 0){
            errorMessage += "No valid assignment name\n";
        }
        if(descriptionTextField.getText() == null || descriptionTextField.getText().length() == 0){
            errorMessage += "No valid description\n";
        }
        if(pointsTextField.getText() == null || pointsTextField.getText().length() == 0){
            errorMessage +="No valid points (must be in numeric form e.g. 40)";
        }
        if(errorMessage.length() == 0) {
            try {
                Integer.parseInt(pointsTextField.getText());
            } catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(stage);
                alert.setTitle("Invalid Fields");
                alert.setHeaderText("No valid points (must be in numeric form e.g. 40)");

                //TODO might have to do lambda on this to fiter out OK response
                alert.showAndWait();

                return false;
            }
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
