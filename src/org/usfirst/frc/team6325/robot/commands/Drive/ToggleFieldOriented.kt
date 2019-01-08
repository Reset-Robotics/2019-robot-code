package org.usfirst.frc.team6325.robot.commands.Drive

import org.sertain.command.Command
import org.usfirst.frc.team6325.robot.Robot


public class ToggleFieldOriented : Command()
{
    override fun execute() = Robot.drivetrain.setFieldOriented(!Robot.drivetrain.getFieldOriented())

    // Make this return true when this Command no longer needs to run execute()
    override fun isCompleted(): Boolean = return true
}