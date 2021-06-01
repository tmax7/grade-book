package grade.book.controllers;

import grade.book.GradedItem;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AssignmentsOverviewController {
    @FXML
    private TableView<GradedItem> assignmentsTableView;
    @FXML
    private TableColumn<GradedItem, String> iDColumn;
    @FXML
    private TableColumn<GradedItem, String> nameColumn;
    @FXML
    private TableColumn<GradedItem, String> descriptionColumn;
    @FXML
    private TableColumn<GradedItem, String> pointsColumn;

    public AssignmentsOverviewController(){
    }

    @FXML
    private void initialize() {
        iDColumn.setCellValueFactory(cellData -> cellData.getValue().IDProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        pointsColumn.setCellValueFactory(cellData -> cellData.getValue().maxScoreProperty());
    }

    public TableView<GradedItem> getAssignmentsTableView(){
        return assignmentsTableView;
    }
}
