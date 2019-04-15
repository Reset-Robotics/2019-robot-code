// Reset Robotics 2019
package frc.robot.commands.RBrake

// Libraries
import org.sertain.command.Command
// Subsystems
import frc.robot.subsystems.RBrake


class ToggleAntilock(param: String = "Null"): Command()
{
	var localParam = param
	
	init { requires(RBrake)	}

	override fun execute(): Boolean
    {
		if(localParam == "Null") 
			RBrake.antilockModeEnable() 
		else if(localParam == "Disable") 
			RBrake.antilockModeDisable() 
		else 
			RBrake.antilockModeEnable()
		
		return true; 
	}
}