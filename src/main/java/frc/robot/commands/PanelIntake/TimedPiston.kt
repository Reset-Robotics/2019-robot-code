// Reset Robotics 2019
package frc.robot.commands.PanelIntake

// Libraries
import org.sertain.command.Command
// Subsystems
import frc.robot.subsystems.PanelIntake


class TimedPiston() : Command()
{	
    val timingConstant: Double = 0.2
    var start: Long = 10L

	init 
	{
        start = System.currentTimeMillis()
		requires(PanelIntake)
	}

	override fun execute(): Boolean
    {
        PanelIntake.deployIn()
        if (start >= timingConstant) PanelIntake.deployOut()

		return true; 
	}
}