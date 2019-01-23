package frc.robot

// Library Imports
import org.sertain.command.*
import org.sertain.hardware.*
import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.buttons.*
import edu.wpi.first.wpilibj.GenericHID


// Robot Imports
import frc.robot.IDs
import frc.robot.commands.Drive.*
import frc.robot.commands.RBrake.Deploy
import frc.robot.commands.Drive.ToggleFieldOriented 
import frc.robot.commands.Forklift.*
import frc.robot.commands.BallIntake.*
import frc.robot.subsystems.BallIntake
//import frc.robot.subsystems.RBrake

public class OI 
{
    val ids: IDs = IDs()
    // Joysticks/Controllers
    val joystickLeft: Joystick = Joystick((ids.joystickLeftIDs.get("USB-ID")) ?: 0)
	//public val joystickRight: Joystick = Joystick((ids.joystickRightIDs.get("USB-ID")) ?: 0)
	val joystickRight by lazy { Joystick((ids.joystickRightIDs.get("USB-ID")) ?: 1) }
    val xboxController: XboxController = XboxController((ids.xboxIDs.get("USB-ID")) ?: 2)
	val xboxJoystickLeft: Joystick = Joystick((ids.xboxIDs.get("Left-Joystick-Y-Axis")) ?: 1)
	val xboxJoystickRight: Joystick = Joystick((ids.xboxIDs.get("Right-Joystick-Y-Axis")) ?: 5)
    
    //setting default trigger variable values
    var leftTrigger: Double = 0.0
    var rightTrigger: Double = 0.0
    var kLeft: Int = 0
    var kRight: Int = 1

   fun OI() 
   {
        //toggle field oriented
        joystickRight.whenActive(ids.joystickRightIDs.get("Trigger") ?: 1, ToggleFieldOriented())

        // toggle R-Brake
        joystickLeft.whenActive(ids.joystickLeftIDs.get("Trigger") ?: 1, Deploy())

        //Toggling Forklift Posistion
        joystickLeft.whenActive(ids.joystickLeftIDs.get("Side-Thumb") ?:1, ToggleForklift())

        //Intake Controls
        leftTrigger = xboxController.getTriggerAxis(GenericHID.Hand.kLeft)
        rightTrigger = xboxController.getTriggerAxis(GenericHID.Hand.kRight)

        if (Math.abs(xboxController.getTriggerAxis(GenericHID.Hand.kLeft)) < BallIntake.deadzone)
            leftTrigger = 0.0
        if (Math.abs(xboxController.getTriggerAxis(GenericHID.Hand.kRight)) < BallIntake.deadzone)
            rightTrigger = 0.0
        SpinIntake(leftTrigger, rightTrigger)
        

   }
}