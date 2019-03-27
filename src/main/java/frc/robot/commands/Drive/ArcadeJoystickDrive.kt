package frc.robot.commands.Drive

import org.sertain.command.Command
import frc.robot.subsystems.Drivetrain
import frc.robot.data.DrivetrainData
import frc.robot.OI


public class ArcadeJoystickDrive : Command()
{
    // Make sure we require any necessary objects/classes
    init 
    {
        requires(Drivetrain)
    }


    // Run all our code here
    override fun execute(): Boolean
    {
        // TODO: implement throttle slider
                
        val driveData: DrivetrainData = DrivetrainData()

        var yDirection: Double = OI().joystickRight.getY()
        var xDirection: Double = OI().joystickRight.getX()
        var spin: Double = OI().joystickRight.getTwist()
        var throttle: Double = ((OI().joystickRight.getThrottle()*-1)+1)/2// Replace with slider throttle later

        if (Math.abs(yDirection) < driveData.deadzone) yDirection = 0.0 
        if (Math.abs(xDirection) < driveData.deadzone) xDirection = 0.0 
        if (Math.abs(spin) < 0.1) 
        {
            spin = 0.0 
            Drivetrain.lockAngle()
        }
        else Drivetrain.unlockAngle()
        
        //System.out.println("[ArcadeJoystickDrive]My spin value is: " + spin)
        Drivetrain.drive(-xDirection, yDirection, spin, throttle)
        return false;
    }

    // Safely stop motors if the command is interrupted or destroyed
    override fun onDestroy() = Drivetrain.killMotors()
}