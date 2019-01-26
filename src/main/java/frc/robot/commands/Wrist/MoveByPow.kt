package frc.robot.commands.Wrist

import org.sertain.command.Command
import frc.robot.subsystems.Wrist
import frc.robot.OI


import frc.robot.IDs

public class MoveByPow(speed: Double): Command()
{
    var ids: IDs = IDs()

    override fun execute(): Boolean
    {
        Wrist.move(speed)
        
        return true;
    }
}