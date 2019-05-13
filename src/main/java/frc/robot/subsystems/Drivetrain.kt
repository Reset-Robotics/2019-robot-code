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
    val leftMaster: WPI_TalonSRX = WPI_TalonSRX(11)
	val leftSlaveTalon: WPI_TalonSRX = WPI_TalonSRX(12);
	val leftSlaveVictor: WPI_VictorSPX = WPI_VictorSPX(18);

	val rightMaster: WPI_TalonSRX = WPI_TalonSRX(13);
	val rightSlaveTalon: WPI_TalonSRX = WPI_TalonSRX(10);
    val rightSlaveVictor: WPI_VictorSPX = WPI_VictorSPX(17);

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
		this.leftSlaveTalon.follow(leftMaster)
		this.leftSlaveVictor.follow(leftMaster)
		this.rightSlaveVictor.follow(rightMaster)
		this.rightSlaveTalon.follow(rightMaster)
		// Set up Encoders
		this.leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0)
		this.rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0)
		// Inverse motors
		this.rightMaster.setInverted(true)
		this.rightSlaveTalon.setInverted(false)
		this.rightSlaveVictor.setInverted(false)
		this.leftMaster.setInverted(false)
		this.leftSlaveTalon.setInverted(false)
		this.leftSlaveVictor.setInverted(true)
		// Set Talon Mode
		this.leftMaster.setNeutralMode(NeutralMode.Brake)
        this.rightMaster.setNeutralMode(NeutralMode.Brake)
		// Current Limiting
		this.leftMaster.configContinuousCurrentLimit(40,0) // desired current after limit
		this.leftMaster.configPeakCurrentLimit(35, 0) // max current
		this.leftMaster.configPeakCurrentDuration(100, 0) // how long after max current to be limited (ms)
		this.leftMaster.enableCurrentLimit(true)
		this.rightMaster.configContinuousCurrentLimit(40,0) // desired current after limit
		this.rightMaster.configPeakCurrentLimit(35, 0) // max current
		this.rightMaster.configPeakCurrentDuration(100, 0) // how long after max current to be limited (ms)
        this.rightMaster.enableCurrentLimit(true)

        // zero gyro yaw
        resetGyro()
    }

    fun Drivetrain()
    {

		// Set Talon Mode
		this.leftMaster.setNeutralMode(NeutralMode.Coast)
        this.rightMaster.setNeutralMode(NeutralMode.Coast)

		// Current Limiting
		this.leftMaster.configContinuousCurrentLimit(40,0) // desired current after limit
		this.leftMaster.configPeakCurrentLimit(35, 0) // max current
		this.leftMaster.configPeakCurrentDuration(100, 0) // how long after max current to be limited (ms)
		this.leftMaster.enableCurrentLimit(true)
		this.rightMaster.configContinuousCurrentLimit(40,0) // desired current after limit
		this.rightMaster.configPeakCurrentLimit(35, 0) // max current
		this.rightMaster.configPeakCurrentDuration(100, 0) // how long after max current to be limited (ms)
        this.rightMaster.enableCurrentLimit(true)

		resetGyro()
        resetEncoders()
    }

	fun drive(leftVal: Double, rightVal: Double) 
    {
    	leftMaster.set(leftVal)
    	rightMaster.set(rightVal)
    }
    fun killMotors() 
    {
    	rightMaster.set(0.0)
    	leftMaster.set(0.0)
    }

    fun resetGyro()
    {
        navx.reset()
        navx.zeroYaw()
    }

    fun resetMotorPositions()
    {
        rightMaster.set(0.0)
        leftMaster.set(0.0)
    }

    fun resetEncoders()
    {
        this.rightMaster.setSelectedSensorPosition(0, 0, 0)
        this.leftMaster.setSelectedSensorPosition(0, 0, 0)
    }

    fun getAngle(): Float{ return navx.getYaw(); }

    override fun pidWrite(output: Double){ turnRate = output }

    override val defaultCommand = TankJoystickDrive()
}