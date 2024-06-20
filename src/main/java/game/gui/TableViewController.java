package game.gui;

import game.result.GameResult;
import game.result.JsonGameResultManager;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class TableViewController {

    @FXML
    private TableView<GameResult> tableView;

    @FXML
    private TableColumn<GameResult, String> playerName;

    @FXML
    private TableColumn<GameResult, Integer> steps;

    @FXML
    private TableColumn<GameResult, String> created;

    @FXML
    private void initialize() throws IOException {
        playerName.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        steps.setCellValueFactory(new PropertyValueFactory<>("steps"));
        created.setCellValueFactory(
                cellData -> {
                    var dateTime = cellData.getValue().getCreated();
                    var formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
                    return new ReadOnlyStringWrapper(formatter.format(dateTime));
                }
        );
        ObservableList<GameResult> observableList = FXCollections.observableArrayList();
        observableList.addAll(new JsonGameResultManager(Path.of("results.json")).getBest(10));
        tableView.setItems(observableList);
    }

    @FXML
    private void switchScene(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/gui.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}