package frc.robot.commands.DriverAssist.PanelScoring

import org.sertain.command.Command
import frc.robot.Orthus
import frc.robot.subsystems.Elevator
import frc.robot.subsystems.Arm
import frc.robot.subsystems.Wrist


public class ScoreLevel2Panel : Command()
{
	override fun execute(): Boolean
	{
		Elevator.elevatorMM("MiddlePanel")
		Arm.armMotionMagic("Scoring")
		Wrist.wristMotionMagic("MiddlePanel")
		return true;
	}
}