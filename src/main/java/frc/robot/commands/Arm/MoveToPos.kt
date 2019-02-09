package frc.robot.commands.Arm

import org.sertain.command.Command
import frc.robot.subsystems.Arm
import frc.robot.OI

public class MoveToPos(position: Arm.MotionData): Command()
{
    var localPos = position
    override fun execute(): Boolean
    {
        Arm.armMotionMagic(localPos)
        
        return true;
    }
}