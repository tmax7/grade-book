package grade.book.controllers;

import grade.book.CourseInfo;
import grade.book.GradedItem;
import grade.book.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditAssignmentDialogController {

    private CourseInfo courseInfo;
    private Stage stage;
    private GradedItem assignment;


    @FXML
    private TextField nameTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField pointsTextField;

    public EditAssignmentDialogController() {}

    @FXML
    private void initialize() {

    }

    @FXML
    private void handleOK() {
        if(isInputValid()){
            String newName = nameTextField.getText();
            String newDescription = descriptionTextField.getText();
            String newMaxScore = pointsTextField.getText();
            assignment.setName(newName);
            assignment.setDescription(newDescription);
            assignment.setMaxScore(newMaxScore);

            //apply changes to each student's version of gradedItem then recalculate that student's percent and grade
            String idOfEditedGradedItem = this.assignment.getID();
            for(Student student : courseInfo.getStudents()){
               for(GradedItem gradedItem : student.getGradedItems()){
                   if(gradedItem.getID().equals(idOfEditedGradedItem)){
                       gradedItem.setName(newName);
                       gradedItem.setDescription(newDescription);
                       gradedItem.setMaxScore(newMaxScore);
                   }
                   student.calculatePercentAndGrade();
               }

            }

            stage.close();
        }
    }

    @FXML
    private void handleCancel() {
        stage.close();
    }

    public boolean isInputValid() {
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

    public void setCourseInfo(CourseInfo courseInfo){
        this.courseInfo = courseInfo;

    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setAssignment(GradedItem assignment){
        this.assignment = assignment;
        nameTextField.setText(assignment.getName());
        descriptionTextField.setText(assignment.getDescription());
        pointsTextField.setText(assignment.getMaxScore());
    }

}
