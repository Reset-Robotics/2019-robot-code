package frc.robot.commands.Drive.Auto

import org.sertain.command.Command
import edu.wpi.first.wpilibj.Timer
import frc.robot.subsystems.Drivetrain


public class DriveByTime(xDir: Double, yDir: Double, angle: Double, throttleVal: Double, time: Double) : Command()
{
    var yDirection: Double = xDir
    var xDirection: Double = yDir
    var localTime: Double = time
    var isFinished: Boolean = false
    var localAngle: Double = angle
    var throttle: Double = throttleVal


    init 
    {
        requires(Drivetrain)
    }

    override fun onCreate() { var startTime: Double = Timer.getMatchTime() }

    override fun execute(): Boolean
    {             
        Drivetrain.drive(yDirection, xDirection, localAngle, throttle)

        if(Timer.getMatchTime() - startTime > localTime)
        {
    		isFinished = true
    		Drivetrain.killMotors()
        }
        if (isFinished = true)
            return true;

        return false;
    }

    override fun onDestroy() = Drivetrain.killMotors()
}