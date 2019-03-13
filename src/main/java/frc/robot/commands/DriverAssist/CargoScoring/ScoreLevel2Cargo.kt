package frc.robot.commands.DriverAssist.CargoScoring

import org.sertain.command.Command
import frc.robot.Mag
import frc.robot.subsystems.Elevator
import frc.robot.subsystems.Arm
import frc.robot.subsystems.Wrist


public class ScoreLevel2Cargo : Command()
{
	override fun execute(): Boolean
	{
		Elevator.elevatorMM("MiddleCargo")
		Arm.armMotionMagic("Scoring")
		Wrist.wristMotionMagic("MiddleCargo")
		return true;
	}
}