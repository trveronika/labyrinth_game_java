package game.state;

import org.tinylog.Logger;

/**
 * Represents a square on the board.
 */
public class Square {
    private boolean topWall;
    private boolean rightWall;
    private boolean bottomWall;
    private boolean leftWall;

    /**
     * Creates a new instance of the {@code Square} class at the given position.
     */
    public Square() {
        this.topWall = false;
        this.rightWall = false;
        this.bottomWall = false;
        this.leftWall = false;
    }

    /**
     * Returns whether there is a wall in the specified direction.
     *
     * @param direction the direction to check for a wall
     * {@return true if there is a wall in the specified direction, false otherwise}
     */
    public boolean hasWall(Direction direction) {
        return switch (direction) {
            case UP -> topWall;
            case RIGHT -> rightWall;
            case DOWN -> bottomWall;
            case LEFT -> leftWall;
        };
    }

    /**
     * Sets the wall in the specified direction of the square.
     *
     * @param direction the direction in which to set the wall
     */
    public void setWall(Direction direction) {
        switch (direction) {
            case UP -> {
                this.setTopWall();
            }
            case RIGHT -> {
                this.setRightWall();
            }
            case DOWN -> {
                this.setBottomWall();
            }
            case LEFT -> {
                this.setLeftWall();
            }
        }
    }

    private void setTopWall() {
        this.topWall = true;
    }

    private void setRightWall() {
        this.rightWall = true;
    }

    private void setBottomWall() {
        this.bottomWall = true;
    }

    private void setLeftWall() {
        this.leftWall = true;
    }

}
