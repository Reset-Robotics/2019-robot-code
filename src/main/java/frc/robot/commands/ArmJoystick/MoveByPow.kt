package frc.robot.commands.Arm

import org.sertain.command.Command
import frc.robot.subsystems.Arm
import frc.robot.OI


import frc.robot.IDs

public class MoveByPow(speed: Double): Command()
{
    var ids: IDs = IDs()
    var speed: Double = 1.0
    override fun execute(): Boolean
    {
        Arm.move(speed)
        
        return true;
    }
}