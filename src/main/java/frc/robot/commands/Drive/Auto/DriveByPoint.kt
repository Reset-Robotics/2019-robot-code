// Reset Robotics 2019
package frc.robot.Drive.Auto

// Libraries
import org.sertain.command.Command
import edu.wpi.first.wpilibj.PIDController
// Subsystems
import frc.robot.subsystems.Drivetrain
// Miscellaneous Imports
import frc.robot.Util.PIDSourceX
import frc.robot.Util.PIDSourceY
import frc.robot.Util.PIDWriteX
import frc.robot.Util.PIDWriteY


public class DriveByPoint(yLocation: Double , xLocation: Double) : Command()
{
    // Localizing target location parameters
    val localXLocation: Double = xLocation*2 // Multiplied by two as a rough correction for strafing taking nearly twice as much effort as normal movement
    val localYLocation: Double = yLocation
    // PID values; Needs tuning
    val pidValPX: Double = 0.006
    val pidValIX: Double = 0.0
    val pidValDX: Double = 0.0
    val pidValFX: Double = 0.0
    val pidValPY: Double = 0.006
    val pidValIY: Double = 0.0
    val pidValDY: Double = 0.0
    val pidValFY: Double = 0.0
    // Drivetrain constants
    val maxVelocity: Double = 4.23 // Theoretical meters per second; Needs tuning
    val throttle: Double = 1.0
    var distanceControllerX: PIDController = PIDController(pidValPX, pidValIX, pidValDX, pidValFX, PIDSourceX, PIDWriteX)
    var distanceControllerY: PIDController = PIDController(pidValPY, pidValIY, pidValDY, pidValFY, PIDSourceY, PIDWriteY)
        
    init { requires(Drivetrain) } // Make sure we require the Drivetrain subsystem

    override fun onCreate()
    {
        Drivetrain.resetGyro()
        // Enabling PID Loop
        distanceControllerX.enable()    
        distanceControllerY.enable() 
        // Setting target location
        distanceControllerX.setSetpoint(localXLocation)  
        distanceControllerY.setSetpoint(localYLocation)  
        // Setting input range for PID Controllers
        distanceControllerX.setInputRange(-maxVelocity , maxVelocity) // May need to be havled; Needs tuning
        distanceControllerY.setInputRange(-maxVelocity , maxVelocity)
        // Setting output range for PID Controllers
        distanceControllerX.setOutputRange(-1.0 , 1.0)
        distanceControllerY.setOutputRange(-1.0 , 1.0)
        // Setting the percentage tolerance of PID Loop input to SetPoint
        distanceControllerX.setAbsoluteTolerance(0.5)
        distanceControllerY.setAbsoluteTolerance(0.5)
        // Setting the PID Loops to be continuous
        distanceControllerX.setContinuous(true)
        distanceControllerY.setContinuous(true)
    }

    override fun execute(): Boolean
    {
        Drivetrain.fieldOrientedDrive(PIDWriteY.getOutput() , PIDWriteX.getOutput(), 0.0, throttle)
        if (distanceControllerX.onTarget() && distanceControllerY.onTarget())
            return true;
        return false;
    }
   
   override fun onDestroy() = Drivetrain.killMotors()
}
