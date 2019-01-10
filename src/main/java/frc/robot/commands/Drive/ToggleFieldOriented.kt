package frc.robot.commands.Drive

import org.sertain.command.Command
import frc.robot.Mag


public class ToggleFieldOriented : Command()
{
    override fun execute() = Mag.drivetrain.setFieldOriented(!Mag.drivetrain.getFieldOriented())

    // Make this return true when this Command no longer needs to run execute()
    override fun isCompleted(): Boolean = return true
}