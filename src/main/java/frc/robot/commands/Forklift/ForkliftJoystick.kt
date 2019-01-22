package frc.robot.commands.Drive

import org.sertain.command.Command
import frc.robot.OI
import frc.robot.subsystems.Forklift

public class ForkliftJoystick : Command ()
{
    init 
    {
        requires(Forklift)
    }

    override fun execute(): Boolean
    {
        var joystickInput: Double = OI().joystickLeft.getY()
       // var forkliftToggle: Double = OI().
        if (Math.abs(joystickInput) < Forklift.deadzone)
            joystickInput = 0.0
        
        Forklift.lift(joystickInput)
    }
}