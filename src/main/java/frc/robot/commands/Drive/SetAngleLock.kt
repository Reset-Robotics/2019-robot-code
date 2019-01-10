package frc.robot.commands.Drive

import org.sertain.command.Command
import frc.robot.Mag


public class SetAngleLock : Command()
{
    public fun SetAngleLock(bool: Boolean): Boolean
    {
        if (bool)
            Mag.drivetrain.lockAngle()
        if (!bool)
            Mag.drivetrain.unlockAngle()
    }

    // Make this return true when this Command no longer needs to run execute()
    override fun isCompleted(): Boolean = return true
}