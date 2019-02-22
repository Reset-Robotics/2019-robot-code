package frc.robot.commands.RBrake

import org.sertain.command.Command
import frc.robot.subsystems.RBrake
import frc.robot.commands.LEDs.SendPattern


class Deploy(param: String = "Null"): Command()
{
	var localParam = param
	
	init 
	{
		requires(RBrake)
	}

	override fun execute(): Boolean
    {
		SendPattern("Cargo-Intake-Ready")
		if(localParam == "Null") RBrake.deploy() else if(localParam == "Out") RBrake.deployOut() else RBrake.deployIn()
		
		return true; 
	}
}
