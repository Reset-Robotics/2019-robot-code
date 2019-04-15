// Reset Robotics 2019
package frc.robot.commands.RBrake

// Libraries
import org.sertain.command.Command
// Subsystems
import frc.robot.subsystems.RBrake
// Miscellaneous Imports
import frc.robot.commands.LEDs.SendPattern


class Deploy(param: String = "Null"): Command()
{
	var localParam = param	

	override fun execute(): Boolean
    {
		SendPattern("Cargo-Intake-Ready")
		if(localParam == "Null") 
			RBrake.deploy() 
			else if(localParam == "Out") 
				RBrake.deployOut() 
			else 
				RBrake.deployIn()
		
		return true; 
	}
}
