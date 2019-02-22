package frc.robot.commands.Forklift

import org.sertain.command.Command
import frc.robot.subsystems.Forklift

public class ResetForkliftSensor: Command()
{
    // resets all the sensors to do with the Forklist subsystem
    override fun execute(): Boolean
    {
        Forklift.ResetEncoders() //reseting both motor encoders on the forklift to have the same "zeroed" state
        //will reset gyro when gyro code is installed
        return true;
    }
}