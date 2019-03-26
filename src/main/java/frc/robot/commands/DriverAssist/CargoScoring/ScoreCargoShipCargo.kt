package frc.robot.commands.DriverAssist.CargoScoring

import org.sertain.command.Command
import frc.robot.Orthus
import frc.robot.subsystems.Elevator
import frc.robot.subsystems.Arm
import frc.robot.subsystems.Wrist


public class ScoreCargoShipCargo : Command()
{
	override fun execute(): Boolean
	{
		Elevator.elevatorMM("CargoshipCargo")
		Arm.armMotionMagic("ScoringDown")
		Wrist.wristMotionMagic("CargoshipCargo")
		return true;
	}
		override fun onDestroy()
	{
		Elevator.setElevatorTargetNull()
		Elevator.killMotors()
	}
}