package org.usfirst.frc.team6325.robot

import org.sertain.command.*
import edu.wpi.first.wpilibj.command.*
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj.Preferences

import org.usfirst.frc.team6325.robot.IDs
import org.usfirst.frc.team6325.robot.commands.Drive.*

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.buttons.*
import edu.wpi.first.wpilibj.GenericHID


public class OI
{
    val ids: IDs()
    // Joysticks/Controllers
    val joystickLeft: Joystick = Joystick((ids.joystickLeftIDs.get("USB-ID"))!!)
	val joystickRight: Joystick = Joystick((ids.joystickRightIDs.get("USB-ID"))!!)
	val xboxController: XboxController = XboxController((ids.xboxIDs.get("USB-ID"))!!)
	val xboxJoystickLeft: Joystick = Joystick((ids.xboxIDs.get("Left-Joystick-Y-Axis"))!!)
	val xboxJoystickRight: Joystick = Joystick((ids.xboxIDs.get("Right-Joystick-Y-Axis"))!!)


    public fun OI()
    {
        // insert commands here
    }
}