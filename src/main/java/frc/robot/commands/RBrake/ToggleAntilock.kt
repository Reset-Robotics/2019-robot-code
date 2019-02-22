package frc.robot.commands.RBrake

import org.sertain.command.Command
import frc.robot.subsystems.RBrake


class ToggleAntilock(param: String = "Null"): Command()
{
	var localParam = param
	
	init 
	{
		requires(RBrake)
	}

	override fun execute(): Boolean
    {
		if(localParam == "Null") RBrake.antilockModeEnable() else if(localParam == "Disable") RBrake.antilockModeDisable() else RBrake.antilockModeEnable()
		
		return true; 
	}
}