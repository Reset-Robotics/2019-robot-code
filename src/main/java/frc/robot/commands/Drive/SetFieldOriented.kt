package frc.robot.commands.Drive

import org.sertain.command.Command
import frc.robot.Mag


public class SetFieldOriented : Command()
{

    public fun SetFieldOriented(boolParam: Boolean) = var bool: Boolean = f

    override fun execute() =  Mag.drivetrain.setFieldOriented(bool);

    // Make this return true when this Command no longer needs to run execute()
    override fun isCompleted(): Boolean = return true
}