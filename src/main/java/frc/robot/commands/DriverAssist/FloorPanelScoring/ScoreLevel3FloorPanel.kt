// Reset Robotics 2019
package frc.robot.commands.DriverAssist.FloorPanelScoring

// Libraries
import org.sertain.command.Command
// Subsystems
import frc.robot.subsystems.Elevator
import frc.robot.subsystems.Arm
import frc.robot.subsystems.Wrist
// Robot Class
import frc.robot.Orthus


public class ScoreLevel3FloorPanel : Command()
{
	override fun execute(): Boolean
	{
		Elevator.elevatorMM("TopPanel")
		Arm.armMotionMagic("Top")
		Wrist.wristMotionMagic("FloorPanel")
		
		return true;
	}
}