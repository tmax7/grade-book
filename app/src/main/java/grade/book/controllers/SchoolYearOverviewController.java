package grade.book.controllers;

import grade.book.CourseInfo;
import grade.book.GradeBookApp;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SchoolYearOverviewController {
    GradeBookApp app;
    @FXML
    private TitledPane fallSemesterTitledPane;
    @FXML
    private ListView<CourseInfo> fallSemesterClasses;
    @FXML
    private TitledPane springSemesterTitledPane;
    @FXML
    private ListView<CourseInfo> springSemesterClasses;
    @FXML
    private AnchorPane rightAnchorPane;

    private AnchorPane courseInfoShown = null;

    public SchoolYearOverviewController() {
    }

    /*
        Initializes the controller class. 
        This method is automatically called after the fxml file has been loaded.
     */
    @FXML
    private void initialize(){
        fallSemesterTitledPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                fallSemesterClasses.getSelectionModel().clearSelection();
            }
        });
        springSemesterTitledPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                springSemesterClasses.getSelectionModel().clearSelection();
            }
        });

        fallSemesterClasses.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showCourseDetails(newValue, "Fall")
        );
        springSemesterClasses.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showCourseDetails(newValue, "Spring")
        );
    }

    private void showCourseDetails(CourseInfo courseInfo, String semester){
        if(courseInfo != null) {
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(GradeBookApp.class.getResource("/CourseOverview.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                // Stores the page in courseInfoShown so can it can be referenced and removed if courseInfo is deleted


                // Gets the CourseOverviewController, links it with the app, and communicates which semester is selected
                CourseOverviewController controller = loader.getController();
                controller.setStudentInfoSystemApp(this.app);
                controller.setCourseInfo(courseInfo);
                controller.setSemester(semester);

                // Gets classTableView from controller and sets it with the assignments from the selected course
                controller.getImportedAssignmentsTableView().setItems(courseInfo.getGradedItems());


                // Gets classTableView from controller and sets it with the students from the selected course
                controller.getImportedStudentTableView().setItems(courseInfo.getStudents());


                // Makes the page fit to its parent rightAnchorPane
                page.prefHeightProperty().bind(rightAnchorPane.heightProperty());
                page.prefWidthProperty().bind(rightAnchorPane.widthProperty());

                // Removes the old displayed page and adds the new page to rightAnchorPane
                if(this.courseInfoShown != null) {
                    rightAnchorPane.getChildren().remove(this.courseInfoShown);
                }
                rightAnchorPane.getChildren().add(page);
                this.courseInfoShown = page;

            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    /* 
        Fall Courses handlers
    */
    @FXML
    private void handleNewFallCourse(){
        if(app.getSchoolYearInfo().getSchoolYear().equals("none")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("");
            alert.setContentText("You must open a file or create a new file to add a new course");
            alert.showAndWait();
        } else{
            app.showNewCourseDialog(fallSemesterClasses, "Fall");

        }

    }
    @FXML
    private void handleEditFallCourse(){
        int indexOfSelectedFallCourse = fallSemesterClasses.getSelectionModel().getSelectedIndex();
        if(indexOfSelectedFallCourse > -1){
            // Gets the selected fall course using index
            CourseInfo selectedFallCourse = this.app.getSchoolYearInfo().getListOfFallCourses().get(indexOfSelectedFallCourse);
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(GradeBookApp.class.getResource("/EditCourseDialog.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                Stage stage = new Stage();
                stage.setTitle("Edit Course");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(app.getPrimaryStage());
                Scene scene = new Scene(page);
                stage.setScene(scene);

                EditCourseDialogController controller = loader.getController();
                controller.setApp(this.app);
                controller.setCourseInfo(selectedFallCourse);
                controller.setStage(stage);

                stage.showAndWait();
                // Clears the selection then reselects it in order to refresh SchoolYearOverview with the new edited information
                fallSemesterClasses.getSelectionModel().clearSelection();
                fallSemesterClasses.getSelectionModel().select(indexOfSelectedFallCourse);

            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void handleDeleteFallCourse(){
        int indexOfSelectedFallCourse = fallSemesterClasses.getSelectionModel().getSelectedIndex();
        if(indexOfSelectedFallCourse > -1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(app.getPrimaryStage());
            alert.setContentText("Deletion is permanent: are you sure you wish to proceed?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    this.app.getSchoolYearInfo().getListOfFallCourses().remove(indexOfSelectedFallCourse);
                    rightAnchorPane.getChildren().remove(this.courseInfoShown);
                    fallSemesterClasses.getSelectionModel().clearSelection();
                }
            });
        }
    }

    //Spring Courses handlers
    @FXML
    private void handleNewSpringCourse(){
        if(app.getSchoolYearInfo().getSchoolYear().equals("none")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("");
            alert.setContentText("You must open a file or create a new file to add a new course");
            alert.showAndWait();
        } else{
            app.showNewCourseDialog(springSemesterClasses, "Spring");
        }
    }
    @FXML
    private void handleEditSpringCourse(){
        int indexOfSelectedSpringCourse = springSemesterClasses.getSelectionModel().getSelectedIndex();
        if(indexOfSelectedSpringCourse > -1){
            // Gets teh selected spring course using index
            CourseInfo selectedSpringCourse = this.app.getSchoolYearInfo().getListOfSpringCourses().get(indexOfSelectedSpringCourse);
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(GradeBookApp.class.getResource("/EditCourseDialog.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                Stage stage = new Stage();
                stage.setTitle("Edit Course");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(app.getPrimaryStage());
                Scene scene = new Scene(page);
                stage.setScene(scene);

                EditCourseDialogController controller = loader.getController();
                controller.setApp(this.app);
                controller.setCourseInfo(selectedSpringCourse);
                controller.setStage(stage);

                stage.showAndWait();

                // Clears the selection and then reselect it in order to refresh SchoolYearOverview with the new edited information
                springSemesterClasses.getSelectionModel().clearSelection();
                springSemesterClasses.getSelectionModel().select(indexOfSelectedSpringCourse);

            } catch(IOException e) {
                e.printStackTrace();
            }

        }
    }
    @FXML
    private void handleDeleteSpringCourse() {
        int indexOfSelectedSpringCourse = springSemesterClasses.getSelectionModel().getSelectedIndex();
        if (indexOfSelectedSpringCourse > -1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(app.getPrimaryStage());
            alert.setContentText("Deletion is permanent: are you sure you wish to proceed?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    this.app.getSchoolYearInfo().getListOfSpringCourses().remove(indexOfSelectedSpringCourse);
                    rightAnchorPane.getChildren().remove(this.courseInfoShown);
                    springSemesterClasses.getSelectionModel().clearSelection();
                }
            });
        }
    }

    @FXML
    public void updateTitledPaneTexts() {
        String fallText = "Fall Semester " + app.getSchoolYearInfo().getSchoolYear();
        fallSemesterTitledPane.setText(fallText);

        String springText = "Spring Semester " + app.getSchoolYearInfo().getSchoolYear();
        springSemesterTitledPane.setText(springText);
    }

    public void setStudentInfoSystemApp(GradeBookApp app){
        this.app = app;
        ObservableList<CourseInfo> fallSemesterClasses = this.app.getSchoolYearInfo().getListOfFallCourses();
        ObservableList<CourseInfo> springSemesterClasses = this.app.getSchoolYearInfo().getListOfSpringCourses();
        this.fallSemesterClasses.setItems(fallSemesterClasses);
        this.springSemesterClasses.setItems(springSemesterClasses);
    }

}
