// Reset Robotics 2019
package frc.robot

// Libraries
import org.sertain.*
import org.sertain.command.Command
import org.sertain.command.and
import edu.wpi.first.wpilibj.Compressor
import edu.wpi.first.wpilibj.command.Scheduler
import edu.wpi.first.wpilibj.I2C
import edu.wpi.first.wpilibj.PWM
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
// Subsystems
import frc.robot.subsystems.Arm
import frc.robot.subsystems.AutoController
import frc.robot.subsystems.CargoIntake
import frc.robot.subsystems.Drivetrain
import frc.robot.subsystems.Elevator
import frc.robot.subsystems.Forklift
import frc.robot.subsystems.PanelIntake
import frc.robot.subsystems.RBrake
import frc.robot.subsystems.Wrist
import frc.robot.subsystems.CameraController

// Miscellaneous Imports
//import frc.robot.commands.Drive.ResetGyro
//import frc.robot.commands.Forklift.ResetForkliftSensor
import frc.robot.commands.Drive.Auto.DriveByTime
import frc.robot.commands.Drive.ToggleFieldOriented
import frc.robot.commands.RBrake.Deploy
//import frc.robot.commands.Drive.InertialGuidance
import frc.robot.commands.Elevator.ResetElevatorSensor


public class Orthus : Robot()
{
    public fun main(args: String)
    {
    }

    // Miscellaneous objects/variables
    public var compressor: Compressor = Compressor(0)


    // Initialize subsystem instance objects for this script
    public val arm: Arm = Arm
    public val autocontroller: AutoController = AutoController
    public val cargoIntake: CargoIntake = CargoIntake
    public val drivetrain: Drivetrain = Drivetrain
    public var elevator: Elevator = Elevator
    //public val forklift: Forklift = Forklift
    public val panelIntake: PanelIntake = PanelIntake
    public val rbrake: RBrake = RBrake
    public val wrist: Wrist = Wrist
    public val cameraController: CameraController = CameraController
   
    // Initialize I2C object for the Arduino
    //public var arduino: I2C = I2C(Port.kOnboard, 63) // put this in a constants file

    // OI Initialization
    public var oi: OI = OI()

    // Runs on robot initialization; WPILib robotInit() equivalent
    override fun onCreate()
    {
        arm.onCreate()
        cargoIntake.onCreate()
        drivetrain.onCreate()
        elevator.onCreate()
        //forklift.onCreate()
        panelIntake.onCreate()
        rbrake.onCreate()
        wrist.onCreate()

        cameraController.onCreate()
    	//camera0.setResolution(320, 240)
        //camera0.setFPS(30)
   
        // put any data to dashboard here
    }

    // Runs periodically when the robot is disabled; WPILib disabledPeriodic() equivalent
    override fun executeDisabled()
    {
        drivetrain.unlockAngle()
        compressor.setClosedLoopControl(false)
        //elevator.clearTalons()
        elevator.ResetEncoders()
        //elevator.setElevatorStateNull()

        // any dashboard data populatin here too
    }

    // Runs on autonomous(sandstorm) initialization; WPILib autonomousInit() equivalent
    override fun onAutoStart()
    {
        drivetrain.onCreate()
        autocontroller.onCreate()
        elevator.onCreate() 
    }

    // Runs periodically during autonomous(sandstorm); WPILib autonomousPeriodic() equivalent
    override fun executeAuto()
    {
        // put any dashboard data
    }

    // Runs on teleop initialization; WPILib teleopInit() equivalent
    override fun onStart()
    {
        drivetrain.onCreate()
        elevator.onCreate()
        elevator.ResetEncoders()
        compressor.setClosedLoopControl(true)
    }
     

    // Runs periodically during teleop; WPILib teleopPeriodic() equivalent
    override fun executeTeleop()
    {
        //ResetElevatorSensor()
        oi.OI()
        //arm.ResetEncoder()
        //wrist.ResetEncoder()

        // put dashboard data here
    }
}