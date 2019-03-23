package frc.robot.commands.DriverAssist.CargoScoring

import org.sertain.command.Command
import frc.robot.Orthus
import frc.robot.subsystems.Elevator
import frc.robot.subsystems.Arm
import frc.robot.subsystems.Wrist


public class ScoreLevel2Cargo : Command()
{
	override fun execute(): Boolean
	{
		Elevator.elevatorMM("MiddleCargo")
		Arm.armMotionMagic("ScoringUp")
		Wrist.wristMotionMagic("MiddleCargo")
		return true;
	}
}