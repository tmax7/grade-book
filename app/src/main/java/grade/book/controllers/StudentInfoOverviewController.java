package grade.book.controllers;

import grade.book.GradeBookApp;
import grade.book.GradedItem;
import grade.book.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class StudentInfoOverviewController {
    private GradeBookApp app;
    private Stage stage;

    @FXML
    public Label studentIdLabel;
    @FXML
    public Label firstNameLabel;
    @FXML
    public Label lastNameLabel;
    @FXML
    public Label percentLabel;
    @FXML
    public Label gradeLabel;

    @FXML
    public TableView<GradedItem> gradedItemTableView;

    @FXML
    public TableColumn<GradedItem, String> iDColumn;
    @FXML
    public TableColumn<GradedItem, String> nameColumn;
    @FXML
    public TableColumn<GradedItem, String> scoreColumn;
    @FXML
    public TableColumn<GradedItem, String> outOfColumn;
    @FXML
    public TableColumn<GradedItem, String> percentColumn;
    @FXML
    public TableColumn<GradedItem, String> noteColumn;
    @FXML
    public TableColumn<GradedItem, String> descriptionColumn;


    public Student student;
    public ObservableList<GradedItem> gradedItemObservableList = FXCollections.observableArrayList();


    public StudentInfoOverviewController(){

    }

    @FXML
    public void initialize() {
        iDColumn.setCellValueFactory(cellData -> cellData.getValue().IDProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        scoreColumn.setCellValueFactory(cellData -> cellData.getValue().scoreProperty());
        outOfColumn.setCellValueFactory(cellData -> cellData.getValue().maxScoreProperty());
        percentColumn.setCellValueFactory(cellData -> cellData.getValue().percentGradeProperty());
        noteColumn.setCellValueFactory(cellData -> cellData.getValue().noteProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
    }

    @FXML
    public Student getStudent() {
        return student;
    }

    @FXML
    public void setStudent(Student student) {
        this.student = student;

        // make the ObservableList of the table the students gradedItemObservableList
        this.gradedItemObservableList = student.getGradedItems();

//     TODO this was old way ->   this.gradedItemTableView.getItems().addAll(student.getGradedItems()); see if it still works:
        this.gradedItemTableView.setItems(student.getGradedItems());
        //set text of labels with info from student
        if(student.getStudentId() != null) { this.studentIdLabel.setText(student.getStudentId()); }
        if(student.getFirstName() != null) { this.firstNameLabel.setText(student.getFirstName()); }
        if(student.getLastName() != null) { this.lastNameLabel.setText(student.getLastName()); }
        if(student.getPercent() != null) { this.percentLabel.setText(student.getPercent());}
        if(student.getGrade() != null) { this.gradeLabel.setText(student.getGrade()); }
    }

    @FXML
    private void handleHelp(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(this.app.getPrimaryStage());
        alert.setHeaderText("Help");
        alert.setContentText("This window shows all of this student's information and assignments\n\n" +
                             "To edit a student's score on an assignment click on an assignment so it is highlighted then click the \"Edit Score\" button");
        alert.showAndWait();
    }
    @FXML
    private void handleEditScore(){
        //get selected gradedItem from TableView
        GradedItem gradedItemToEdit = this.gradedItemTableView.getSelectionModel().getSelectedItem();
        //create textInputDialog
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("New Score");
        inputDialog.setHeaderText("");
        inputDialog.setContentText("Enter New Score");

        //show inputDialog and get text user inputted as result
        inputDialog.showAndWait();
        String result = inputDialog.getResult();
        System.out.println(result);
        //if result is int then sets score to result
        //if result is null or empty string (i.e. user clicked cancel, or clicked ok with no input) then do nothing
        //if result is string that is not int then shows alert
        try{
            Integer.parseInt(result);
            gradedItemToEdit.setScore(result);
            student.calculatePercentAndGrade();
        } catch(NumberFormatException nfe){
            if(result != null && !(result.equals(""))){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(this.app.getPrimaryStage());
                alert.setContentText("Score must be a positive whole number e.g. 47");
                alert.showAndWait();
            }
        }
    }
    @FXML
    private void handleOK(){
        this.stage.close();
    }

    //setters
    public void setStudentInfoSystemApp(GradeBookApp app){
        this.app = app;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

