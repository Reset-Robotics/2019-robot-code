package frc.robot.commands.LEDs

import org.sertain.command.Command
import edu.wpi.first.wpilibj.I2C
import frc.robot.subsystems.LEDController

public class SendPattern(pattern: String): Command() 
{
    val localPattern: String = pattern
    override fun execute(): Boolean
    {
        LEDController.sendToArduino(localPattern)
        
        return false;
    }
}
