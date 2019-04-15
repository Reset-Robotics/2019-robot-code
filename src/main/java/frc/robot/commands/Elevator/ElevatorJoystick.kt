// Reset Robotics 2019
package frc.robot.commands.Elevator

// Libraries
import org.sertain.command.Command
// Subsystems
import frc.robot.subsystems.Elevator
// Miscellaneous Imports
import frc.robot.OI


public class ElevatorJoystick: Command ()
{
    var isElevatorLevel: Boolean = true
    var leftCorrection: Double = 1.0
    var rightCorrection: Double = 1.0
    var joystickInput: Double = 0.0

    init { requires(Elevator) }

    override fun execute(): Boolean
    {
        joystickInput = OI().joystickRight.getY()
        if (Math.abs(joystickInput) < Elevator.deadzone)
            joystickInput = -.13

        if (Elevator.checkBottomSwitches())
            Elevator.ResetEncoders()
        
        //Elevator.lift(leftCorrection*joystickInput, rightCorrection*joystickInput)

        if(Math.abs(joystickInput) > Elevator.deadzone)
            Elevator.lift(joystickInput)
        
        return false; 
    }
}