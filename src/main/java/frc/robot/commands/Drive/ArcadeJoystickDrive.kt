package frc.robot.commands.Drive

import org.sertain.command.Command
import frc.robot.subsystems.Drivetrain
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
                
        var yDirection: Double = OI().joystickRight.getY()
        var xDirection: Double = -OI().joystickRight.getX()
        var spin: Double = OI().joystickRight.getTwist() * .75
        var throttle: Double = ((OI().joystickRight.getThrottle()*-1)+1)/2// Replace with slider throttle later

        if (Math.abs(yDirection) < Drivetrain.deadzone) yDirection = 0.0 
        if (Math.abs(xDirection) < Drivetrain.deadzone) xDirection = 0.0 
        if (Math.abs(spin) < Drivetrain.deadzone) 
        {
            spin = 0.0 
            Drivetrain.lockAngle()
        }
        else Drivetrain.unlockAngle()
        
        Drivetrain.drive(yDirection, xDirection, spin, throttle)
        System.err.println("throttle")
        return false;
    }

    // Safely stop motors if the command is interrupted or destroyed
    override fun onDestroy() = Drivetrain.killMotors()
}