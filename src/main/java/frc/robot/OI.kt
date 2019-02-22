package frc.robot

// Library Imports
import org.sertain.command.*
import org.sertain.hardware.*
import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.buttons.*
import edu.wpi.first.wpilibj.GenericHID

// Robot Imports
import frc.robot.commands.Drive.*
//import frc.robot.commands.RBrake.*
//import frc.robot.commands.Forklift.*
//import frc.robot.commands.CargoIntake.*
//import frc.robot.subsystems.CargoIntake
//import frc.robot.subsystems.RBrake
import frc.robot.data.OIData
import frc.robot.commands.RBrake.*

// Util classes
import frc.robot.util.toggleOnButtonPress

public class OI 
{
    val oiData: OIData = OIData()

    // Joysticks/Controllers
    val joystickLeft by lazy { Joystick((oiData.leftUSBID.id)) }
	val joystickRight by lazy { Joystick((oiData.rightUSBID.id)) }
    val xboxController by lazy { XboxController((oiData.xboxUSBID.id)) }
	val xboxJoystickLeft by lazy { Joystick((oiData.xboxLeftJoystickYAxis.id)) }
    val xboxJoystickRight by lazy { Joystick((oiData.xboxRightJoystickYAxis.id)) }
    
    //setting default trigger variable values
    var leftTrigger: Double = 0.0
    var rightTrigger: Double = 0.0
    var kLeft: Int = 0
    var kRight: Int = 1

   fun OI() 
   {
        joystickRight.whenActive(4, ResetGyro())//Top-Button-Bottom-Left
        joystickRight.whenActive(3, ToggleAngleLock())//Top-Button-Bottom-Right
        joystickRight.whenActive(5, Deploy())
        //joystickRight.toggleOnButtonPress(oiData.rightTrigger.id, Deploy()) // Toggle whether the drivetrain is field oriented or normal
        //joystickLeft.whenActive((IDs().joystickLeftIDs.get("Trigger")) ?: 1, Deploy()) // deploys the R-Brake in/out
        
        // TODO: Change to require being held down for a few seconds before triggering
        //joystickLeft.whenActive((IDs().joystickLeftIDs.get("Side-Thumb")) ?: 2, DeployForks()) // deploys the forklift

        //Ball intake Controls
        leftTrigger = xboxController.getTriggerAxis(GenericHID.Hand.kLeft)
        rightTrigger = xboxController.getTriggerAxis(GenericHID.Hand.kRight)

        //if (Math.abs(xboxController.getTriggerAxis(GenericHID.Hand.kLeft)) < CargoIntake.deadzone) leftTrigger = 0.0
        //if (Math.abs(xboxController.getTriggerAxis(GenericHID.Hand.kRight)) < CargoIntake.deadzone) rightTrigger = 0.0
        //SpinIntake(leftTrigger, rightTrigger)
        
        //if(xboxController.getAButtonPressed()) ToggleAutoStop() // checks for auto interupt.
   }
}