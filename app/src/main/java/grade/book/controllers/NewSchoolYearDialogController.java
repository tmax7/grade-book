package grade.book.controllers;

import grade.book.GradeBookApp;
import grade.book.SchoolYear;
import grade.book.SchoolYearInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewSchoolYearDialogController {
    @FXML
    private TextField startYearTextField;
    @FXML
    private TextField endYearTextField;

    GradeBookApp app;
    private Stage stage;

    @FXML
    private void initialize() {
    }

    public void setApp(GradeBookApp app) {
        this.app = app;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleOK() {
        if(isInputValid()){
            int startYearAsInt = Integer.parseInt(startYearTextField.getText());
            int endYearAsInt = Integer.parseInt(endYearTextField.getText());
            SchoolYear schoolYear = new SchoolYear(startYearAsInt, endYearAsInt);

            app.setSchoolYearInfo(new SchoolYearInfo(schoolYear));

            app.showSchoolYearOverview();
            app.updateSchoolYearTitles();

            stage.close();

        }
    }

    @FXML
    private void handleCancel() {
        stage.close();
    }

    private boolean isInputValid() {
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
}
