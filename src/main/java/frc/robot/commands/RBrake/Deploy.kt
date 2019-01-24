package frc.robot.commands.RBrake

import org.sertain.command.Command
import frc.robot.subsystems.RBrake


class Deploy(param: String = "Null"): Command()
{
	var localParam = param
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
		if(localParam == "Null")
			RBrake.deploy()
		if(localParam == "Out")
			RBrake.deployOut()
		else
			RBrake.deployIn()
		return true; 
	}
}
