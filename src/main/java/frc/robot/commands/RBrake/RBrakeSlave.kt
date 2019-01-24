package frc.robot.commands.RBrake

import org.sertain.command.Command
import frc.robot.subsystems.RBrake
import frc.robot.OI


public class RBrakeSlave : Command()
{
    // make sure we require any necessary objects/classes
    init {
        requires(RBrake)
    }


    // run all our code here
    override fun execute(): Boolean
    {
        // implement throttle slider
                
        var yDirection: Double = OI().joystickLeft.getY()
        var throttle: Double = 1.0 // replace with slider throttle later
       // if (Math.abs(yDirection) < RBrake.deadzone) 
        //    yDirection = 0.0
        RBrake.driveRBrake(yDirection)
        return false;
    }

    // safely stop motors if the command is interrupted or destroyed
    override fun onDestroy() = RBrake.killMotors()
}