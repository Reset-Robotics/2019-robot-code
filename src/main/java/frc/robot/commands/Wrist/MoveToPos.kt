package frc.robot.commands.Wrist

import org.sertain.command.Command
import frc.robot.subsystems.Wrist
import frc.robot.OI


import frc.robot.IDs

public class MoveToPos(position: String): Command()
{
    var ids: IDs = IDs()

    override fun execute(): Boolean
    {
        Wrist.wristMotionMagic(position)
        
        return true;
    }
}