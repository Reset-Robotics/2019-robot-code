package org.usfirst.frc.team6325.robot.commands.Drive

import org.sertain.command.Command
import org.usfirst.frc.team6325.robot.Robot


public class ToggleAngleLock : Command()
{
    override fun execute()
    {
        if (!Robot.drivetrain.isAngleLocked)
            Robot.drivetrain.unlockAngle()
        else
            Robot.drivetrain.lockAngle()
    }

    // Make this return true when this Command no longer needs to run execute()
    override fun isCompleted(): Boolean = return true
}