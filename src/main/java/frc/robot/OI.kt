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
import frc.robot.commands.DriverAssist.CargoScoring.*
import frc.robot.commands.DriverAssist.FloorPanelScoring.*
import frc.robot.commands.DriverAssist.PanelScoring.*
import frc.robot.commands.PanelIntake.*

// Util classes
import frc.robot.Util.toggleOnButtonPress

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
        //Driver 1
            joystickRight.whenActive(4, ToggleFieldOriented())//Toggles Field Oriented Drive ---- Top-Button-Bottom-Left
            joystickRight.whenActive(3, ToggleAngleLock())//Toggles angle lock for linging up --- Top-Button-Bottom-Right
            joystickRight.whenActive(5, Deploy()) //Deploys R-Brake
            joystickRight.toggleOnButtonPress(oiData.rightTrigger.id, Deploy()) // Toggle whether the drivetrain is field oriented or normal
    
        //Driver 2
            //Ball intake Controls
                leftTrigger = xboxController.getTriggerAxis(GenericHID.Hand.kLeft)
                rightTrigger = xboxController.getTriggerAxis(GenericHID.Hand.kRight)
            //Panel Intake
                if (xboxController.getBumperPressed(GenericHID.Hand.kRight))
                    TogglePiston()
        // Auto controls
   }
}