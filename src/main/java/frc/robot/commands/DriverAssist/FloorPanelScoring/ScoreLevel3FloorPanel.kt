package frc.robot.commands.DriverAssist.FloorPanelScoring

import org.sertain.command.Command
import frc.robot.Orthus
import frc.robot.subsystems.Elevator
import frc.robot.subsystems.Arm
import frc.robot.subsystems.Wrist


public class ScoreLevel3FloorPanel : Command()
{
	override fun execute(): Boolean
	{
		Elevator.elevatorMM("TopPanel")
		Arm.armMotionMagic("Scoring")
		Wrist.wristMotionMagic("FloorPanel")
		return true;
	}
}