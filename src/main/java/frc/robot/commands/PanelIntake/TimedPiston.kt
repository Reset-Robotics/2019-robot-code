package frc.robot.commands.PanelIntake

import org.sertain.command.Command
import frc.robot.subsystems.PanelIntake


class TimedPiston() : Command()
{	
    val timingConstant: Double = 0.2
    var start: Double = 0

	init 
	{
        start = System.currentTimeMillis()
		requires(PanelIntake)
	}

	fun TimedPiston() 
    {
	}

	override fun execute(): Boolean
    {
        PanelIntake.deployIn()
        if (start >= timingConstant) PanelIntake.deployOut()

		return true; 
	}
}