package frc.robot.commands.Wrist

import org.sertain.command.Command
import frc.robot.subsystems.Wrist
import frc.robot.OI
import frc.robot.data.WristData


public class MoveToPos(position: WristData.MMData): Command()
{
    var localPos = position

    override fun execute(): Boolean
    {
        Wrist.wristMotionMagic(localPos)
        
        return true;
    }
}