package game.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SquareTest {

    @Test
    public void constructor() {
        Square square = new Square();
        assertFalse(square.hasWall(Direction.UP));
        assertFalse(square.hasWall(Direction.RIGHT));
        assertFalse(square.hasWall(Direction.DOWN));
        assertFalse(square.hasWall(Direction.LEFT));
    }

    @Test
    public void setWall() {
        Square square = new Square();
        assertFalse(square.hasWall(Direction.UP));
        square.setWall(Direction.UP);
        assertTrue(square.hasWall(Direction.UP));
        assertFalse(square.hasWall(Direction.DOWN));
        square.setWall(Direction.DOWN);
        assertTrue(square.hasWall(Direction.DOWN));
        assertFalse(square.hasWall(Direction.LEFT));
        square.setWall(Direction.LEFT);
        assertTrue(square.hasWall(Direction.LEFT));
        assertFalse(square.hasWall(Direction.RIGHT));
        square.setWall(Direction.RIGHT);
        assertTrue(square.hasWall(Direction.RIGHT));
    }

    @Test
    public void hasWall() {
        Square square = new Square();
        assertFalse(square.hasWall(Direction.UP));
        assertFalse(square.hasWall(Direction.RIGHT));
        assertFalse(square.hasWall(Direction.DOWN));
        assertFalse(square.hasWall(Direction.LEFT));
        square.setWall(Direction.UP);
        assertTrue(square.hasWall(Direction.UP));
        assertFalse(square.hasWall(Direction.RIGHT));
        assertFalse(square.hasWall(Direction.DOWN));
        assertFalse(square.hasWall(Direction.LEFT));
        square.setWall(Direction.RIGHT);
        assertTrue(square.hasWall(Direction.RIGHT));
    }
}



