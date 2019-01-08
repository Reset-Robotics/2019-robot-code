package org.usfirst.frc.team6325.robot.commands.Drive

import org.sertain.command.Command
import org.usfirst.frc.team6325.robot.Robot


public class SetAngleLock : Command()
{
    public fun SetAngleLock(bool: Boolean): Boolean
    {
        if (bool)
            Robot.drivetrain.lockAngle()
        if (!bool)
            Robot.drivetrain.unlockAngle()
    }

    // Make this return true when this Command no longer needs to run execute()
    override fun isCompleted(): Boolean = return true
}