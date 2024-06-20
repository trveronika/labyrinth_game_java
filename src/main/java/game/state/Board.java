package game.state;

import org.tinylog.Logger;

/**
 * Represents a labyrinth board consisting of squares with walls, a ball that moves and a goal.
 */
public class Board {
    private final int size = 7;
    private final Square[][] squares;
    private final Position ball;
    private final Position goal = new Position(5,2);
    private int steps = 0;

    /**
     * Creates a {@code Board} object with the given ball position.
     *
     * @param ballPosition the position of the ball in the labyrinth
     */
    public Board(Position ballPosition) {
        squares = new Square[size][size];
        initializeSquares();
        addOutsideBorders();
        addWalls();
        isValidPosition(ballPosition);
        this.ball = ballPosition;
        Logger.info("Board created");
    }

    private void initializeSquares(){
        for (var i = 0; i < size; i++) {
            for (var j = 0; j < size; j++) {
                squares[i][j] = new Square();
            }
        }
    }

    private void addOutsideBorders() {
        for (var i = 0; i < size; i++) {
            squares[0][i].setWall(Direction.UP);
            squares[size - 1][i].setWall(Direction.DOWN);
        }
        for (var i = 0; i < size; i++) {
            squares[i][0].setWall(Direction.LEFT);
            squares[i][size - 1].setWall(Direction.RIGHT);
        }
    }

    private void addWalls() {
        addWallToSquare(new Position(0, 0), Direction.RIGHT);
        addWallToSquare(new Position(0, 3), Direction.RIGHT);
        addWallToSquare(new Position(0, 2), Direction.DOWN);
        addWallToSquare(new Position(0, 6), Direction.DOWN);
        addWallToSquare(new Position(2, 1), Direction.DOWN);
        addWallToSquare(new Position(2, 2), Direction.RIGHT);
        addWallToSquare(new Position(2, 6), Direction.LEFT);
        addWallToSquare(new Position(3, 3), Direction.DOWN);
        addWallToSquare(new Position(3, 3), Direction.RIGHT);
        addWallToSquare(new Position(3, 4), Direction.RIGHT);
        addWallToSquare(new Position(4, 0), Direction.DOWN);
        addWallToSquare(new Position(4, 4), Direction.DOWN);
        addWallToSquare(new Position(4, 6), Direction.UP);
        addWallToSquare(new Position(5, 2), Direction.LEFT);
        addWallToSquare(new Position(5, 2), Direction.DOWN);
        addWallToSquare(new Position(5, 2), Direction.RIGHT);
        addWallToSquare(new Position(6, 3), Direction.RIGHT);
        addWallToSquare(new Position(6, 5), Direction.RIGHT);
    }

    private void addWallToSquare(Position position, Direction direction) {
        Square square = getSquare(position);
        square.setWall(direction);
        addWallToAdjacentSquare(position, direction);
    }

    private void addWallToAdjacentSquare(Position position, Direction direction){
        if (hasAdjacentSquare(position, direction)){
            Position neighbourPosition = position.getAdjacentPosition(direction);
            Square neighbourSquare = getSquare(neighbourPosition);
            Direction oppositeDirection = direction.oppositeDirection();
            neighbourSquare.setWall(oppositeDirection);
        }
    }

    private boolean hasAdjacentSquare(Position position, Direction direction){
        Position neighbour = position.getAdjacentPosition(direction);
        return isValidPosition(neighbour);
    }

    private boolean isValidPosition(Position position) {
        return position.row() >= 0 && position.row() < size && position.col() >= 0 && position.col() < size;
    }

    /**
     * Retrieves the size of the board.
     *
     * @return the size of the labyrinth
     */
    public int getSize() {
        return size;
    }

    /**
     * Retrieves the position of the ball.
     *
     *@return the position of the ball
     */
    public Position getBall() {
        return ball;
    }

    /**
     * Retrieves the position of the goal.
     *
     * @return the position of the goal
     */
    public Position getGoal() {
        return goal;
    }

    /**
     * Retrives the number of steps.
     *
     * @return the number of steps taken
     */
    public int getSteps() {
        return steps;
    }

    /**
     * Retrieves a square of a given position of the board.
     *
     * @param position the position of the square to retrieve
     * @return the square at the given position
     * @throws IllegalArgumentException when the position is outside the bounds of the board
     */
    public Square getSquare(Position position) throws IllegalArgumentException {
        if (!isValidPosition(position)) {
            String errorMessage = "Position is outside the bounds of the board: " + position;
            Logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        return squares[position.row()][position.col()];
    }

    /**
     * Checks whether the ball can move in the given direction.
     *
     * @param direction the direction in which to check the movement
     * @return true if the ball can move otherwise false
     */
    public boolean canMove(Direction direction) {
        Square currentSquare = this.getSquare(ball);
        if (isGameOver()) {
            return false;
        }
        return wallCheck(direction, currentSquare);
    }

    /**
     * Checks if the ball has reached the goal comparing their positions.
     *
     * @return true if the ball is on the goal, otherwise returns false
     */
    public boolean isGameOver() {
        boolean gameOver = ball.equals(this.goal);
        if (gameOver) {
            Logger.info("Game over! The ball reached the goal.");
        }
        return gameOver;
    }

    private boolean wallCheck(Direction direction, Square square){
        return switch (direction) {
            case UP -> !square.hasWall(Direction.UP);
            case RIGHT -> !square.hasWall(Direction.RIGHT);
            case DOWN -> !square.hasWall(Direction.DOWN);
            case LEFT -> !square.hasWall(Direction.LEFT);
        };
    }

    /**
     * Moves the ball in the given direction until it hits a wall.
     *
     * @param direction The direction in which to move the ball.
     */
    public void move(Direction direction) {
        while(canMove(direction)) {
            Position newPosition = ball.getAdjacentPosition(direction);
            ball.setPosition(newPosition);
        }
        steps++;
        Logger.info("Moving in direction: {}", direction);
        Logger.info("Movement finished. Final position: {}", ball);
        Logger.info("Total steps taken: {}", steps);
    }
}
