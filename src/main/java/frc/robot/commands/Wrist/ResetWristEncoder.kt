package frc.robot.commands.Wrist

import org.sertain.command.Command
import frc.robot.subsystems.Wrist
import frc.robot.OI

public class ResetWristEncoders(position: String): Command()
{
    override fun execute(): Boolean
    {
        Wrist.ResetEncoder()
        //will reset gyro when gyro code is installed
        return true;
    }
}