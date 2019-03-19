package frc.robot.commands.Wrist

import org.sertain.command.Command
import frc.robot.subsystems.Wrist
import frc.robot.OI

public class MoveByPow(speed: Double): Command()
{
    var throttle : Double = .33
    override fun execute(): Boolean
    {

        Wrist.move(speed*throttle)
        
        return true;
    }
}