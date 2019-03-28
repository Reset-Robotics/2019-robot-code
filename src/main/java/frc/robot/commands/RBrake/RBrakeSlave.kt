package frc.robot.commands.RBrake

import org.sertain.command.Command
import frc.robot.subsystems.RBrake
import frc.robot.OI
import frc.robot.data.RBrakeData


public class RBrakeSlave : Command()
{
    var rBrakeData: RBrakeData = RBrakeData()
    
    init 
    {
        requires(RBrake)
    }


    // run all our code here
    override fun execute(): Boolean
    {
        // implement throttle slider
                
        var yDirection: Double = OI().joystickRight.getY()
        var xDirection: Double = OI().joystickRight.getX()
        
        var throttle: Double = 1.0 // replace with slider throttle later

        if (Math.abs(yDirection) < rBrakeData.deadzone)
        {
            yDirection = 0.0;
        }

        if (Math.abs(xDirection) > 0.1)
        {
            RBrake.killMotors()
            RBrake.deployIn()
        }

        RBrake.driveRBrake(yDirection)
       /*  if(!RBrake.isLimitSwitchTriggered())
        {
            RBrake.killMotors()
        }
        */
        return false;
        
    }


    // safely stop motors if the command is interrupted or destroyed
    override fun onDestroy() = RBrake.killMotors()
}