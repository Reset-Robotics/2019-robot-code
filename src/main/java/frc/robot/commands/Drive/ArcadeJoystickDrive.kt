package frc.robot.commands.Drive

import org.sertain.command.Command
import frc.robot.subsystems.Drivetrain
import frc.robot.OI

/* this is the main commmand that is looping on the Drivetrain Subsytem and is
responsible for handling joystick inputs to the drive train.
*/

public class ArcadeJoystickDrive : Command()
{
    // make sure we require any necessary objects/classes
    init 
    {
        requires(Drivetrain)
    }


    // run all our code here
    override fun execute(): Boolean
    {
        // implement throttle slider
                
        var yDirection: Double = OI().joystickRight.getY()
        var xDirection: Double = -OI().joystickRight.getX()
        var spin: Double = OI().joystickRight.getTwist() * .75
        var throttle: Double = 1.0 // replace with slider throttle later

        if (Math.abs(yDirection) < Drivetrain.deadzone) 
            yDirection = 0.0
        if (Math.abs(xDirection) < Drivetrain.deadzone) 
            xDirection = 0.0
        if (Math.abs(spin) < Drivetrain.deadzone)
            spin = 0.0

        Drivetrain.drive(yDirection, xDirection, spin, throttle)

        return false;
    }

    // safely stop motors if the command is interrupted or destroyed
    override fun onDestroy() = Drivetrain.killMotors()
}