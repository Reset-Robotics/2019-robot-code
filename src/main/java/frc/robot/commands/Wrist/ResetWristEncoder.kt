package frc.robot.commands.Wrist

import org.sertain.command.Command
import frc.robot.subsystems.Wrist
import frc.robot.OI

public class ResetWristEncoders(position: String): Command() //done after auto unfold is complete
{
    Wrist.ResetEncoders()
}