package frc.robot.commands.RBrake

import org.sertain.command.Command
import frc.robot.subsystems.RBrake


class Deploy(deployIn: Boolean=false,
			 deployOut: Boolean=false): Command()
{
	var rbrake: RBrake =  RBrake
	var localDeployIn = deployIn
	var localDeployOut = deployOut
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
		if(!localDeployIn && !localDeployOut)
			RBrake.deploy()
		if(localDeployIn)
			RBrake.deployIn()
		else(localDeployOut)
			RBrake.deployOut()
		return true; 
	}
}
