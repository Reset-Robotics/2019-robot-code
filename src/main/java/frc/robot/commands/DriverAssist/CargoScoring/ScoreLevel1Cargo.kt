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


public class ScoreLevel1Cargo : Command()
{
	override fun execute(): Boolean
	{
		Elevator.elevatorMM("BottomCargo")
		Arm.armMotionMagic("ScoringDown")
		Wrist.wristMotionMagic("BottomCargo")
		
		return true;
	}
}