package frc.robot.subsystems

import org.sertain.command.Subsystem
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.*
import com.ctre.phoenix.motorcontrol.FeedbackDevice.*
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import frc.robot.commands.Forklift.ForkliftJoystick
import frc.robot.data.ForkliftData


public object Forklift : Subsystem()
 {
    val forkliftData: ForkliftData = ForkliftData()
    val forkliftLeft: WPI_TalonSRX = WPI_TalonSRX(forkliftData.leftMotor)
    val forkliftRight: WPI_TalonSRX = WPI_TalonSRX(forkliftData.rightMotor)

    fun Forklift()
    {
        //current limiting 
        this.forkliftLeft.configContinuousCurrentLimit(40,0) // Desired current after limit
        this.forkliftLeft.configPeakCurrentLimit(35,0) // Max current
        this.forkliftLeft.configPeakCurrentDuration(100,0)  // How long after max current to be limited (ms)
        this.forkliftLeft.enableCurrentLimit(true) 
        this.forkliftRight.configContinuousCurrentLimit(40,0)
        this.forkliftRight.configPeakCurrentLimit(35,0)
        this.forkliftRight.configPeakCurrentDuration(100,0)
        this.forkliftRight.enableCurrentLimit(true) 

        //set Talon Mode
        this.forkliftLeft.setNeutralMode(NeutralMode.Brake)
        this.forkliftRight.setNeutralMode(NeutralMode.Brake)
    }

    override fun onCreate()
    {
        // Setting up talons to ensure no unexpected behavior
        this.forkliftLeft.configFactoryDefault()
        this.forkliftRight.configFactoryDefault()
        // Set up for encoders
        this.forkliftLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, forkliftData.kPIDLoopIdx, forkliftData.kTimeoutMs);
        this.forkliftRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, forkliftData.kPIDLoopIdx, forkliftData.kTimeoutMs);

        /**
		 * Configure Talon SRX Output and Sesnor direction accordingly
		 * Invert Motor to have green LEDs when driving Talon Forward / Requesting Postiive Output
		 * Phase sensor to have positive increment when driving Talon Forward (Green LED)
		 */
        this.forkliftLeft.setSensorPhase(true)
        this.forkliftRight.setSensorPhase(true)
        this.forkliftLeft.setInverted(false)
        this.forkliftRight.setInverted(false)
        // Set relevant frame periods to be at least as fast as periodic rate
		this.forkliftLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, forkliftData.kTimeoutMs);
	    this.forkliftLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, forkliftData.kTimeoutMs);
		this.forkliftRight.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, forkliftData.kTimeoutMs);
	    this.forkliftRight.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, forkliftData.kTimeoutMs);
        // Set the peak and nominal outputs
		this.forkliftRight.configNominalOutputForward(0.0, forkliftData.kTimeoutMs);
		this.forkliftRight.configNominalOutputReverse(0.0, forkliftData.kTimeoutMs);
		this.forkliftRight.configPeakOutputForward(1.0, forkliftData.kTimeoutMs);
		this.forkliftRight.configPeakOutputReverse(-1.0, forkliftData.kTimeoutMs);
		this.forkliftLeft.configNominalOutputForward(0.0, forkliftData.kTimeoutMs);
		this.forkliftLeft.configNominalOutputReverse(0.0, forkliftData.kTimeoutMs);
		this.forkliftLeft.configPeakOutputForward(1.0, forkliftData.kTimeoutMs);
		this.forkliftLeft.configPeakOutputReverse(-1.0, forkliftData.kTimeoutMs);
        // Set Motion Magic gains in slot kSlotIdx
		this.forkliftRight.selectProfileSlot(forkliftData.kSlotIdx, forkliftData.kPIDLoopIdx);
		this.forkliftRight.config_kF(forkliftData.kSlotIdx, forkliftData.kGainskF, forkliftData.kTimeoutMs);
		this.forkliftRight.config_kP(forkliftData.kSlotIdx, forkliftData.kGainskP, forkliftData.kTimeoutMs);
		this.forkliftRight.config_kI(forkliftData.kSlotIdx, forkliftData.kGainskI, forkliftData.kTimeoutMs);
		this.forkliftRight.config_kD(forkliftData.kSlotIdx, forkliftData.kGainskD, forkliftData.kTimeoutMs);
		this.forkliftLeft.selectProfileSlot(forkliftData.kSlotIdx, forkliftData.kPIDLoopIdx);
		this.forkliftLeft.config_kF(forkliftData.kSlotIdx, forkliftData.kGainskF, forkliftData.kTimeoutMs);
		this.forkliftLeft.config_kP(forkliftData.kSlotIdx, forkliftData.kGainskP, forkliftData.kTimeoutMs);
		this.forkliftLeft.config_kI(forkliftData.kSlotIdx, forkliftData.kGainskI, forkliftData.kTimeoutMs);
		this.forkliftLeft.config_kD(forkliftData.kSlotIdx, forkliftData.kGainskD, forkliftData.kTimeoutMs);
        // Set acceleration and vcruise velocity - see documentation
		this.forkliftLeft.configMotionCruiseVelocity(forkliftData.cruiseVelocity.data, forkliftData.kTimeoutMs);
		this.forkliftLeft.configMotionAcceleration(forkliftData.acceleration.data, forkliftData.kTimeoutMs);
		this.forkliftRight.configMotionCruiseVelocity(forkliftData.cruiseVelocity.data, forkliftData.kTimeoutMs);
		this.forkliftRight.configMotionAcceleration(forkliftData.acceleration.data, forkliftData.kTimeoutMs);
        ResetEncoders()
    }

    fun ResetEncoders()
    {
        forkliftLeft.setSelectedSensorPosition(0, forkliftData.kPIDLoopIdx, forkliftData.kTimeoutMs)
        forkliftRight.setSelectedSensorPosition(0, forkliftData.kPIDLoopIdx, forkliftData.kTimeoutMs)
    }
    
    fun lift(speed: Double) 
    {
        forkliftLeft.set(ControlMode.PercentOutput, speed)
        forkliftRight.set(ControlMode.PercentOutput, speed)
    }

    fun manualLift(inputValue: Double) { lift(inputValue) }

    fun deployForks()
    {
        // do something
    }

    /*fun forkliftMM(state: Boolean)
    {
        forkliftState = state
        if (forkliftState == false)
        {
            forkliftLeft.set(ControlMode.MotionMagic, forkliftData.height)
            forkliftRight.set(ControlMode.MotionMagic, forkliftData.height)
        }
        if (forkliftState == true)
        {
            forkliftLeft.set(ControlMode.MotionMagic, 0.0)
            forkliftRight.set(ControlMode.MotionMagic, 0.0)
        }
    }*/

    fun getEncoderRawLeftForklift(): Int { return forkliftLeft.getSelectedSensorPosition(0); }
    fun getEncoderRawRightForklift(): Int { return forkliftRight.getSelectedSensorPosition(0); }

    val defautCommand = ForkliftJoystick()
}
