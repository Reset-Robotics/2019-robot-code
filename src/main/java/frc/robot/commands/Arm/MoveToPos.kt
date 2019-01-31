package frc.robot.commands.Arm

import org.sertain.command.Command
import frc.robot.subsystems.Arm
import frc.robot.OI

public class MoveToPos(position: String): Command()
{
    var position: String = "Null"
    override fun execute(): Boolean
    {
        Arm.armMotionMagic(position)
        
        return true;
    }
}