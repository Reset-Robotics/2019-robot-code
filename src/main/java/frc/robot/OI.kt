package frc.robot

// Library Imports
import org.sertain.command.*
import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.buttons.*
import edu.wpi.first.wpilibj.GenericHID

// Robot Imports
import frc.robot.IDs
import frc.robot.commands.Drive.*


public class OI 
{
    val ids: IDs = IDs()
    // Joysticks/Controllers
    val joystickLeft: Joystick = Joystick((ids.joystickLeftIDs.get("USB-ID")) ?: 0)
	val joystickRight: Joystick = Joystick((ids.joystickRightIDs.get("USB-ID")) ?: 1)
	val xboxController: XboxController = XboxController((ids.xboxIDs.get("USB-ID")) ?: 2)
	val xboxJoystickLeft: Joystick = Joystick((ids.xboxIDs.get("Left-Joystick-Y-Axis")) ?: 1)
	val xboxJoystickRight: Joystick = Joystick((ids.xboxIDs.get("Right-Joystick-Y-Axis")) ?: 5)

    // Buttons
    var toggleDriveMode: Button = JoystickButton(joystickLeft, (ids.joystickLeftIDs.get("Trigger")) ?: 1)


    fun OI()
    {
        //toggleDriveMode.whenPressed(ToggleFieldOriented())
    }
}