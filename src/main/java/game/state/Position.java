package game.state;

import java.util.Objects;

/**
 * Represents a 2D position.
 */
public class Position implements Cloneable {
    private int row;
    private int col;

    /**
     * Creates a {@code Position} object.
     *
     * @param row the row coordinate of the position
     * @param col the column coordinate of the position
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * {@return the row coordinate of the position}
     */
    public int row() {
        return row;
    }

    /**
     * {@return the column coordinate of the position}
     */
    public int col() {
        return col;
    }

    private void setRow(int row) {
        this.row = row;
    }

    private void setCol(int col) {
        this.col = col;
    }

    public void setPosition(Position newPosition) {
        this.setCol(newPosition.col());
        this.setRow(newPosition.row());
    }

    /**
     * Returns the adjacent position in the given direction.
     *
     * @param direction the direction to move in
     * {@return the adjacent position}
     */
    public Position getAdjacentPosition(Direction direction) {
        return switch (direction) {
            case UP -> new Position(row - 1, col);
            case RIGHT -> new Position(row, col + 1);
            case DOWN -> new Position(row + 1, col);
            case LEFT -> new Position(row, col - 1);
        };
    }

    /**
     * Compares the current {@code Position} object to a given object
     *
     * @param o (Object) the object that is compared to the {@code Position} object
     * {@return true if the specified object is equal to the current Position object, false otherwise}
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        return (o instanceof Position other) && row == other.row && col == other.col;
    }

    /**
     * {@return the hash code value for the Position object}
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    /**
     * {@return the shallow copy of the current {@code Position} object}
     */
    @Override
    public Position clone() {
        Position copy;
        try {
            copy = (Position) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // never happens
        }
        return copy;
    }

    /**
     * {@return the string representation of the {@code Position} object}
     */
    @Override
    public String toString() {
        return String.format("(%d,%d)", row, col);
    }
}