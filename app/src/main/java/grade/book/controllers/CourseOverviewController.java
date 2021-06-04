package grade.book.controllers;

import grade.book.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CourseOverviewController {

    private GradeBookApp app;
    private CourseInfo courseInfo;
    private String semester;

    private AssignmentsOverviewController assignmentsOverviewController;

    @FXML
    private Tab studentsTab;

    @FXML
    private AnchorPane studentsTabTopAnchorPane;

    @FXML
    private AnchorPane assignmentsTabTopAnchorPane;

    @FXML
    private TableView<Student> importedStudentTableView;

    @FXML
    private TableView<GradedItem> importedAssignmentsTableView;


    public CourseOverviewController(){
    }

    @FXML
    private void initialize() {
        try{
            /* For class overview */
            FXMLLoader classOverviewLoader = new FXMLLoader();
            classOverviewLoader.setLocation(CourseOverviewController.class.getResource("/ClassOverview.fxml"));
            AnchorPane page = (AnchorPane) classOverviewLoader.load();

            // Makes the page fit to its parent topAnchorPaneOfStudentsTab
            page.prefHeightProperty().bind(studentsTabTopAnchorPane.heightProperty());
            page.prefWidthProperty().bind(studentsTabTopAnchorPane.widthProperty());

            studentsTabTopAnchorPane.getChildren().add(page);

            // Gets the student table from ClassOverviewController
            ClassOverviewController classOverviewController = classOverviewLoader.getController();
            importedStudentTableView = classOverviewController.getStudentTableView();

            /* For assignments overview */
            FXMLLoader assignmentsOverviewLoader = new FXMLLoader();
            assignmentsOverviewLoader.setLocation(CourseOverviewController.class.getResource("/AssignmentsOverview.fxml"));
            AnchorPane page2 = (AnchorPane) assignmentsOverviewLoader.load();

            // Gets the controller so that SchoolYearOverviewController can call linkGradedItemLists method  to get the assignment table
            AssignmentsOverviewController assignmentsOverviewController = assignmentsOverviewLoader.getController();
            this.assignmentsOverviewController = assignmentsOverviewController;
            importedAssignmentsTableView = assignmentsOverviewController.getAssignmentsTableView();

            page2.prefHeightProperty().bind(assignmentsTabTopAnchorPane.heightProperty());
            page2.prefWidthProperty().bind(assignmentsTabTopAnchorPane.widthProperty());

            assignmentsTabTopAnchorPane.getChildren().add(page2);


            /*  
                TODO make student info update when student tab is selected 
                    studentsTab.setOnSelectionChanged(new EventHandler<Event>() {
                        @Override
                        public void handle(Event event) {
                        }
                    });
            */
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    // The students tabPane button "event handlers" so to speak
    @FXML
    private void handleShowStudentInfo(){
        // Gets the index of the student selected in studentTableView
        int selectedIndex = this.importedStudentTableView.getSelectionModel().getSelectedIndex();
        // Makes sure a student is selected
        if( selectedIndex > -1){
            // Gets the student selected in studentTableView
            Student student = importedStudentTableView.getItems().get(selectedIndex);
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(CourseOverviewController.class.getResource("/StudentInfoOverview.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                /* 
                    Gives the controller access to the selected student and 
                    setStudent method will get everything else ready in StudentInfoOverview
                */
                StudentInfoOverviewController controller = loader.getController();
                controller.setStudent(student);


                // Creates the stage
                Stage stage = new Stage();
                stage.setTitle("Student Info");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(app.getPrimaryStage());
                Scene scene = new Scene(page);
                stage.setScene(scene);

                // Gives the controller access to stage and app
                controller.setStudentInfoSystemApp(this.app);
                controller.setStage(stage);

                stage.showAndWait();

            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleNewStudent(){

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CourseOverviewController.class.getResource("/NewStudentDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();


            Stage stage = new Stage();
            stage.setTitle("Add New Student");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(app.getPrimaryStage());
            Scene scene = new Scene(page);
            stage.setScene(scene);

            NewStudentDialogController controller = loader.getController();
            controller.setCourseInfo(this.courseInfo);
            controller.setStage(stage);

            stage.showAndWait();

        } catch(IOException e){
            e.printStackTrace();
        }

    }
    @FXML
    private void handleEditStudent(){
        int selectedIndex = this.importedStudentTableView.getSelectionModel().getSelectedIndex();

        if( selectedIndex > -1) {
            // Gets the student selected in studentTableView
            Student student = importedStudentTableView.getItems().get(selectedIndex);
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(CourseOverviewController.class.getResource("/EditStudentDialog.fxml"));
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
    private void handleDeleteStudent(){
        int selectedIndex = this.importedStudentTableView.getSelectionModel().getSelectedIndex();
        if(selectedIndex > -1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(app.getPrimaryStage());
            alert.setContentText("Deleting a student removes all their data. Are you sure you wish to proceed?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Gets the student selected in studentTableView
                    importedStudentTableView.getItems().remove(selectedIndex);
                }
            });

        }
    }

    /* Assignment TabPane */
    @FXML
    private void handleShowAssignmentInfo(){

        int selectedIndex = this.importedAssignmentsTableView.getSelectionModel().getSelectedIndex();

        if(selectedIndex > -1) {
            // Gets the assignment selected in assignmentsTableView
            GradedItem gradedItem = this.importedAssignmentsTableView.getItems().get(selectedIndex);

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(CourseOverviewController.class.getResource("/AssignmentInfoOverview.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                Stage stage = new Stage();
                stage.setTitle("Assignment Overview");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(app.getPrimaryStage());
                Scene scene = new Scene(page);
                stage.setScene(scene);

                AssignmentInfoOverviewController controller = loader.getController();
                controller.setStudentInfoSystemApp(this.app);
                controller.setCourseInfo(this.courseInfo);
                controller.setGradedItem(gradedItem);
                controller.setStage(stage);


                stage.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleNewAssignment(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CourseOverviewController.class.getResource("/NewAssignmentDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add New Assignment");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(app.getPrimaryStage());
            Scene scene = new Scene(page);
            stage.setScene(scene);

            /*
            Gives the controller access to:
                app,
                semester,
                list of gradedItems in courseInfo which is shared also with AssignmentOverviewController,
                and window.
            */
            NewAssignmentDialogController controller = loader.getController();
            controller.setStudentInfoSystemApp(this.app);
            controller.setCourseInfo(this.courseInfo);
            controller.setSemester(this.semester);
            controller.setStage(stage);

            // Shows the window and waits till an action performed
            stage.showAndWait();

        } catch(IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void handleEditAssignment(){
        int selectedIndex = this.importedAssignmentsTableView.getSelectionModel().getSelectedIndex();

        if(selectedIndex > -1){
            // Gets the assignment selected in assignmentsTableView
            GradedItem gradedItem = this.importedAssignmentsTableView.getItems().get(selectedIndex);
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(CourseOverviewController.class.getResource("/EditAssignmentDialog.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                Stage stage = new Stage();
                stage.setTitle("EditAssignmentDialog");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(app.getPrimaryStage());
                Scene scene = new Scene(page);
                stage.setScene(scene);

                EditAssignmentDialogController controller = loader.getController();
                controller.setCourseInfo(this.courseInfo);
                controller.setStage(stage);
                controller.setAssignment(gradedItem);

                stage.showAndWait();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleDeleteAssignment(){
        int selectedIndex = this.importedAssignmentsTableView.getSelectionModel().getSelectedIndex();
        if(selectedIndex > -1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(app.getPrimaryStage());
            alert.setContentText("Deleting an assignment removes all its data. Are you sure you wish to proceed?");
            alert.showAndWait().ifPresent(response -> {
                if(response == ButtonType.OK) {
                    // Gets the assignment selected in assignmentsTableView
                    GradedItem gradedItem = this.importedAssignmentsTableView.getItems().get(selectedIndex);

                    // Removes the GradedItem from each Student
                    String gradedItemID = gradedItem.getID();
                    for(Student student : courseInfo.getStudents()){
                        for(int i = 0; i < student.getGradedItems().size(); i++){
                            if(student.getGradedItems().get(i).getID().equals(gradedItemID)){
                                student.getGradedItems().remove(i);
                                student.calculatePercentAndGrade();
                                break;
                            }
                        }
                    }
                    // Removes the assignment from assignmentTableView
                    this.importedAssignmentsTableView.getItems().remove(selectedIndex);
                }
            });
        }
    }

    public GradeBookApp getStudentInfoSystemApp() {
        return app;
    }

    public void setStudentInfoSystemApp(GradeBookApp app) {
        this.app = app;
    }

    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public TableView<Student> getImportedStudentTableView() {
        return importedStudentTableView;
    }

    public void setImportedStudentTableView(TableView<Student> importedStudentTableView) {
        this.importedStudentTableView = importedStudentTableView;
    }

    public TableView<GradedItem> getImportedAssignmentsTableView() {
        return importedAssignmentsTableView;
    }

    public void setImportedAssignmentsTableView(TableView<GradedItem> importedAssignmentsTableView) {
        this.importedAssignmentsTableView = importedAssignmentsTableView;
    }

}

