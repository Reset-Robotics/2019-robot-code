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
import frc.robot.commands.RBrake.*
import frc.robot.commands.Forklift.*
import frc.robot.commands.CargoIntake.*
import frc.robot.subsystems.CargoIntake
import frc.robot.subsystems.RBrake

public class OI 
{
    // Joysticks/Controllers
    val joystickLeft by lazy { Joystick(IDs().joystickLeftIDs.get("USB-ID")!!) }
	val joystickRight by lazy { Joystick(IDs().joystickRightIDs.get("USB-ID")!!) }
    val xboxController by lazy { XboxController(IDs().xboxIDs.get("USB-ID")!!) }
	val xboxJoystickLeft by lazy { Joystick(IDs().xboxIDs.get("Left-Joystick-Y-Axis")!!) }
	val xboxJoystickRight by lazy { Joystick(IDs().xboxIDs.get("Right-Joystick-Y-Axis")!!) }
    
    //setting default trigger variable values
    var leftTrigger: Double = 0.0
    var rightTrigger: Double = 0.0
    var kLeft: Int = 0
    var kRight: Int = 1

   fun OI() 
   {
        joystickRight.whenActive(IDs().joystickRightIDs.get("Trigger")!!, ToggleFieldOriented()) // Toggle whether the drivetrain is field oriented or normal
        joystickLeft.whenActive(IDs().joystickLeftIDs.get("Trigger")!!, Deploy()) // deploys the R-Brake in/out
        
        // TODO: Change to require being held down for a few seconds before triggering
        joystickLeft.whenActive(IDs().joystickLeftIDs.get("Side-Thumb")!!, ToggleForklift()) // deploys the forklift

        //Ball intake Controls
        leftTrigger = xboxController.getTriggerAxis(GenericHID.Hand.kLeft)
        rightTrigger = xboxController.getTriggerAxis(GenericHID.Hand.kRight)

        if (Math.abs(xboxController.getTriggerAxis(GenericHID.Hand.kLeft)) < CargoIntake.deadzone) leftTrigger = 0.0
        if (Math.abs(xboxController.getTriggerAxis(GenericHID.Hand.kRight)) < CargoIntake.deadzone) rightTrigger = 0.0
        SpinIntake(leftTrigger, rightTrigger)
        
        if(xboxController.getAButtonPressed()) ToggleAutoStop() // checks for auto interupt.
   }
}