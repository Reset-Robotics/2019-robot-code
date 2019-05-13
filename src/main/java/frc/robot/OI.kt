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
    val ids: IDs = IDs()
    // Joysticks/Controllers
    val joystickLeft by lazy { Joystick(1) }
	val joystickRight by lazy { Joystick(0) }
    val xboxController by lazy { XboxController((ids.xboxIDs.get("USB-ID")) ?: 2) }
	val xboxJoystickLeft by lazy { Joystick((ids.xboxIDs.get("Left-Joystick-Y-Axis")) ?: 1) }
	val xboxJoystickRight by lazy { Joystick((ids.xboxIDs.get("Right-Joystick-Y-Axis")) ?: 5) }
    
    //setting default trigger variable values
    var leftTrigger: Double = 0.0
    var rightTrigger: Double = 0.0
    var kLeft: Int = 0
    var kRight: Int = 1

   fun OI() 
   {
        joystickRight.whenActive(ids.joystickRightIDs.get("Trigger") ?: 1, ToggleFieldOriented()) // Toggle whether the drivetrain is field oriented or normal
        joystickLeft.whenActive(ids.joystickLeftIDs.get("Trigger") ?: 1, Deploy()) // deploys the R-Brake in/out
        
        // TODO: Change to require being held down for a few seconds before triggering
        joystickLeft.whenActive(ids.joystickLeftIDs.get("Side-Thumb") ?:1, ToggleForklift()) // deploys the forklift

        //Ball intake Controls
        leftTrigger = xboxController.getTriggerAxis(GenericHID.Hand.kLeft)
        rightTrigger = xboxController.getTriggerAxis(GenericHID.Hand.kRight)

        if (Math.abs(xboxController.getTriggerAxis(GenericHID.Hand.kLeft)) < CargoIntake.deadzone) leftTrigger = 0.0
        if (Math.abs(xboxController.getTriggerAxis(GenericHID.Hand.kRight)) < CargoIntake.deadzone) rightTrigger = 0.0
        SpinIntake(leftTrigger, rightTrigger)
        
        if(xboxController.getAButtonPressed()) ToggleAutoStop() // checks for auto interupt.
        
        // TODO: Change this to being just a default command run by the RBrake subsystem
        RBrakeSlave()
   }
}