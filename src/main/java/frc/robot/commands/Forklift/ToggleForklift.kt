package frc.robot.commands.Forklift

import org.sertain.command.Command
import frc.robot.OI
import frc.robot.subsystems.Forklift

public class ToggleForklift: Command ()
{
     init 
    {
        requires(Forklift)
    }
    override fun execute(): Boolean
    {
       // var currentstate: Boolean = Forklift.getForkliftState()
        //Forklift.forkliftMM(!currentstate) 
        return true; 
    }
}