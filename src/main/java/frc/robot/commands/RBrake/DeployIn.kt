package frc.robot.commands.RBrake

import org.sertain.command.Command
import frc.robot.subsystems.RBrake


public class DeployIn: Command()
{
	fun DeployIn() 
    {
		// Use requires() here to declare subsystem dependencies
		requires(RBrake)
	}

	override fun execute(): Boolean 
    {
		RBrake.deployIn()

		return false;
	}
}
