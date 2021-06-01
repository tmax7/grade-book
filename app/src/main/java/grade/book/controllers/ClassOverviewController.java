package grade.book.controllers;

import grade.book.GradeBookApp;
import grade.book.Student;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ClassOverviewController {

    @FXML
    private TableView<Student> studentTableView;

    @FXML
    private TableColumn<Student, String> courseNameColumn;
    @FXML
    private TableColumn<Student, String> coursePeriodColumn;
    @FXML
    private TableColumn<Student, String> studentIdColumn;
    @FXML
    private TableColumn<Student, String> firstNameColumn;
    @FXML
    private TableColumn<Student, String> lastNameColumn;
    @FXML
    private TableColumn<Student, String> percentColumn;
    @FXML
    private TableColumn<Student, String> gradeColumn;

    //TODO add functionality to add notes attribute by adding it to Student class
    //@FXML
    //private TableColumn<Student, String> notesColumn;

    private GradeBookApp app;

    public ClassOverviewController(){
    }

    @FXML
    private void initialize(){
        courseNameColumn.setCellValueFactory(cellData -> cellData.getValue().courseNameProperty());
        coursePeriodColumn.setCellValueFactory(cellData -> cellData.getValue().coursePeriodProperty());
        studentIdColumn.setCellValueFactory(cellData -> cellData.getValue().studentIdProperty());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        percentColumn.setCellValueFactory(cellData -> cellData.getValue().percentProperty());
        gradeColumn.setCellValueFactory(cellData -> cellData.getValue().gradeProperty());
        //notesColumn.setCellValueFactory(cellData -> cellData.getValue().notesProperty());
    }

    public void setStudentInfoSystemApp(GradeBookApp app){
        this.app = app;
    }

    public TableView<Student> getStudentTableView(){
        return studentTableView;
    }
}
