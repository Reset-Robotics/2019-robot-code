package org.usfirst.frc.team6325.robot.commands.Drive

import org.sertain.command.Command
import org.usfirst.frc.team6325.robot.Robot


public class ToggleFieldOriented : Command()
{

    public fun SetFieldOriented(boolParam: Boolean) = var bool: Boolean = f

    override fun execute() =  Robot.drivetrain.setFieldOriented(bool);

    // Make this return true when this Command no longer needs to run execute()
    override fun isCompleted(): Boolean = return true
}