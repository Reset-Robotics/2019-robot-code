package frc.robot.commands.RBrake

import org.sertain.command.Command
import frc.robot.subsystems.RBrake


public class DeployOut: Command()
{
	fun DeployOut() 
    {
		// Use requires() here to declare subsystem dependencies
		requires(RBrake)
	}

	override fun execute(): Boolean
    {
		RBrake.deployOut()

		return false;
	}
}
