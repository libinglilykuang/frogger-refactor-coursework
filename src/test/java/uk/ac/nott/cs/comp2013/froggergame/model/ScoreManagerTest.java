package uk.ac.nott.cs.comp2013.froggergame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.score.ScoreManager;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class ScoreManagerTest {

    private ScoreManager scoreManager;

    @BeforeEach
    void setUp() {
        scoreManager = new ScoreManager();
    }

    @Test
    void testAddPointsPositive() {
        scoreManager.addPoints(20);
        assertEquals(20, scoreManager.getPoints());
        assertTrue(scoreManager.hasScoreChanged());
    }

    @Test
    void testAddPointsNegative() {
        scoreManager.addPoints(-10);
        assertEquals(0, scoreManager.getPoints()); // Points should not go below 0
        assertTrue(scoreManager.hasScoreChanged());
    }

    @Test
    void testAddPointsNoChange() {
        scoreManager.addPoints(0);
        assertEquals(0, scoreManager.getPoints());
        assertFalse(scoreManager.hasScoreChanged());
    }

    @Test
    void testHandleMovementScoreMoveUp() {
        scoreManager.handleMovementScore(-5.0);
        assertEquals(10, scoreManager.getPoints()); // Moving up adds points
        assertTrue(scoreManager.hasScoreChanged());
    }

    @Test
    void testHandleMovementScoreMoveDown() {
        scoreManager.handleMovementScore(5.0);
        assertEquals(0, scoreManager.getPoints()); // Moving down subtracts points but stays at 0
        assertTrue(scoreManager.hasScoreChanged());
    }

    @Test
    void testHandleMovementScoreNoMovement() {
        scoreManager.handleMovementScore(0);
        assertEquals(0, scoreManager.getPoints());
        assertFalse(scoreManager.hasScoreChanged());
    }

    @Test
    void testResetScoreChangedFlag() {
        scoreManager.addPoints(10);
        assertTrue(scoreManager.hasScoreChanged());
        scoreManager.resetScoreChangedFlag();
        assertFalse(scoreManager.hasScoreChanged());
    }

    @Test
    void testGetDigitsZero() {
        Stack<Integer> digits = scoreManager.getDigits(0);
        assertEquals(1, digits.size());
        assertEquals(0, digits.pop());
    }

    @Test
    void testGetDigitsPositiveNumber() {
        Stack<Integer> digits = scoreManager.getDigits(1234);
        assertEquals(4, digits.size());
        assertEquals(1, digits.pop());
        assertEquals(2, digits.pop());
        assertEquals(3, digits.pop());
        assertEquals(4, digits.pop());
    }

    @Test
    void testGetDigitsNegativeNumber() {
        assertThrows(IllegalArgumentException.class, () -> scoreManager.getDigits(-123));
    }
}
