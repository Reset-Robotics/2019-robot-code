package frc.robot.commands.DriverAssist.PanelScoring

import org.sertain.command.Command
import frc.robot.Mag
import frc.robot.subsystems.Elevator
import frc.robot.subsystems.Arm
import frc.robot.subsystems.Wrist


public class ScoreLevel3Panel : Command()
{
	override fun execute(): Boolean
	{
		Elevator.elevatorMM("TopPanel")
		Arm.armMotionMagic("Scoring")
		Wrist.wristMotionMagic("TopPanel")
		return true;
	}
}