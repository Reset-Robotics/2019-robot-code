package org.usfirst.frc.team6325.robot.commands.Drive

import org.sertain.command.Command
import org.usfirst.frc.team6325.robot.Robot


public class ResetGyro : Command()
{
	public fun ResetGyro() = requires(Robot.drivetrain)

	// Called just before this Command runs the first time
	override fun execute() = Robot.drivetrain.resetGyro()

	// Make this return true when this Command no longer needs to run execute()
	override fun isCompleted(): Boolean = return true
}