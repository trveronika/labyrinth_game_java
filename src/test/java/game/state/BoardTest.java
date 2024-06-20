package game.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board1;
    private Position ballPosition1;
    private Board board2;
    private Position ballPosition2;

    @BeforeEach
    public void setup() {
        ballPosition1 = new Position(1, 4);
        board1 = new Board(ballPosition1);
        ballPosition2 = new Position(3,3);
        board2 = new Board(ballPosition2);
    }
    @Test
    public void Board(){
        assertEquals(ballPosition1, board1.getBall());
        assertTrue(board1.getSquare(new Position(0, 0)).hasWall(Direction.RIGHT));
        assertTrue(board1.getSquare(new Position(0, 3)).hasWall(Direction.RIGHT));
        assertTrue(board1.getSquare(new Position(0, 2)).hasWall(Direction.DOWN));
        assertTrue(board1.getSquare(new Position(0, 6)).hasWall(Direction.DOWN));
        assertTrue(board1.getSquare(new Position(2, 1)).hasWall(Direction.DOWN));
        assertTrue(board1.getSquare(new Position(2, 2)).hasWall(Direction.RIGHT));
        assertTrue(board1.getSquare(new Position(2, 6)).hasWall(Direction.LEFT));
        assertTrue(board1.getSquare(new Position(3, 3)).hasWall(Direction.DOWN));
        assertTrue(board1.getSquare(new Position(3, 3)).hasWall(Direction.RIGHT));
        assertTrue(board1.getSquare(new Position(3, 4)).hasWall(Direction.RIGHT));
        assertTrue(board1.getSquare(new Position(4, 0)).hasWall(Direction.DOWN));
        assertTrue(board1.getSquare(new Position(4, 4)).hasWall(Direction.DOWN));
        assertTrue(board1.getSquare(new Position(4, 6)).hasWall(Direction.UP));
        assertTrue(board1.getSquare(new Position(5, 2)).hasWall(Direction.LEFT));
        assertTrue(board1.getSquare(new Position(5, 2)).hasWall(Direction.DOWN));
        assertTrue(board1.getSquare(new Position(5, 2)).hasWall(Direction.RIGHT));
        assertTrue(board1.getSquare(new Position(6, 3)).hasWall(Direction.RIGHT));
        assertTrue(board1.getSquare(new Position(6, 5)).hasWall(Direction.RIGHT));
        assertEquals(ballPosition2, board2.getBall());
    }

    @Test
    public void getSize() {
        assertEquals(7, board1.getSize());
        assertEquals(7, board2.getSize());
    }

    @Test
    public void getBall() {
        assertEquals(ballPosition1, board1.getBall());
        assertEquals(ballPosition2, board2.getBall());
    }

    @Test
    public void getGoal() {
        Position goalPosition1 = new Position(5, 2);
        Position goalPosition2 = new Position(6, 6);
        assertEquals(goalPosition1, board1.getGoal());
        assertNotEquals(goalPosition2, board2.getGoal());
    }

    @Test
    public void getSteps(){
        assertEquals(0,board1.getSteps());
        solveBoard1();
        assertEquals(18,board1.getSteps());
        assertEquals(0,board2.getSteps());
        solveBoard2();
        assertEquals(5,board2.getSteps());
        Board board3 = new Board(new Position(2,0));
        board3.move(Direction.RIGHT);
        assertEquals(1,board3.getSteps());
        board3.move(Direction.LEFT);
        assertEquals(2,board3.getSteps());
        board3.move(Direction.UP);
        assertEquals(3,board3.getSteps());
        board3.move(Direction.DOWN);
        assertEquals(4,board3.getSteps());
    }



    private void solveBoard1(){
        board1.move(Direction.RIGHT);
        board1.move(Direction.DOWN);
        board1.move(Direction.LEFT);
        board1.move(Direction.DOWN);
        board1.move(Direction.LEFT);
        board1.move(Direction.UP);
        board1.move(Direction.LEFT);
        board1.move(Direction.DOWN);
        board1.move(Direction.LEFT);
        board1.move(Direction.UP);
        board1.move(Direction.RIGHT);
        board1.move(Direction.UP);
        board1.move(Direction.RIGHT);
        board1.move(Direction.UP);
        board1.move(Direction.LEFT);
        board1.move(Direction.DOWN);
        board1.move(Direction.RIGHT);
        board1.move(Direction.DOWN);
    }

    private void solveBoard2(){
        board2.move(Direction.UP);
        board2.move(Direction.LEFT);
        board2.move(Direction.DOWN);
        board2.move(Direction.RIGHT);
        board2.move(Direction.DOWN);
    }

    @Test
    public void getSquare_validPosition() {
        Square square1 = board1.getSquare(new Position(3, 3));
        assertNotNull(square1);
        Square square2 = board2.getSquare(new Position(2, 5));
        assertNotNull(square2);
    }

    @Test
    public void getSquare_invalidPosition() {
        assertThrows(IllegalArgumentException.class, () -> board1.getSquare(new Position(7, 7)));
        assertThrows(IllegalArgumentException.class, () -> board2.getSquare(new Position(8, -9)));
    }

    @Test
    public void canMove(){
    assertTrue(board1.canMove(Direction.RIGHT));
    assertTrue(board1.canMove(Direction.LEFT));
    assertTrue(board1.canMove(Direction.UP));
    assertTrue(board1.canMove(Direction.DOWN));
    board1.move(Direction.RIGHT);
    assertFalse(board1.canMove(Direction.RIGHT));
    assertFalse(board1.canMove(Direction.UP));
    board1.move(Direction.LEFT);
    assertFalse(board1.canMove(Direction.LEFT));
    board1.move(Direction.UP);
    assertFalse(board1.canMove(Direction.UP));
    assertTrue(board2.canMove(Direction.LEFT));
    assertTrue(board2.canMove(Direction.UP));
    assertFalse(board2.canMove(Direction.RIGHT));
    assertFalse(board2.canMove(Direction.DOWN));
    board2.move(Direction.UP);
    board2.move(Direction.LEFT);
    board2.move(Direction.DOWN);
    board2.move(Direction.RIGHT);
    board2.move(Direction.DOWN);
    assertFalse(board2.canMove(Direction.DOWN));
    assertFalse(board2.canMove(Direction.UP));
    assertFalse(board2.canMove(Direction.RIGHT));
    assertFalse(board2.canMove(Direction.LEFT));
    }

    @Test
    public void isGameOver() {
        assertFalse(board1.isGameOver());
        board1.move(Direction.RIGHT);
        board1.move(Direction.DOWN);
        board1.move(Direction.LEFT);
        board1.move(Direction.DOWN);
        assertFalse(board1.isGameOver());
        board1.move(Direction.LEFT);
        board1.move(Direction.UP);
        board1.move(Direction.LEFT);
        board1.move(Direction.DOWN);
        assertFalse(board1.isGameOver());
        board1.move(Direction.LEFT);
        board1.move(Direction.UP);
        board1.move(Direction.RIGHT);
        board1.move(Direction.UP);
        assertFalse(board1.isGameOver());
        board1.move(Direction.RIGHT);
        board1.move(Direction.UP);
        board1.move(Direction.LEFT);
        board1.move(Direction.DOWN);
        assertFalse(board1.isGameOver());
        board1.move(Direction.RIGHT);
        board1.move(Direction.DOWN);
        assertTrue(board1.isGameOver());
        assertFalse(board2.isGameOver());
        board2.move(Direction.UP);
        board2.move(Direction.LEFT);
        board2.move(Direction.DOWN);
        board2.move(Direction.RIGHT);
        assertFalse(board2.isGameOver());
        board2.move(Direction.DOWN);
        assertTrue(board2.isGameOver());
    }

    @Test
    public void move(){
        board1.move(Direction.RIGHT);
        assertEquals(new Position(1,6),board1.getBall());
        board1.move(Direction.UP);
        assertEquals(new Position(1,6),board1.getBall());
        board1.move(Direction.RIGHT);
        assertEquals(new Position(1,6),board1.getBall());
        board1.move(Direction.DOWN);
        assertEquals(new Position(3,6),board1.getBall());
        board1.move(Direction.RIGHT);
        assertEquals(new Position(3,6),board1.getBall());
        board1.move(Direction.LEFT);
        assertEquals(new Position(3,5),board1.getBall());
        board2.move(Direction.UP);
        board2.move(Direction.LEFT);
        board2.move(Direction.DOWN);
        board2.move(Direction.RIGHT);
        assertEquals(new Position(2,2),board2.getBall());
        board2.move(Direction.DOWN);
        assertEquals(new Position(5,2),board2.getGoal());
        board2.move(Direction.UP);
        assertEquals(new Position(5,2),board2.getGoal());
        board2.move(Direction.LEFT);
        assertEquals(new Position(5,2),board2.getGoal());
        board2.move(Direction.DOWN);
        assertEquals(new Position(5,2),board2.getGoal());
        board2.move(Direction.RIGHT);
        assertEquals(new Position(5,2),board2.getGoal());
    }
}
