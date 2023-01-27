package frc.tigerlib.interpolable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class InterpolatingDoubleTest {
    
    @Test
    void testInterpolatePositives() {
        InterpolatingDouble a = new InterpolatingDouble(0.0);
        InterpolatingDouble b = new InterpolatingDouble(10.0);
        assertEquals(5, a.interpolate(b, .5).value);
    }

    @Test
    void testInterpolateMixedSigns() {
        InterpolatingDouble a = new InterpolatingDouble(3.0);
        InterpolatingDouble b = new InterpolatingDouble(-3.0);
        assertEquals(2.4, a.interpolate(b, .1).value);
    }    

    @Test
    void testInverseInterpolateGood() {
        InterpolatingDouble a = new InterpolatingDouble(0.0);
        InterpolatingDouble b = new InterpolatingDouble(10.0);
        InterpolatingDouble c = new InterpolatingDouble(5.0);
        assertEquals(a.inverseInterpolate(b, c), .5);             
    }

    @Test
    void testInverseInterpolateErrors() {
        InterpolatingDouble a = new InterpolatingDouble(0.0);
        InterpolatingDouble b = new InterpolatingDouble(10.0);
        InterpolatingDouble c = new InterpolatingDouble(5.0);
        assertEquals(b.inverseInterpolate(a, c), 0);             
        assertEquals(b.inverseInterpolate(a, c), 0);                     
    }

    @Test
    void testCompareMatch() {
        InterpolatingDouble a = new InterpolatingDouble(3.0);
        InterpolatingDouble b = new InterpolatingDouble(3.0);    
        assertEquals(a.compareTo(b), 0);       
    }

    @Test
    void testCompareNotMatch() {
        InterpolatingDouble a = new InterpolatingDouble(3.0);
        InterpolatingDouble b = new InterpolatingDouble(5.0);    
        assertNotEquals(a.compareTo(b), 0);       
    }
}
