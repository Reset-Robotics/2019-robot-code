// Reset Robotics 2019
package frc.robot.commands.DriverAssist.CargoScoring

// Libraries
import org.sertain.command.Command
// Subsystems
import frc.robot.subsystems.Elevator
import frc.robot.subsystems.Arm
import frc.robot.subsystems.Wrist
// Robot Class
import frc.robot.Orthus


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