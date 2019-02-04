package frc.robot.commands.LEDs

import org.sertain.command.Command
import frc.robot.subsystems.LEDController

public class SendPattern(pattern: String): Command() 
{
      override fun execute(): Boolean
    {
        LEDController.sendToArduino(pattern)
        
        return false;
    }
}
