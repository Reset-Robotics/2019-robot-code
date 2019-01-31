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
		val left: Double = -OI().joystickLeft.getY()
        //System.err.println("left joystick output number is " + left);
		val right: Double = -OI().joystickRight.getY()
		//System.err.println("right joystick output number is " + right);
        Drivetrain.drive(left, right)

        return false;
    }

    // Safely stop motors if the command is interrupted or destroyed
    override fun onDestroy() = Drivetrain.killMotors()
}