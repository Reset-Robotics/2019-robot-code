package org.usfirst.frc.team6325.robot.commands.Drive

import org.sertain.command.Command
import org.usfirst.frc.team6325.robot.Robot


public class ArcadeJoystickDrive : Command()
{
    init() = requires Robot.drivetrain

    override fun execute()
    {
        // implement throttle slider
                
        var yDirection: Double = -Robot.oi.joystickLeft.getY()
        var xDirection: Double = Robot.oi.joystickLeft.getX()
        var spin: Double = Robot.oi.joystickLeft.getTwist() * .75
        var throttle: Double = 1.0 // replace with slider throttle later

        if (Math.abs(yDirection) < Robot.drivetrain.deadzone) 
            yDirection = 0
        if (Math.abs(xDirection) < Robot.drivetrain.deadzone) 
            xDirection = 0
        if (Math.abs(spin) < Robot.drivetrain.deadzone)
            spin = 0

        drivetrain.dr
    }
}