package frc.robot.commands.DriverAssist.CargoScoring

import org.sertain.command.Command
import frc.robot.Orthus
import frc.robot.subsystems.Elevator
import frc.robot.subsystems.Arm
import frc.robot.subsystems.Wrist


public class ScoreLevel3Cargo : Command()
{
	override fun execute(): Boolean
	{
		Elevator.elevatorMM("TopCargo")
		Arm.armMotionMagic("Scoring")
		Wrist.wristMotionMagic("TopCargo")
		return true;
	}
}