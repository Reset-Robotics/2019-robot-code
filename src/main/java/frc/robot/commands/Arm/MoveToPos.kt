package frc.robot.commands.CargoIntake

import org.sertain.command.Command
import frc.robot.subsystems.CargoIntake
import frc.robot.OI


import frc.robot.IDs

public class MoveToPos(position: String): Command()
{
    var ids: IDs = IDs()

    override fun execute(): Boolean
    {
        Arm.armMotionMagic(position)
        
        return true;
    }
}