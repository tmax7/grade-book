package grade.book;

import grade.book.controllers.NewCourseDialogController;
import grade.book.controllers.NewSchoolYearDialogController;
import grade.book.controllers.RootLayoutController;
import grade.book.controllers.SchoolYearOverviewController;
import grade.book.wrappers.SchoolYearInfoWrapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

public class GradeBookApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private String schoolYearFilePath;
    private SchoolYearInfo schoolYearInfo = new SchoolYearInfo();

    private SchoolYearOverviewController schoolYearOverviewController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("StudentInformationSystemApp");

        initRootLayout();

    }

    public void initRootLayout(){
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GradeBookApp.class.getResource("/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setStudentInfoSystemApp(this);

            //Make window viable
            primaryStage.show();


// TODO see if commenting these out causes any problems
//            showSchoolYearOverview();
//            updateSchoolYearTitles();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSchoolYearOverview(){
        try {
            // Load student overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GradeBookApp.class.getResource("/SchoolYearOverview.fxml"));
            AnchorPane schoolYearOverview = (AnchorPane) loader.load();

            //Give the controller access to the Student Info System app
            this.schoolYearOverviewController = loader.getController();
            this.schoolYearOverviewController.setStudentInfoSystemApp(this);

            //Set schoolYearOverview into the center of the root layout
            rootLayout.setCenter(schoolYearOverview);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showNewSchoolYearDialog(){
        try{
            //TODO returning true or false does not seem to be necessary because this isn't used as a input to a function or variable
            // also figure out how to create new file
            //loads FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GradeBookApp.class.getResource("/NewSchoolYearDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // creates stage and sets it with scene
            Stage stage = new Stage();
            stage.setTitle("Add New School Year");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            stage.setScene(scene);

            // creates controller and links it to this app and stage
            NewSchoolYearDialogController controller = loader.getController();
            controller.setStudentInfoSystemApp(this);
            controller.setStage(stage);

            // shows the stage
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showNewCourseDialog(ListView<CourseInfo> courseList, String semester) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GradeBookApp.class.getResource("/NewCourseDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add New Course");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            stage.setScene(scene);

            NewCourseDialogController controller = loader.getController();
            controller.setStudentInfoSystemApp(this);
            controller.setStage(stage);
            controller.setSchoolYearOverviewCourseList(courseList);
            controller.setSemester(semester);

            stage.showAndWait();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    ///TODO is XML correct solution for this application?
    public void loadSchoolYearFromFile(File file) {

        try {
            JAXBContext context = JAXBContext.newInstance(SchoolYearInfoWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            //Reads XML from the file and unmarshals it.
            SchoolYearInfoWrapper schoolYearInfoWrapper = (SchoolYearInfoWrapper) um.unmarshal(file);

//            //Test
//            System.out.println(schoolYearInfoWrapper.getSchoolYear());
//            System.out.println(schoolYearInfoWrapper.getListOfFallCourses());
//            System.out.println(schoolYearInfoWrapper.getListOfSpringCourses());

            //clear schoolYearInfo and then set it with info from importedSchoolYearInfo gotten from unwrapped SchoolYearInfoWrapper
            this.clearSchoolYearInfo();
            SchoolYearInfo importedSchoolYearInfo = unwrapSchoolYearInfoWrapper(schoolYearInfoWrapper);

//            //Test
//            System.out.println((importedSchoolYearInfo.getSchoolYear()));
//            System.out.println(importedSchoolYearInfo.getListOfFallCourses());
//            System.out.println(importedSchoolYearInfo.getListOfSpringCourses());

            this.schoolYearInfo = importedSchoolYearInfo;

            //reload schoolYearOverview with new schoolYearInfo and update the TitlePanes
            showSchoolYearOverview();
            updateSchoolYearTitles();

            //set filePath to opened file's path
            setSchoolYearFilePath(file);

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public void saveSchoolYearToFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(SchoolYearInfoWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            SchoolYearInfoWrapper wrapper = new SchoolYearInfoWrapper(this.schoolYearInfo);
            m.marshal(wrapper, file);

            // set FilePath to the saved file's path
            setSchoolYearFilePath(file);
        } catch (Exception e) { // catches ANY exception
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }


    public File getSchoolYearFile() {
        if (schoolYearFilePath != null) {
            return new File(schoolYearFilePath);
        } else {
            return null;
        }
    }

    public void setSchoolYearFilePath(File file) {
        if (file != null) {
            schoolYearFilePath = file.getPath();
            primaryStage.setTitle("StudentInfoSystemApp:  " + file.getName());
        } else {
            schoolYearFilePath = null;
            primaryStage.setTitle("StudentInfoSystemApp");
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public SchoolYearInfo getSchoolYearInfo() {
        return schoolYearInfo;
    }

    public void setSchoolYearInfo(SchoolYearInfo schoolYearInfo) {
        this.schoolYearInfo = schoolYearInfo;
    }

    public void clearSchoolYearInfo(){
        schoolYearInfo.clearAll();
    }

    public void updateSchoolYearTitles(){
        this.schoolYearOverviewController.updateTitledPaneTexts();
    }

    public SchoolYearInfo unwrapSchoolYearInfoWrapper(SchoolYearInfoWrapper schoolYearInfoWrapper){
       return new SchoolYearInfo(schoolYearInfoWrapper);
    }

    //main function
    public static void main(String[] args) {
        launch(args);
    }
}
