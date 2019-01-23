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

    override fun execute(): Boolean
    {
        var joystickInput: Double = OI().joystickLeft.getY()
        //var ElevatorToggle: Double = OI().
        if (Math.abs(joystickInput) < Elevator.deadzone)
            joystickInput = 0.0
            
        Elevator.lift(joystickInput)
        return false; 
    }
}