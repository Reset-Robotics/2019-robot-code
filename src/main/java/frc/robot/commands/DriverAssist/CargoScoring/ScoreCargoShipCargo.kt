package frc.robot.commands.DriverAssist.CargoScoring

import org.sertain.command.Command
import frc.robot.Mag
import frc.robot.subsystems.Elevator
import frc.robot.subsystems.Arm
import frc.robot.subsystems.Wrist


public class ScoreCargoShipCargo : Command()
{
	override fun execute(): Boolean
	{
		Elevator.elevatorMM("CargoshipCargo")
		Arm.armMotionMagic("Scoring")
		Wrist.wristMotionMagic("CargoshipCargo")
		return true;
	}
}