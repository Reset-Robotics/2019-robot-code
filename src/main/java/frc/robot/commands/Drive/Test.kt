package frc.robot.commands.Drive

import org.sertain.command.Command

public class Test : Command()
{
    override fun execute(): Boolean
    {
        System.err.println("Yes Daddy")
        return true
    }
}