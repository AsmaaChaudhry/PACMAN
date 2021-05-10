package basecode;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GhostTest {
    /**
     * Test if you make ghost without a speed in its constructor that it moves only two spaces
     * Correct Result: Because this method relies on a random number, one if else should pass 
     * every time this test is run
     */
    @Test
    void testMoveDefault() {
        Ghost testGhost = new Ghost("src/images/blueGhosst1.gif", 50, 50);
        testGhost.move(5);
        if(testGhost.ghostDir == 1 ) {
            assertEquals(testGhost.getGhostX(), 52);
        }else if(testGhost.ghostDir ==2) {
            assertEquals(testGhost.getGhostY(), 52);
        }else if (testGhost.ghostDir == 3) {
            assertEquals(testGhost.getGhostX(), 48);
        }else if(testGhost.ghostDir == 4) {
            assertEquals(testGhost.getGhostY(),48);
        }
        
    }
    /**
     * Test if you make ghost with a speed moves that speed
     * Correct Result: Because this method relies on a random number, one if else should pass 
     * every time this test is run
     */
    @Test
    void testMoveSpeed() {
        Ghost speedGhost = new Ghost("src/images/blueGhosst1.gif", 50, 50, 5);
        speedGhost.move(5);
        System.out.println(speedGhost.ghostDir);
        if(speedGhost.ghostDir == 1 ) {
            assertEquals(speedGhost.getGhostX(), 55);
        }else if(speedGhost.ghostDir ==2) {
            assertEquals(speedGhost.getGhostY(), 55);
        }else if (speedGhost.ghostDir == 3) {
            assertEquals(speedGhost.getGhostX(), 45);
        }else if(speedGhost.ghostDir == 4) {
            assertEquals(speedGhost.getGhostY(),45);
        }
    }

}
