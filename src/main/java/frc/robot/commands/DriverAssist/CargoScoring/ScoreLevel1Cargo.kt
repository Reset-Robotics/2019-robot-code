package frc.robot.commands.DriverAssist.CargoScoring

import org.sertain.command.Command
import frc.robot.Mag
import frc.robot.subsystems.Elevator
import frc.robot.subsystems.Arm
import frc.robot.subsystems.Wrist


public class ScoreLevel1Cargo : Command()
{
	override fun execute(): Boolean
	{
		Elevator.elevatorMM("BottomCargo")
		Arm.armMotionMagic("Scoring")
		Wrist.wristMotionMagic("BottomCargo")
		return true;
	}
}