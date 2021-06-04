package grade.book.controllers;

import grade.book.CourseInfo;
import grade.book.GradeBookApp;
import grade.book.GradedItem;
import grade.book.Student;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class AssignmentInfoOverviewController {

    private GradeBookApp app;
    private Stage stage;
    private CourseInfo courseInfo;
    private GradedItem gradedItem;

    @FXML
    private Label assignmentLabel;
    @FXML
    private Label courseNameLabel;
    @FXML
    private Label coursePeriodLabel;


    @FXML
    private TableView<Student> assignmentInfoTableView;
    @FXML
    private TableColumn<Student, String> studentIDColumn;
    @FXML
    private TableColumn<Student, String> firstNameColumn;
    @FXML
    private TableColumn<Student, String> lastNameColumn;
    @FXML
    private TableColumn<Student, String> scoreColumn;
    @FXML
    private TableColumn<Student, String> maxScoreColumn;
    @FXML
    private TableColumn<Student, String> percentColumn;

    public AssignmentInfoOverviewController(){
    }

    @FXML
    private void initialize(){
        assignmentInfoTableView.setEditable(true);
    }

    @FXML
    private void handleHelp(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(this.app.getPrimaryStage());
        alert.setHeaderText("Help");
        alert.setContentText("This window shows each student's score and percent for this particular assignment.\n\n" +
                             "To edit a students score just double click the score you wish to change, type in the new score, and press the enter key.");
        alert.showAndWait();
    }

    @FXML
    private void handleOK(){
        this.stage.close();
    }

    public void setApp(GradeBookApp app) {
        this.app = app;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
        // TODO old way ->assignmentInfoTableView.getItems().addAll(this.courseInfo.getStudents());
        assignmentInfoTableView.setItems(this.courseInfo.getStudents());
        courseNameLabel.setText(courseInfo.getCourseName());
        coursePeriodLabel.setText(courseInfo.getCoursePeriod());
    }

    /*
        Sets CellValueFactory here because we need to call calculateIndex()
        which cannot happen until after initialize() method is called;
    */
    public void setGradedItem(GradedItem gradedItem) {
        this.gradedItem = gradedItem;
        int index = calculateIndex();

        assignmentLabel.setText(gradedItem.getName());

        studentIDColumn.setCellValueFactory(cellData -> cellData.getValue().studentIdProperty());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        scoreColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        scoreColumn.setCellValueFactory(cellData -> cellData.getValue().getGradedItems().get(index).scoreProperty());

        maxScoreColumn.setCellValueFactory(cellData -> cellData.getValue().getGradedItems().get(index).maxScoreProperty());
        percentColumn.setCellValueFactory(cellData -> cellData.getValue().getGradedItems().get(index).percentGradeProperty());
    }

//    @FXML
//    private void handleEditStart(TableColumn.CellEditEvent<Student, String> studentStringCellEditEvent){
//        EventHandler<TableColumn.CellEditEvent<Student, String>> editCommitEventHandler = new EventHandler<TableColumn.CellEditEvent<Student, String>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<Student, String> studentStringCellEditEvent) {
//
//            }
//        }
//        studentStringCellEditEvent.getTableView().getEditingCell().getTableColumn().setOnEditCommit();
//    }

    @FXML
    private void handleEditCommit(TableColumn.CellEditEvent<Student,String> studentStringCellEditEvent) {
        /*
            TODO find out if can make index = calculateIndex(); member of this function instead
            of used in each method  to calculate index where graded item is in index
        */
        int index = calculateIndex();
        //Gets student who was edited
        int studentRowNumber = studentStringCellEditEvent.getTablePosition().getRow();
        Student student = studentStringCellEditEvent.getTableView().getItems().get(studentRowNumber);

        String oldScore = studentStringCellEditEvent.getOldValue();
        String newScore = studentStringCellEditEvent.getNewValue();
        try {
            // Sees if input is a Integer
            Integer.parseInt(newScore);
            // If no exception is thrown, sets the student's score to newScore and recalculates the student's percent and grade.
            student.getGradedItems().get(index).setScore(newScore);
            student.calculatePercentAndGrade();
        } catch(NumberFormatException nfe) {
            // Shows alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.app.getPrimaryStage());
            alert.setContentText("Score must be a positive whole number e.g. 47");
            alert.showAndWait();
            // Sets student's score to oldScore
            student.getGradedItems().get(index).setScore(oldScore);
            // Sets the Cell to the edit state
            TableColumn<Student,String> scoreColumn = studentStringCellEditEvent.getTablePosition().getTableColumn();
            studentStringCellEditEvent.getTableView().edit(studentRowNumber, scoreColumn);
        }
    }

    private int calculateIndex(){
        /* 
            Finds what the index within the courseInfo's gradedItemList
            is of the gradedItem that has been selected
            by comparing the selected gradedItem's ID to the ID's in the gradedItemList;
        */
        int index = -1;
        String gradedItemIndex = gradedItem.getID();
        ObservableList<GradedItem> gradedItems = courseInfo.getGradedItems();

        for(int i = 0; i < gradedItems.size(); i++){
                if(gradedItems.get(i).getID().equals(gradedItemIndex)){
                    index = i;
                    break;
                }
        }
        
        return index;
    }

}
