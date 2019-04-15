// Reset Robotics 2019
package frc.robot.commands.DriverAssist.PanelScoring

// Libraries
import org.sertain.command.Command
// Subsystems
import frc.robot.subsystems.Elevator
import frc.robot.subsystems.Arm
import frc.robot.subsystems.Wrist
// Robot Class
import frc.robot.Orthus


public class ScoreLevel3Panel : Command()
{
	override fun execute(): Boolean
	{
		Elevator.elevatorMM("TopPanel")
		//Arm.armMotionMagic("ScoringUp")
		//Wrist.wristMotionMagic("TopPanel")

		return true;
	}
}