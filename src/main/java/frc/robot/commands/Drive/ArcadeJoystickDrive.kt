package frc.robot.commands.Drive

import org.sertain.command.Command
import frc.robot.Mag
import frc.robot.subsystems.Drivetrain


public class ArcadeJoystickDrive : Command()
{
    init
    {
        requires(Drivetrain)
    }

    override fun execute(): Boolean
    {
        // implement throttle slider
                
        var yDirection: Double = -Mag.oi.joystickLeft.getY()
        var xDirection: Double = Mag.oi.joystickLeft.getX()
        var spin: Double = Mag.oi.joystickLeft.getTwist() * .75
        var throttle: Double = 1.0 // replace with slider throttle later

        if (Math.abs(yDirection) < Mag.drivetrain.deadzone) 
            yDirection = 0
        if (Math.abs(xDirection) < Mag.drivetrain.deadzone) 
            xDirection = 0
        if (Math.abs(spin) < Mag.drivetrain.deadzone)
            spin = 0

        Mag.drivetrain.cartesianDrive(xDirection, yDirection, spin, throttle)
    }

    override fun isCanceled() = Mag.drivetrain.killMotors()
    override fun onDestroy() = Mag.drivetrain.killMotors()
}