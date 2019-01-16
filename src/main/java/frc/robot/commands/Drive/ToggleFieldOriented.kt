package frc.robot.commands.Drive

import org.sertain.command.Command
import frc.robot.subsystems.Drivetrain


public class ToggleFieldOriented : Command()
{
    override fun execute(): Boolean
    {
        Drivetrain.setFieldOriented(!Drivetrain.getFieldOriented())

        return false;
    }
}