package frc.robot.commands.DriverAssist.FloorPanelScoring

import org.sertain.command.Command
import frc.robot.Mag
import frc.robot.subsystems.Elevator
import frc.robot.subsystems.Arm
import frc.robot.subsystems.Wrist


public class ScoreLevel1FloorPanel : Command()
{
	override fun execute(): Boolean
	{
		Elevator.elevatorMM("BottomPanel")
		Arm.armMotionMagic("Top")
		Wrist.wristMotionMagic("FloorPanel")
		return true;
	}
}