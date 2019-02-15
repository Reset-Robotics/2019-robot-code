// Rename to <robotname>.kt once we pick a robot name
package frc.robot

import org.sertain.*
import org.sertain.command.Command
import org.sertain.command.and
import edu.wpi.first.wpilibj.Compressor
import edu.wpi.first.wpilibj.command.Scheduler
import edu.wpi.first.wpilibj.I2C

import frc.robot.subsystems.Drivetrain
import frc.robot.subsystems.RBrake
//import frc.robot.subsystems.Elevator
//import frc.robot.commands.Drive.ResetGyro
//import frc.robot.commands.Forklift.ResetForkliftSensor
import frc.robot.subsystems.AutoController
import frc.robot.commands.Drive.Auto.DriveByTime
import frc.robot.commands.Drive.ToggleFieldOriented
import frc.robot.commands.Drive.InertialGuidance


public class Mag : Robot()
{
    public fun main(args: String)
    {
    }

    // Miscellaneous objects/variables
    //public var compressor: Compressor = Compressor(0)

    // Initialize subsystem instance objects for this script
    public val drivetrain: Drivetrain = Drivetrain
    public val rbrake: RBrake = RBrake
    //public var elevator: Elevator = Elevator
    public val autocontroller: AutoController = AutoController
   
    // Initialize I2C object for the Arduino
    //public var arduino: I2C = I2C(Port.kOnboard, 63) // put this in a constants file

    // auto command/chooser initilization goes here later?

    // OI Initialization
    public var oi: OI = OI()

    // Runs on robot initialization; WPILib robotInit() equivalent
    override fun onCreate()
    {
        drivetrain.onCreate()
        //rbrake.onCreate()
        //elevator.onCreate()
        
        // put any data to dashboard here
    }

    // Runs periodically when the robot is disabled; WPILib disabledPeriodic() equivalent
    override fun executeDisabled()
    {
        //drivetrain.unlockAngle()
        //compressor.setClosedLoopControl(false)
        // any dashboard data population here too
    }

    // Runs on autonomous(sandstorm) initialization; WPILib autonomousInit() equivalent
    override fun onAutoStart()
    {
        drivetrain.onCreate()
        autocontroller.onCreate()
        InertialGuidance(2.0, 2.0).start()
        //ToggleFieldOriented()
        //DriveByTime(-1.0, 0.0, 0.25, 1.0, 2.0).start()
        //DriveByTime
        

        /* auto code goes here later. for now, have a banana
        
         _
        //\
        V  \
         \  \_
          \,'.`-.
           |\ `. `.
           ( \  `. `-.                        _,.-:\
            \ \   `.  `-._             __..--' ,-';/
             \ `.   `-.   `-..___..---'   _.--' ,'/
              `. `.    `-._        __..--'    ,' /
                `. `-_     ``--..''       _.-' ,'
                  `-_ `-.___        __,--'   ,'
                     `-.__  `----"""    __.-'
        hh                `--..____..--'
         */
    }

    // Runs periodically during autonomous(sandstorm); WPILib autonomousPeriodic() equivalent
    override fun executeAuto()
    {
        // put any dashboard data
        // something to allow for interruption and transition to 'teleop' either at the end of the sandstorm or as soon as the driver takes control
    }

    // Runs on teleop initialization; WPILib teleopInit() equivalent
    override fun onStart()
    {
        //drivetrain.onCreate()
        //compressor.setClosedLoopControl(true)
        //ResetForkliftSensor()
        //frc.robot.commands.Drive.ResetGyro()
        //frc.robot.commands.Drive.ResetEncoders()
        // reset elevator encoders
        // any other starting configurations
        // nullcheck auto command and cancel it since telop is starting; this can eventually be replaced with smoother transition optimization to allow for a few seconds longer in auto control to allow for the sandstorm barrier to be fully up before drivers take control
    }

    // Runs periodically during teleop; WPILib teleopPeriodic() equivalent
    override fun executeTeleop()
    {
        oi.OI()

        // put dashboard data here
    }
}