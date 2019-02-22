package frc.robot.commands.PanelIntake

import org.sertain.command.Command
import frc.robot.subsystems.PanelIntake


class TogglePiston(param: String = "Null") : Command()
{
	var localParam = param
	
	init 
	{
		requires(PanelIntake)
	}

	override fun execute(): Boolean
    {
		if(localParam == "Null") PanelIntake.deploy() else if(localParam == "Out") PanelIntake.deployOut() else PanelIntake.deployIn()
		
		return true; 
	}
}