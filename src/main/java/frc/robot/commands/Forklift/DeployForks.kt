// Reset Robotics 2019
package frc.robot.commands.Forklift

// Libraries
import org.sertain.command.Command
// Subsystems
import frc.robot.subsystems.Forklift


class DeployForks(param: String = "Null"): Command()
{
	var localParam = param
	
	init { requires(Forklift) }

	override fun execute(): Boolean
    {
		Forklift.deployForks()
		
		return true; 
	}
}