package grade.book.controllers;

import grade.book.GradeBookApp;
import grade.book.SchoolYearInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class RootLayoutController {

    GradeBookApp app;

    public RootLayoutController(){
    }

    public void setStudentInfoSystemApp(GradeBookApp app){
        this.app = app;
    }

    @FXML
    private void handleNewSchoolYearFile() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(app.getPrimaryStage());
        alert.setContentText("Creating new file will delete any unsaved changes. Are you sure you wish to proceed?");

        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK) {
                app.clearSchoolYearInfo();
                app.setSchoolYearFilePath(null);

                //shows NewSchoolYearDialog
                app.showNewSchoolYearDialog();
            }
        });


    }

    @FXML
    private void handleOpenSchoolYearFile(){
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(app.getPrimaryStage());

        if (file != null) {
            app.loadSchoolYearFromFile(file);
        }
    }

    @FXML
    private void handleSave_SchoolYearFile() {
        File schoolYearFile = app.getSchoolYearFile();
        if (schoolYearFile != null) {
            app.saveSchoolYearToFile(schoolYearFile);
        } else {
            handleSaveAs_SchoolYearFile();
        }
    }

    @FXML
    private void handleSaveAs_SchoolYearFile() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter and
        // Set initial name for file and
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        String schoolYearFileName = app.getSchoolYearInfo().getSchoolYear() + "_school_year";
        fileChooser.setInitialFileName(schoolYearFileName);

        // Show save file dialog and create file from it's result
        File file = fileChooser.showSaveDialog(app.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            app.saveSchoolYearToFile(file);
        }

    }

    @FXML
    private void handleExit(){
        System.exit(0);
    }

    @FXML
    private void handleEditFile(){
        SchoolYearInfo schoolYearInfo = this.app.getSchoolYearInfo();
        if(schoolYearInfo != null
                && schoolYearInfo.getSchoolYear() != null
                &&  !(schoolYearInfo.getSchoolYear().equals("none"))){
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(GradeBookApp.class.getResource("/EditSchoolYearDialog.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                Stage stage = new Stage();
                stage.setTitle("Add New Course");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(app.getPrimaryStage());
                Scene scene = new Scene(page);
                stage.setScene(scene);

                EditSchoolYearDialogController controller = loader.getController();
                controller.setStudentInfoSystemApp(this.app);
                controller.setSchoolYearInfo(schoolYearInfo);
                controller.setStage(stage);

                stage.showAndWait();
            } catch(IOException e){
                e.printStackTrace();
            }


        }
    }

    @FXML
    private void handleHelp(){

    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(app.getPrimaryStage());
        alert.setTitle("StudentInfoSystemApp");
        alert.setHeaderText("About");
        alert.setContentText("Author: Taylor Maxfield");

        alert.showAndWait();
    }




}
