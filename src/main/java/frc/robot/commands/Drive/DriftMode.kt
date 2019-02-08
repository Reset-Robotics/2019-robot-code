package frc.robot.commands.Drive

import org.sertain.command.Command
import frc.robot.Mag
import frc.robot.subsystems.Drivetrain

public class DriftMode : Command()
{
	// Called just before this Command runs the first time
	override fun execute(): Boolean
	{
		Drivetrain.toggleDriftMode()
		return true;
	}
}
//Makes our robot think its in a TokyoDriftMovie