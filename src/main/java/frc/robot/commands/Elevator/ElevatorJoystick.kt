package frc.robot.commands.Elevator

import org.sertain.command.Command
import frc.robot.OI
import frc.robot.subsystems.Elevator

public class ElevatorJoystick: Command ()
{

    var isElevatorLevel: Boolean = true
    var leftCorrection: Double = 1.0
    var rightCorrection: Double = 1.0
    var joystickInput: Double = 0.0

    init 
    {
        requires(Elevator)
    }
    override fun execute(): Boolean
    {
        
       
        //joystick input
        joystickInput = OI().joystickRight.getY()
        if (Math.abs(joystickInput) < Elevator.deadzone)
            joystickInput = -.13

    

        if (Elevator.checkBottomSwitches())
        {
            Elevator.ResetEncoders()
        }
        
       
        //Elevator.lift(leftCorrection*joystickInput, rightCorrection*joystickInput)

        if(Math.abs(joystickInput) > Elevator.deadzone)
        {
        Elevator.lift(joystickInput)
        }
        
        return false; 
    }
}