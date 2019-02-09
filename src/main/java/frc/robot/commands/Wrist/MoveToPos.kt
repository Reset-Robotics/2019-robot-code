package frc.robot.commands.Wrist

import org.sertain.command.Command
import frc.robot.subsystems.Wrist
import frc.robot.OI

public class MoveToPos(position: String): Command()
{
    var localPos: String = position

    override fun execute(): Boolean
    {
        Wrist.wristMotionMagic(localPos)
        
        return true;
    }
}