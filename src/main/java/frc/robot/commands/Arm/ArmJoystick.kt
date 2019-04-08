// Reset Robotics 2019
package frc.robot.commands.Arm

// Libraries
import org.sertain.command.Command
// Subsystems
import frc.robot.subsystems.Arm
// Miscellaneous Imports
import frc.robot.OI
import frc.robot.data.ArmData


public class ArmJoystick: Command()
{
    var armData: ArmData = ArmData() // Initialize a local object for our data class

    var yDirection: Double = 0.0
    var throttle: Double = 1.0

    init { requires(Arm) } // Make sure we require the Arm subsystem, as its necessary for default commands

    // Run all our code here
    override fun execute(): Boolean
    {   
        throttle = ((OI().joystickRight.getThrottle()*-1)+1)/2             
        yDirection = -OI().xboxController.getRawAxis(1)

        if (Math.abs(yDirection) < .25) yDirection = 0.0

        Arm.move(yDirection)

        return false;
    }

    // Safely stop motors if the command is interrupted or destroyed
    override fun onDestroy() = Arm.killMotors()
}