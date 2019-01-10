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
    val joystickLeft: Joystick = Joystick((ids.joystickLeftIDs.get("USB-ID"))!!)
	val joystickRight: Joystick = Joystick((ids.joystickRightIDs.get("USB-ID"))!!)
	val xboxController: XboxController = XboxController((ids.xboxIDs.get("USB-ID"))!!)
	val xboxJoystickLeft: Joystick = Joystick((ids.xboxIDs.get("Left-Joystick-Y-Axis"))!!)
	val xboxJoystickRight: Joystick = Joystick((ids.xboxIDs.get("Right-Joystick-Y-Axis"))!!)

    // Buttons
    var toggleDriveMode: Button = JoystickButton(joystickLeft, (ids.joystickLeftIDs.get("Trigger"))!!)


    fun OI()
    {
        //toggleDriveMode.whenPressed(ToggleFieldOriented())
    }
}