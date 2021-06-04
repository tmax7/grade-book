package grade.book.controllers;

import grade.book.GradeBookApp;
import grade.book.SchoolYear;
import grade.book.SchoolYearInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class EditSchoolYearDialogController {
    GradeBookApp app;
    SchoolYearInfo schoolYearInfo;
    Stage stage;

    @FXML
    private TextField startYearTextField;
    @FXML
    private TextField endYearTextField;

    public EditSchoolYearDialogController(){

    }

    @FXML
    private void handleOK(){
        if(isInputValid()){
            int startYear = Integer.parseInt(startYearTextField.getText());
            int endYear = Integer.parseInt(endYearTextField.getText());
            SchoolYear schoolYear = new SchoolYear(startYear, endYear);

            this.schoolYearInfo.setSchoolYear(schoolYear.getString());
            this.app.updateSchoolYearTitles();

            stage.close();
        }
    }

    @FXML
    private void handleCancel(){
        stage.close();
    }

    private boolean isInputValid(){
        String errorMessage = "";

        if(startYearTextField.getText() == null || startYearTextField.getText().length() == 0){
            errorMessage += "No valid start year\n";
        }
        if(endYearTextField.getText() == null || endYearTextField.getText().length() == 0){
            errorMessage += "No valid end year\n";
        }

        try {
            Integer.parseInt(startYearTextField.getText());
            Integer.parseInt(endYearTextField.getText());
        } catch(NumberFormatException e) {
            errorMessage += "Year must be entered as a number e.g. 2005";
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

    public void setStudentInfoSystemApp(GradeBookApp app) {
        this.app = app;
    }

    public void setSchoolYearInfo(SchoolYearInfo schoolYearInfo) {
        this.schoolYearInfo = schoolYearInfo;
        String schoolYear = schoolYearInfo.getSchoolYear();
        int indexOfDash = schoolYear.indexOf("-");
        String startYear = schoolYear.substring(0, indexOfDash - 1);
        String endYear = schoolYear.substring(indexOfDash + 2);
        startYearTextField.setText(startYear);
        endYearTextField.setText(endYear);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
