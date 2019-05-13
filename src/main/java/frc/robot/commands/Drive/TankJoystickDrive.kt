package frc.robot.commands.Drive

import org.sertain.command.Command
import frc.robot.subsystems.Drivetrain
import frc.robot.OI


public class TankJoystickDrive : Command()
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
		var left: Double = -OI().joystickLeft.getY()
        //System.err.println("left joystick output number is " + left);
		var right: Double = -OI().joystickRight.getY()
		//System.err.println("right joystick output number is " + right);

        // Deadzones
        if (Math.abs(left) < 0.2) left = 0.0
        if (Math.abs(right) < 0.2) right = 0.0

        Drivetrain.drive(left, right)

        return false;
    }

    // Safely stop motors if the command is interrupted or destroyed
    override fun onDestroy() = Drivetrain.killMotors()
}