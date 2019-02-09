package frc.robot.commands.Drive

import org.sertain.command.Command
import frc.robot.Mag
import frc.robot.subsystems.Drivetrain

public class DriftMode : Command()
{
	// Called just before this Command runs the first time
	override fun execute(): Boolean
	{
	
        
		return true;
	}
    
    override fun onCreate()
    {
        Drivetrain.toggleDriftMode()
    }
    override fun onDestroy()
    {
        Drivetrain.toggleDriftMode()
    }
}
//Makes our robot think its in a TokyoDriftMovie