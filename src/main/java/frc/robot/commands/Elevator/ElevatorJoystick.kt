package frc.robot.commands.Elevator

import org.sertain.command.Command
import frc.robot.OI
import frc.robot.subsystems.Elevator

public class ElevatorJoystick: Command ()
{
    init 
    {
        requires(Elevator)
    }
    var isElevatorLevel: Boolean = true
    var leftCorrection: Double = 1.0
    var rightCorrection: Double = 1.0
    override fun execute(): Boolean
    {
        
       
        //joystick input
         var joystickInput: Double = OI().joystickRight.getY()
        if (Math.abs(joystickInput) < Elevator.deadzone)
        {
            joystickInput = 0.0
        }
        else
        {
            Elevator.lift(joystickInput, joystickInput)
        }
        
        return false; 
    }
}