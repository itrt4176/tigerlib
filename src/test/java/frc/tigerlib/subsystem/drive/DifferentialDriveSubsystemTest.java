package frc.tigerlib.subsystem.drive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;


public class DifferentialDriveSubsystemTest {

    @Mock
    private Gyro mockGyro = Mockito.mock(Gyro.class);
    

   @Test 
    void testSetMotors() {
        DifferentialDriveSubsystem driveSub = Mockito.mock(DifferentialDriveSubsystem.class, Mockito.CALLS_REAL_METHODS);
        when(mockGyro.getRotation2d()).thenReturn(new Rotation2d());      
        driveSub.setGyro(mockGyro);
        

        MotorController l = Mockito.mock(MotorController.class);
        MotorController r = Mockito.mock(MotorController.class);        

        driveSub.setMotors(l, r);
        verify(r).setInverted(true);

    } 
}
