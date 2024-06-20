package game.gui;

import game.result.GameResult;
import game.result.GameResultManager;
import game.result.JsonGameResultManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import game.state.Board;
import game.state.Direction;
import game.state.Position;
import game.state.Square;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.tinylog.Logger;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.*;

public class GameController implements Initializable {
    @FXML
    private GridPane boardGrid;
    private Board board;
    private String playerName;
    @FXML
    private Text welcomeText;

    private final GameResultManager gameResult = new JsonGameResultManager(Path.of("results.json"));

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializePlayer();
        Logger.debug("Setting up the board");
        setupBoard();
        updateBoardView();
        addKeyListeners();
        boardGrid.requestFocus();
    }

    private void initializePlayer() {
        TextField playerNameField = new TextField();
        playerNameField.setPromptText("Enter your name");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Enter Name");
        alert.setHeaderText("Please enter your name:");
        alert.getDialogPane().setContent(playerNameField);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            playerName = playerNameField.getText().trim();
            if (playerName.isEmpty()) {
                playerName = "Anonymous";
            }
            Logger.debug("Player name entered: {}" , playerName);
        } else {
            playerName = "Anonymous";
            Logger.debug("No player name entered. Using default: " + playerName);
        }
        welcomeText.setText("Good luck, " + playerName + " !");
    }

    private void setupBoard() {
        board = new Board(new Position(1, 4));
        int size = board.getSize();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Square square = board.getSquare(new Position(row, col));
                StackPane stackPane = createStackPane(square);
                boardGrid.add(stackPane, col, row);
            }
        }
    }

    private StackPane createStackPane(Square square) {
        StackPane stackPane = new StackPane();
        if (square.hasWall(Direction.UP)) {
            stackPane.getStyleClass().add("top-wall");
        }
        if (square.hasWall(Direction.RIGHT)) {
            stackPane.getStyleClass().add("right-wall");
        }
        if (square.hasWall(Direction.DOWN)) {
            stackPane.getStyleClass().add("bottom-wall");
        }
        if (square.hasWall(Direction.LEFT)) {
            stackPane.getStyleClass().add("left-wall");
        }
        if (square.hasWall(Direction.DOWN) && square.hasWall(Direction.UP)) {
            stackPane.getStyleClass().add("top-bottom-wall");
        }
        if (square.hasWall(Direction.LEFT) && square.hasWall(Direction.DOWN)) {
            stackPane.getStyleClass().add("left-bottom-wall");
        }
        if (square.hasWall(Direction.UP) && square.hasWall(Direction.RIGHT)) {
            stackPane.getStyleClass().add("top-right-wall");
        }
        if (square.hasWall(Direction.RIGHT) && square.hasWall(Direction.LEFT)) {
            stackPane.getStyleClass().add("right-left-wall");
        }
        if (square.hasWall(Direction.LEFT) && square.hasWall(Direction.UP)) {
            stackPane.getStyleClass().add("top-left-wall");
        }
        if (square.hasWall(Direction.RIGHT) && square.hasWall(Direction.DOWN)) {
            stackPane.getStyleClass().add("right-bottom-wall");
        }
        if (square.hasWall(Direction.LEFT) && square.hasWall(Direction.DOWN) && square.hasWall(Direction.RIGHT)) {
            stackPane.getStyleClass().add("left-bottom-right-wall");
        }
        if (square.hasWall(Direction.LEFT) && square.hasWall(Direction.UP) && square.hasWall(Direction.RIGHT)) {
            stackPane.getStyleClass().add("left-top-right-wall");
        }
        if (square.hasWall(Direction.UP) && square.hasWall(Direction.RIGHT) && square.hasWall(Direction.DOWN)) {
            stackPane.getStyleClass().add("top-right-bottom-wall");
        }
        if (square.hasWall(Direction.UP) && square.hasWall(Direction.LEFT) && square.hasWall(Direction.DOWN)) {
            stackPane.getStyleClass().add("top-left-bottom-wall");
        }
        return stackPane;
    }

    private void updateBoardView() {
        int size = board.getSize();
        Position goalPosition = board.getGoal();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                StackPane stackPane = (StackPane) boardGrid.getChildren().get(row * size + col);
                stackPane.getChildren().clear();
                Position position = new Position(row, col);
                if (board.getBall().equals(position)) {
                    Circle ball = createBall();
                    stackPane.getChildren().add(ball);
                }
                if (goalPosition.equals(position)) {
                    Text goalText = createGoalText();
                    stackPane.getChildren().add(goalText);
                }
            }
        }
    }

    private Circle createBall() {
        Circle ball = new Circle(20);
        ball.getStyleClass().add("ball");
        return ball;
    }

    private Text createGoalText() {
        Text goalText = new Text("CÉL");
        goalText.getStyleClass().add("goal");
        return goalText;
    }

    private void addKeyListeners() {
        boardGrid.setFocusTraversable(true);
        boardGrid.setOnKeyPressed(event -> {
            KeyCombination restartKeyCombination = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
            KeyCombination quitKeyCombination = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN);
            if (restartKeyCombination.match(event)) {
                Logger.debug("Restarting game");
                restartGame();
            } else if (quitKeyCombination.match(event)) {
                Logger.debug("Exiting");
                Platform.exit();
            } else {
                KeyCode keyCode = event.getCode();
                Logger.debug(keyCode + " pressed");
                if (keyCode == KeyCode.UP) {
                    performMove(Direction.UP);
                } else if (keyCode == KeyCode.RIGHT) {
                    performMove(Direction.RIGHT);
                } else if (keyCode == KeyCode.DOWN) {
                    performMove(Direction.DOWN);
                } else if (keyCode == KeyCode.LEFT) {
                    performMove(Direction.LEFT);
                }
            }
        });
    }

    @FXML
    private void restartGame() {
        Logger.debug("Restarting game");
        initializePlayer();
        setupBoard();
        updateBoardView();
        addKeyListeners();
        boardGrid.requestFocus();
    }

    private void performMove(Direction direction) {
        if (board.canMove(direction)) {
            board.move(direction);
            Logger.debug("Move performed in direction: " + direction);
            updateBoardView();
        }
        handleGameOver();
    }

    private void handleGameOver() {
        if (board.isGameOver()) {
            showCongratulationsAlert();
            storeToLeaderboard();
        }
    }

    private void showCongratulationsAlert() {
        Logger.debug("Showing congratulations alert");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Congratulations");
        alert.setHeaderText("Congratulations, you solved the labyrinth!");
        ButtonType leaderboardButton = new ButtonType("Leaderboard");
        ButtonType closeButton = new ButtonType("Close");
        alert.getButtonTypes().setAll(leaderboardButton, closeButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == leaderboardButton) {
            Logger.debug("Leaderboard button clicked");
            storeToLeaderboard();
            switchToLeaderboardScene();
        } else {
            Logger.debug("Close button clicked");
            restartGame();
            Logger.debug("Restarting Game");
        }
    }

    private void switchToLeaderboardScene() {
        Logger.debug("Switching to leaderboard scene");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/table.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Stage currentStage = (Stage) boardGrid.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            Logger.error("Error while switching to leaderboard scene: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void storeToLeaderboard() {
        try {
            gameResult.add(addGameResult());
            Logger.info("Data stored to Leaderboard");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private GameResult addGameResult() {
        return GameResult.builder()
                .playerName(getPlayerName())
                .steps(board.getSteps())
                .created(ZonedDateTime.now())
                .build();
    }
}