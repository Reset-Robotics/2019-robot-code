package frc.robot.subsystems

import org.sertain.command.Subsystem
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.*
import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.PIDController
import edu.wpi.first.wpilibj.PIDOutput
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.Timer

import frc.robot.Mag
import frc.robot.IDs
import frc.robot.commands.Drive.TankJoystickDrive


public object Drivetrain : Subsystem(), PIDOutput
{
    // Constants (move local constants to IDS later)
    val ids: IDs = IDs()

    val pidValP: Double = ids.drivetrainPID.get("P") ?: 0.006
    val pidValI: Double = ids.drivetrainPID.get("I") ?: 0.0
    val pidValD: Double = ids.drivetrainPID.get("D") ?: 0.0
    val pidValF: Double = ids.drivetrainPID.get("F") ?: 0.0
    val wheelCircumference: Double = 18.8495559215
    val deadzone: Double = ids.deadzones.get("Drivetrain") ?: 0.1

    // drive motors
    // motor 0 is the climber
    val frontLeft: WPI_TalonSRX = WPI_TalonSRX(7)
	val leftDriveMaster: WPI_TalonSRX = WPI_TalonSRX(2);
	val backLeft: WPI_VictorSPX = WPI_VictorSPX (9);
	val frontRight: WPI_VictorSPX = WPI_VictorSPX(4);
	val rightDriveMaster: WPI_TalonSRX = WPI_TalonSRX(3);
    val backRight: WPI_VictorSPX = WPI_VictorSPX(8);

    // PID values for turning to angles; PIDF stored in IDs()
    val turnThreshold: Double = 2.0 // how many degrees the robot has to be within for it to stop looking for the required angle
    var turnRate: Double = 0.0
    var driveAngle: Double = 0.0

    // other assorted vars/objects
    val navx: AHRS = AHRS(SPI.Port.kMXP) // "the robot knows where it is at all times."
    //var turnController: PIDController = PIDController(pidValP, pidValI, pidValD, pidValF, navx, this, 0.05)
    var isFieldOriented: Boolean = false
    var isAngleLocked: Boolean = false
    

    override fun onCreate()
    {
        // Set Followers
		this.backLeft.follow(leftDriveMaster)
		this.frontLeft.follow(leftDriveMaster)
		this.backRight.follow(rightDriveMaster)
		this.frontRight.follow(rightDriveMaster)
		// Set up Encoders
		this.leftDriveMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0)
		this.rightDriveMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0)
		// Inverse motors
		this.rightDriveMaster.setInverted(true)
		this.backRight.setInverted(true)
		this.frontRight.setInverted(true)
		this.leftDriveMaster.setInverted(false)
		this.backLeft.setInverted(false)
		this.frontLeft.setInverted(true)
		this.rightDriveMaster.setSensorPhase(false)
		// Set Talon Mode
		this.leftDriveMaster.setNeutralMode(NeutralMode.Brake)
        this.rightDriveMaster.setNeutralMode(NeutralMode.Brake)
		// Current Limiting
		this.leftDriveMaster.configContinuousCurrentLimit(40,0) // desired current after limit
		this.leftDriveMaster.configPeakCurrentLimit(35, 0) // max current
		this.leftDriveMaster.configPeakCurrentDuration(100, 0) // how long after max current to be limited (ms)
		this.leftDriveMaster.enableCurrentLimit(true)
		this.rightDriveMaster.configContinuousCurrentLimit(40,0) // desired current after limit
		this.rightDriveMaster.configPeakCurrentLimit(35, 0) // max current
		this.rightDriveMaster.configPeakCurrentDuration(100, 0) // how long after max current to be limited (ms)
        this.rightDriveMaster.enableCurrentLimit(true)

        // zero gyro yaw
        resetGyro()
    }

    fun Drivetrain()
    {

		// Set Talon Mode
		this.leftDriveMaster.setNeutralMode(NeutralMode.Brake)
        this.rightDriveMaster.setNeutralMode(NeutralMode.Brake)

		// Current Limiting
		this.leftDriveMaster.configContinuousCurrentLimit(40,0) // desired current after limit
		this.leftDriveMaster.configPeakCurrentLimit(35, 0) // max current
		this.leftDriveMaster.configPeakCurrentDuration(100, 0) // how long after max current to be limited (ms)
		this.leftDriveMaster.enableCurrentLimit(true)
		this.rightDriveMaster.configContinuousCurrentLimit(40,0) // desired current after limit
		this.rightDriveMaster.configPeakCurrentLimit(35, 0) // max current
		this.rightDriveMaster.configPeakCurrentDuration(100, 0) // how long after max current to be limited (ms)
        this.rightDriveMaster.enableCurrentLimit(true)

		resetGyro()
        resetEncoders()
    }

	fun drive(leftVal: Double, rightVal: Double) 
    {
    	leftDriveMaster.set(leftVal)
    	rightDriveMaster.set(rightVal)
    }
    fun killMotors() 
    {
    	rightDriveMaster.set(0.0)
    	leftDriveMaster.set(0.0)
    }

    fun resetGyro()
    {
        navx.reset()
        navx.zeroYaw()
    }

    fun resetMotorPositions()
    {
        rightDriveMaster.set(0.0)
        leftDriveMaster.set(0.0)
    }

    fun resetEncoders()
    {
        this.rightDriveMaster.setSelectedSensorPosition(0, 0, 0)
        this.leftDriveMaster.setSelectedSensorPosition(0, 0, 0)
    }

    fun getAngle(): Float{ return navx.getYaw(); }

    override fun pidWrite(output: Double){ turnRate = output }

    override val defaultCommand = TankJoystickDrive()
}