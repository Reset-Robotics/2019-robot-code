package frc.robot.commands.Drive

import org.sertain.command.Command
import frc.robot.Mag
import frc.robot.subsystems.Drivetrain
import frc.robot.OI


public class ArcadeJoystickDrive : Command()
{
    init
    {
        requires(Drivetrain())
    }

    override fun execute(): Boolean
    {
        // implement throttle slider
                
        var yDirection: Double = -OI().joystickLeft.getY()
        var xDirection: Double = -OI().joystickLeft.getX()
        var spin: Double = OI().joystickLeft.getTwist() * .75
        var throttle: Double = 1.0 // replace with slider throttle later

        if (Math.abs(yDirection) < Drivetrain().deadzone) 
            yDirection = 0.0
        if (Math.abs(xDirection) < Drivetrain().deadzone) 
            xDirection = 0.0
        if (Math.abs(spin) < Drivetrain().deadzone)
            spin = 0.0

        Drivetrain().cartesianDrive(xDirection, yDirection, spin, throttle)

        return false;
    }

    override fun isCanceled() = Drivetrain().killMotors()
    override fun onDestroy() = Drivetrain().killMotors()
}