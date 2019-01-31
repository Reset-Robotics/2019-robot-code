package frc.robot.commands.Wrist

import org.sertain.command.Command
import frc.robot.subsystems.Wrist
import frc.robot.OI

public class MoveToPos(position: String): Command()
{
    var position: String = "Null"

    override fun execute(): Boolean
    {
        Wrist.wristMotionMagic(position)
        
        return true;
    }
}