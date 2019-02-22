package frc.robot.commands.Wrist

import org.sertain.command.Command
import frc.robot.subsystems.Wrist
import frc.robot.OI

public class MoveByPow(speed: Double): Command()
{
    var speed: Double = 1.0
    override fun execute(): Boolean
    {

        Wrist.move(speed)
        
        return true;
    }
}