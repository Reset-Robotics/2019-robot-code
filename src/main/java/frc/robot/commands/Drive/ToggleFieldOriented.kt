package frc.robot.commands.Drive

import org.sertain.command.Command
import frc.robot.subsystems.Drivetrain


public class ToggleFieldOriented : Command()
{
    val isFieldOriented: Boolean = !Drivetrain.getFieldOriented()
    override fun execute(): Boolean
    {
        Drivetrain.setFieldOriented(isFieldOriented)
        System.err.println(Drivetrain.getFieldOriented())

        return true;
    }
}