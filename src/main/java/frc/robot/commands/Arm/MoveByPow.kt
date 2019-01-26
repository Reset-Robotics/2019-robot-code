package frc.robot.commands.CargoIntake

import org.sertain.command.Command
import frc.robot.subsystems.CargoIntake
import frc.robot.OI


import frc.robot.IDs

public class MoveByPow(speed: Double): Command()
{
    var ids: IDs = IDs()

    override fun execute(): Boolean
    {
        Arm.move(speed)
        
        return true;
    }
}