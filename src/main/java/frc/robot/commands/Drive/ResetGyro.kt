package frc.robot.commands.Drive

import org.sertain.command.Command
import frc.robot.Mag
import frc.robot.subsystems.Drivetrain


public class ResetGyro : Command()
{
	public fun ResetGyro() = requires(Drivetrain)

	// Called just before this Command runs the first time
	override fun execute(): Boolean
	{
		Drivetrain.resetGyro()
		return true;
	}
}