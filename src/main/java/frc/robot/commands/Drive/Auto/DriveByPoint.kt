package frc.robot.Drive.Auto

import org.sertain.command.Command
import frc.robot.subsystems.Drivetrain
import edu.wpi.first.wpilibj.PIDController
import frc.robot.Util.PIDSourceX
import frc.robot.Util.PIDSourceY
import frc.robot.Util.PIDWriteX
import frc.robot.Util.PIDWriteY

public class DriveByPoint(yLocation: Double , xLocation: Double) : Command()
{
    //localizing target locatin
    val localXLocation: Double = xLocation*2//multpilied by two as the mecanumn drive train is less effecitive in this direction (probably)
    val localYLocation: Double = yLocation
    //needs tuning
    val pidValPX: Double = 0.006
    val pidValIX: Double = 0.0
    val pidValDX: Double = 0.0
    val pidValFX: Double = 0.0
    val pidValPY: Double = 0.006
    val pidValIY: Double = 0.0
    val pidValDY: Double = 0.0
    val pidValFY: Double = 0.0
    //drivetrain constants
    val maxVelocity: Double = 4.23 //meters/second therotircal
    val throttle: Double = 1.0
    var distanceControllerX: PIDController = PIDController(pidValPX, pidValIX, pidValDX, pidValFX, PIDSourceX, PIDWriteX)
    var distanceControllerY: PIDController = PIDController(pidValPY, pidValIY, pidValDY, pidValFY, PIDSourceY, PIDWriteY)
        
    init
    {
        requires(Drivetrain)
    }

    override fun onCreate()
    {
        Drivetrain.resetGyro()
        //enabling PID Loop
        distanceControllerX.enable()    
        distanceControllerY.enable() 
        //setting target location
        distanceControllerX.setSetpoint(localXLocation)  
        distanceControllerY.setSetpoint(localYLocation)  
        //setting Input Range for PID Controllers
        distanceControllerX.setInputRange(-maxVelocity , maxVelocity)//may need to be havled 
        distanceControllerY.setInputRange(-maxVelocity , maxVelocity)
        //setting Output Rangs for the PID Controllers
        distanceControllerX.setOutputRange(-1.0 , 1.0)
        distanceControllerY.setOutputRange(-1.0 , 1.0)
        //setting the percentage tolerance of PID Loop input to SetPoint
        distanceControllerX.setAbsoluteTolerance(0.5)
        distanceControllerY.setAbsoluteTolerance(0.5)
        //setting the PID Loops to be continuous
        distanceControllerX.setContinuous(true)
        distanceControllerY.setContinuous(true)

    }

    override fun execute(): Boolean
    {

        Drivetrain.fieldOrientedDrive(PIDWriteY.getOutput() , PIDWriteX.getOutput(), 0.0, throttle)
        if (distanceControllerX.onTarget() && distanceControllerY.onTarget())
            return true
        return false
    }
   
   override fun onDestroy()
   {
       Drivetrain.killMotors()
   }
}
