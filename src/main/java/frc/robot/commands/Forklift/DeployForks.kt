package frc.robot.commands.Forklift

import org.sertain.command.Command
import frc.robot.subsystems.Forklift


class DeployForks(param: String = "Null"): Command()
{
	var localParam = param
	
	init 
	{
		requires(Forklift)
	}

	override fun execute(): Boolean
    {
		Forklift.deployForks()
		
		return true; 
	}
}