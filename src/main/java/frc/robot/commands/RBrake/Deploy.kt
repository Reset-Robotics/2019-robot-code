package frc.robot.commands.RBrake

import org.sertain.command.Command
import frc.robot.subsystems.RBrake


class Deploy : Command()
{
	var rbrake: RBrake =  RBrake
	init {
		requires(RBrake)
	}
	fun Deploy() 
    {
		// Use requires() here to declare subsystem dependencies
		//execute()
	}
	//override fun isCompleted(): Boolean 
	//{
	//	return true;
	//}

	override fun execute(): Boolean
    {
		rbrake.deploy()

		return true; 
	}
}
