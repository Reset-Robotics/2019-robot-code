// Reset Robotics 2019
package frc.robot.commands.Drive

/*
    As of the end of our 2019 competitive season, this form 
    of autonomous control is still largely non-functional.
    With further testing and tuning during the off-season, 
    this may become a more standard way to control mecanum
    robots autonomously for our team.
    
    The intended use of this functionality is controlled
    autonomous path-like movement for mecanum based robots.
    It works similarly to field-oriented drive modes, but 
    requires additional math and algorithms (such as the 
    Kalman Filter) to work properly. If it were functional,
    this autonomous format would utilize NavX gyro sensor
    readings, PID loops, and other various sensors to
    calculate the robot's position as well as its intended
    path to allow for more accurate three dimensional movement.
 */



 
// Libraries
import org.sertain.command.Command
//import org.apache.commons.math3.filter.KalmanFilter
import edu.wpi.first.wpilibj.PIDController
import edu.wpi.first.wpilibj.PIDOutput
// Subsystems
import frc.robot.subsystems.Drivetrain
// Miscellaneous Imports
import frc.robot.OI
import frc.robot.commands.Drive.distanceControllerX
import frc.robot.commands.Drive.distanceControllerY


/* public class InertialGuidance(xDis: Double, yDis: Double) : Command()
{
    init { requires(Drivetrain) } // Make sure we require the Drivetrain subsystem

    val pidValP: Double = 0.006
    val pidValI: Double = 0.0
    val pidValD: Double = 0.0
    val pidValF: Double = 0.0
    public val cycleTime: Double = Math.pow(1.49925037,-9.0).toDouble()
    //var distanceControllerX: PIDController = PIDController(pidValP, pidValI, pidValD, pidValF, Drivetrain.navx, this, cycleTime)
    //var distanceControllerY: PIDController = PIDController(pidValP, pidValI, pidValD, pidValF, Drivetrain.navx, this, cycleTime)
    public var ax: Double = 0.0
    public var ay: Double = 0.0
    public var sx: Double = 0.0
    public var sy: Double = 0.0
    public var localXDis: Double = xDis
    public var localYDis: Double = yDis
    public var driveXVal: Double = 0.0 
    public var driveYVal: Double = 0.0 
    public var xComplete: Boolean = false
    public var yComplete: Boolean = false
    public var throttle: Double = 0.0

    override fun onCreate()
    {
        distanceControllerX.distanceControllerX.enable()
        distanceControllerX.distanceControllerX.setSetpoint(sx)
        distanceControllerY.distanceControllerY.enable()
        distanceControllerY.distanceControllerY.setSetpoint(sy)

        distanceControllerX.distanceControllerX.setInputRange(0.0, 180.0)
        distanceControllerX.distanceControllerX.setOutputRange(-1.0, 1.0)
        distanceControllerX.distanceControllerX.setAbsoluteTolerance(0.5)
        distanceControllerX.distanceControllerX.setContinuous(true)
        distanceControllerY.distanceControllerY.setInputRange(0.0, 180.0)
        distanceControllerY.distanceControllerY.setOutputRange(-1.0, 1.0)
        distanceControllerY.distanceControllerY.setAbsoluteTolerance(0.5)
        distanceControllerY.distanceControllerY.setContinuous(true)
    }

    // Run all our code here
    override fun execute(): Boolean
    {
        var ax: Double = Drivetrain.navx.getWorldLinearAccelX().toDouble()
        var ay: Double = Drivetrain.navx.getWorldLinearAccelY().toDouble()
        sx = sx + (0.5*(Math.pow(ax,2.0)).toDouble())
        sy = sy + (0.5*(Math.pow(ay,2.0)).toDouble())
        if(Math.abs(localXDis) > Math.abs(sx))
            driveXVal = 1.0*distanceControllerX.throttle
        else 
        {
            driveXVal = 0.0
            xComplete = true
        }

        if(Math.abs(localYDis) > Math.abs(sy))
            driveYVal = 0.56*distanceControllerY.throttle
        else 
        {
            driveYVal = 0.0
            yComplete = true
        }
        
        Drivetrain.fieldOrientedDrive (driveYVal, driveXVal, 0.0, 1.0)
        if (yComplete && xComplete) 
            return true;

        return false;
    }

    // Safely stop motors if the command is interrupted or destroyed
    override fun onDestroy() 
    {
         Drivetrain.killMotors()
         distanceControllerX.distanceControllerX.disable()
         distanceControllerY.distanceControllerY.disable()
    }

    //override fun pidWrite(output: Double){ throttle = output }
}
*/