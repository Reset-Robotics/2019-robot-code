package frc.robot.commands.Drive

import org.sertain.command.Command
import frc.robot.Mag


public class ResetGyro : Command()
{
	public fun ResetGyro() = requires(Mag.drivetrain)

	// Called just before this Command runs the first time
	override fun execute() = Mag.drivetrain.resetGyro()

	// Make this return true when this Command no longer needs to run execute()
	override fun isCompleted(): Boolean = return true
}