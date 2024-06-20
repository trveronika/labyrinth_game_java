package game.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DirectionTest {

    @Test
    void of() {
        assertSame(Direction.UP, Direction.of(-1, 0));
        assertSame(Direction.RIGHT, Direction.of(0, 1));
        assertSame(Direction.DOWN, Direction.of(1, 0));
        assertSame(Direction.LEFT, Direction.of(0, -1));
    }

    @Test
    void of_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Direction.of(0, 0));
    }

    @Test
    void oppositeDirection() {
        assertSame(Direction.DOWN, Direction.UP.oppositeDirection());
        assertSame(Direction.LEFT, Direction.RIGHT.oppositeDirection());
        assertSame(Direction.UP, Direction.DOWN.oppositeDirection());
        assertSame(Direction.RIGHT, Direction.LEFT.oppositeDirection());
    }




}