package frc.robot.commands.Drive

import org.sertain.command.Command
import frc.robot.Mag


public class ToggleAngleLock : Command()
{
    override fun execute()
    {
        if (!Mag.drivetrain.isAngleLocked)
            Mag.drivetrain.unlockAngle()
        else
            Mag.drivetrain.lockAngle()
    }

    // Make this return true when this Command no longer needs to run execute()
    override fun isCompleted(): Boolean = return true
}