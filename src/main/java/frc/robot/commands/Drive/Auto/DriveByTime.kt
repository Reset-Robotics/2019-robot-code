package frc.robot.commands.Drive.Auto

import org.sertain.command.Command
import frc.robot.subsystems.Drivetrain


public class DriveByTime(xDir: Double, yDir: Double, angle: Double, throttleVal: Double, timeInSeconds: Double) : Command()
{
    var yDirection: Double = xDir
    var xDirection: Double = yDir
    var timeInMillis: Double = timeInSeconds * 1000.0
    var startTime: Long = 0
    var isFinished: Boolean = false
    var localAngle: Double = angle
    var throttle: Double = throttleVal


    init 
    {
        requires(Drivetrain)
    }

    override fun onCreate() { startTime = System.currentTimeMillis() }

    override fun execute(): Boolean
    {             
        Drivetrain.unlockAngle()
        Drivetrain.drive(yDirection, xDirection, localAngle, throttle)

        if(System.currentTimeMillis() - startTime > timeInMillis)
        {
    		isFinished = true
    		Drivetrain.killMotors()
        }
        if (isFinished)
            return true;

        return false;
    }

    override fun onDestroy() = Drivetrain.killMotors()
}