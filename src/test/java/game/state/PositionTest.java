package game.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    Position position;

    void assertPosition(int expectedRow, int expectedCol, Position position) {
        assertAll("position",
                () -> assertEquals(expectedRow, position.row()),
                () -> assertEquals(expectedCol, position.col())
        );
    }

    @BeforeEach
    void init() {
        position = new Position(0,0);
    }

    @Test
    void getAdjacentPosition() {
        Position position = new Position(2, 2);
        assertEquals(new Position(1, 2), position.getAdjacentPosition(Direction.UP));
        assertEquals(new Position(2, 3), position.getAdjacentPosition(Direction.RIGHT));
        assertEquals(new Position(3, 2), position.getAdjacentPosition(Direction.DOWN));
        assertEquals(new Position(2, 1), position.getAdjacentPosition(Direction.LEFT));
    }

    @Test
    void testEquals() {
        assertTrue(position.equals(position));
        assertTrue(position.equals(new Position(position.row(), position.col())));
        assertFalse(position.equals(new Position(Integer.MIN_VALUE, position.col())));
        assertFalse(position.equals(new Position(position.row(), Integer.MAX_VALUE)));
        assertFalse(position.equals(new Position(Integer.MIN_VALUE, Integer.MAX_VALUE)));
        assertFalse(position.equals(null));
        assertFalse(position.equals("Hello, World!"));
    }

    @Test
    void testHashCode() {
        assertTrue(position.hashCode() == position.hashCode());
        assertTrue(position.hashCode() == new Position(position.row(), position.col()).hashCode());
    }

    @Test
    void testClone() {
        var clone = position.clone();
        assertTrue(clone.equals(position));
        assertNotSame(position, clone);
    }

    @Test
    void testToString() {
        assertEquals("(0,0)", position.toString());
    }
}