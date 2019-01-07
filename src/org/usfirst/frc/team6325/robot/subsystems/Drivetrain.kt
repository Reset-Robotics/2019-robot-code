package org.usfirst.frc.team6325.robot.subsytems

import org.sertain.command.Subsystem
import org.usfirst.frc.team6325.robot.Robot
import org.usfirst.frc.team6325.robot.IDs
//import org.usfirst.frc.team6325.robot.commands.Drive.ArcadeJoystickDrive
import com.ctre.phoenix.motorcontrol.* // possibly deprecated
import com.kanuailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.PIDController
import edu.wpi.first.wpilibj.PIDOutput
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.Timer


class Drivetrain : Subsystem()
{
    // constants (move local constants to IDS later)
    //val ids: IDs = IDs()
    val wheelCircumference: Double = 18.8495559215
    val deadzone: Double = 0.1

    // drive motors
    val driveFrontLeft: WPI_TalonSRX = WPI_TalonSRX((ids.driveMotorsIDs.get("Front-Left"))!!)
    val driveFrontRight: WPI_TalonSRX = WPI_TalonSRX((ids.driveMotorsIDs.get("Front-Right"))!!)
    val driveBackLeft: WPI_TalonSRX = WPI_TalonSRX((ids.driveMotorsIDs.get("Back-Left"))!!)
    val driveBackRight: WPI_TalonSRX = WPI_TalonSRX((ids.driveMotorsIDs.get("Back-Right"))!!)

    // PID values for turning to angles
    val turnP: Double = 0.006
    val turnI: Double = 0.0
    val turnD: Double = 0.0
    val turnF: Double = 0.0
    val turnThreshold: Double = 2.0 // how many degrees the robot has to be within for it to stop looking for the required angle
    val turnRate: Double = 0.0
    val driveAngle: Double = 0.0

    // other assorted vars/objects
    val navx: AHRS = AHRS(SPI.Port.kMXP) // "the robot knows where it is at all times."
    val turnController: PIDController = PIDController(turnP, turnI, turnD, turnF, navx, this)
    var isFieldOriented: Boolean = false
    var isAngleLocked: Boolean = false
    

    override fun onCreate()
    {
        // Set up encoders
        this.driveFrontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0)
        this.driveFrontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0)
        this.driveBackLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0)
        this.driveBackRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0)   

        // configure PID loop
        turnController.setInputRange(-180.0f, 180.0f)
        turnController.setOutputRange(-1.0, 1.0)
        turnController.setAbsoluteTolerance(turnThreshold)
        turnController.setContinuous(true)

        // zero gyro yaw
        resetGyro()
    }

    fun Drivetrain()
    {
        // Set Talon Mode
        this.driveLeftMaster.setNeutralMode(NeutralMode.Brake)
        this.driveRightMaster.setNeutralMode(NeutralMode.Brake)
		
        // Current Limiting
		this.driveFrontLeft.configContinuousCurrentLimit(40,0) // desired current after limit
		this.driveFrontLeft.configPeakCurrentLimit(35, 0) // max current
		this.driveFrontLeft.configPeakCurrentDuration(100, 0) // how long after max current to be limited (ms)
		this.driveFrontLeft.enableCurrentLimit(true)
		this.driveFrontRight.configContinuousCurrentLimit(40,0)
		this.driveFrontRight.configPeakCurrentLimit(35, 0)
		this.driveFrontRight.configPeakCurrentDuration(100, 0)
		this.driveFrontRight.enableCurrentLimit(true)
		this.driveBackLeft.configContinuousCurrentLimit(40,0)
		this.driveBackLeft.configPeakCurrentLimit(35, 0)
		this.driveBackLeft.configPeakCurrentDuration(100, 0)
		this.driveBackLeft.enableCurrentLimit(true)
		this.driveBackRight.configContinuousCurrentLimit(40,0)
		this.driveBackRight.configPeakCurrentLimit(35, 0)
		this.driveBackRight.configPeakCurrentDuration(100, 0)
		this.driveBackRight.enableCurrentLimit(true)   

		resetGyro()
        resetEncoders()
    }

    fun drive(yVal: Double, xVal: Double, spinVal: Double, throttleVal: Double)
    {
        if(isFieldOriented)
        {
            var angle: Double = navx.getAngle() * Math.PI / 180
            var rotatedYVal: Double = yVal * Math.cos(angle) + xVal * Math.sin(angle)
            var rotatedXVal: Double = -yVal * Math.sin(angle) + xVal * Math.cos(angle)

            yVal = rotatedYVal
            xVal = rotatedXVal
        }

        if(isAngleLocked)
            spinVal = turnRate

        cartesianDrive(yVal, xVal, spinVal, throttleVal)
    }

    fun cartesianDrive(yVal: Double, xVal: Double, spinVal: Double, throttleVal: Double)
    {
        arrayOf()
    }

    fun killMotors()
    {
        driveFrontLeft.set(0)
        driveFrontRight.set(0)
        driveBackLeft.set(0)
        driveBackRight.set(0)
    }

    fun resetGyro()
    {
        navx.reset()
        navx.zeroYaw()
    }

    fun resetEncoders()
    {
        this.driveFrontLeft.setSelectedSensorPosition(0, 0, 0)
        this.driveFrontRight.setSelectedSensorPosition(0, 0, 0)
        this.driveBackLeft.setSelectedSensorPosition(0, 0, 0)
        this.driveBackRight.setSelectedSensorPosition(0, 0, 0)
    }

    fun getAngle(): Float = return navx.getYaw();

    fun getEncoderRawFrontLeft(): Double = return driveFrontLeft.getSelectedSensorPosition(0)
    fun getEncoderRawFrontRight(): Double = return driveFrontRight.getSelectedSensorPosition(0)
    fun getEncoderRawBackLeft(): Double = return driveBackLeft.getSelectedSensorPosition(0)
    fun getEncoderRawBackRight(): Double = return driveBackRight.getSelectedSensorPosition(0)


    override fun initDefaultCommand() = setDefaultCommand = ArcadeJoystickDrive()
}