package frc.robot.commands.Drive

import org.sertain.command.Command
import frc.robot.subsystems.Drivetrain


public class ToggleAngleLock: Command()
{

	// Called just before this Command runs the first time
	override fun execute(): Boolean
	{
		Drivetrain.lockAngle()
		return true;
	}
}