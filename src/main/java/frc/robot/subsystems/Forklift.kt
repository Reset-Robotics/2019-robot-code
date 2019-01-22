package frc.robot.subsystems

import org.sertain.command.Subsystem
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.*
import com.ctre.phoenix.motorcontrol.FeedbackDevice*.
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.kauailabs.navx.frc.AHRS
import frc.robot.commands.Forklift.ForkliftJoystick

import frc.robot.IDs

public object Forklift : Subsystem()
 {
    //importing ids
    val ids: IDs = IDs()
    val forkliftLeft: WPI_TalonSRX = WPI_TalonSRX((ids.forkliftMotorIDs.get("Left"))?: 5) //temp    
    val forkliftRight: WPI_TalonSRX = WPI_TalonSRX((ids.forkliftMotorIDs.get("right"))?: 6) //temp

    //setting controller deadzone
    var deadzone: Double = 0.1

    // setting default command
    override val defautCommand = ForkliftJoystick()

    //configuring motion magic
    var cruiseVelocity: Double = 15000 //temp
    var acceleration: Double = 6000   //temp
    var height: Double = 6000  //temp
    
    var forkliftState: Boolean = true //forklift starts in down position

    override fun onCreate()
    {
        //setting up talons to ensure no unexpected behavior
        this.forkliftLeft.configFactoryDefault()
        this.forkliftRight.configFactoryDefault()
        //Set up for encoders
        this.forkliftLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
											Constants.kPIDLoopIdx, 
											Constants.kTimeoutMs);
        this.forkliftRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
											Constants.kPIDLoopIdx, 
											Constants.kTimeoutMs);

        /**
		 * Configure Talon SRX Output and Sesnor direction accordingly
		 * Invert Motor to have green LEDs when driving Talon Forward / Requesting Postiive Output
		 * Phase sensor to have positive increment when driving Talon Forward (Green LED)
		 */
        this.forkliftLeft.setSensorPhase(true)
        this.forkliftRight.setSensorPhase(true)
        this.forkliftLeft.setInverted(false)
        this.forkliftRight.setInverted(false)
        /* Set relevant frame periods to be at least as fast as periodic rate */
		this.forkliftLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
	    this.forkliftLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);
		this.forkliftRight.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
	    this.forkliftRight.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);
        /* Set the peak and nominal outputs */
		this.forkliftRight.configNominalOutputForward(0, Constants.kTimeoutMs);
		this.forkliftRight.configNominalOutputReverse(0, Constants.kTimeoutMs);
		this.forkliftRight.configPeakOutputForward(1, Constants.kTimeoutMs);
		this.forkliftRight.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		this.forkliftLeft.configNominalOutputForward(0, Constants.kTimeoutMs);
		this.forkliftLeft.configNominalOutputReverse(0, Constants.kTimeoutMs);
		this.forkliftLeft.configPeakOutputForward(1, Constants.kTimeoutMs);
		this.forkliftLeft.configPeakOutputReverse(-1, Constants.kTimeoutMs);
        /* Set Motion Magic gains in slot0 - see documentation */
		this.forkliftRight.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
		this.forkliftRight.config_kF(Constants.kSlotIdx, Constants.kGains.kF, Constants.kTimeoutMs);
		this.forkliftRight.config_kP(Constants.kSlotIdx, Constants.kGains.kP, Constants.kTimeoutMs);
		this.forkliftRight.config_kI(Constants.kSlotIdx, Constants.kGains.kI, Constants.kTimeoutMs);
		this.forkliftRight.config_kD(Constants.kSlotIdx, Constants.kGains.kD, Constants.kTimeoutMs);
		this.forkliftLeft.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
		this.forkliftLeft.config_kF(Constants.kSlotIdx, Constants.kGains.kF, Constants.kTimeoutMs);
		this.forkliftLeft.config_kP(Constants.kSlotIdx, Constants.kGains.kP, Constants.kTimeoutMs);
		this.forkliftLeft.config_kI(Constants.kSlotIdx, Constants.kGains.kI, Constants.kTimeoutMs);
		this.forkliftLeft.config_kD(Constants.kSlotIdx, Constants.kGains.kD, Constants.kTimeoutMs);

    }

    fun Forklift()
    {
        /* 
        //current limiting
        this.forkliftLeft.configContinuousCurrentLimit(40,0) // desired current after limit
        this.forkliftLeft.configPeakCurrentLimit(35,0)//max current
        this.forkliftLeft.configPeakCurrentDuration(100,0)  // how long after max current to be limited (ms)
        this.forkliftLeft.enableCurrentLimit(true) 
        this.forkliftRight.configContinuousCurrentLimit(40,0) // desired current after limit
        this.forkliftRight.configPeakCurrentLimit(35,0)//max current
        this.forkliftRight.configPeakCurrentDuration(100,0)  // how long after max current to be limited (ms)
        this.forkliftRight.enableCurrentLimit(true) 

        //set Talon Mode
        this.forkliftLeft.setNeutralMode(NuetralMode.Brake)
        this.forkliftRight.setNeutralMode(NuetralMode.Brake)
          */
        

    }
    
    fun lift(speed: Double)
    {
        forkliftLeft.set(ControlMode.PercentOutput, speed)
        forkliftRight.set(ControlMode.PercentOutput, speed)
    }
    fun manualLift(inputValue: Double)
    {
        lift(inputValue)
    }

    fun forkliftMM (state: Boolean)
    {
        forkliftState = state
        if (forkliftState == false)
        {
            forkliftLeft.set(ControlMode.MotionMagic, height)
            forkliftRight.set(ControlMode.MotionMagic, height)
        }
        if (forkliftState == true)
        {
            forkliftLeft.set(ControlMode.MotionMagic, -height)
            forkliftRight.set(ControlMode.MotionMagic, -height)
        }
    }

    //functions for getting encoder values
    fun getEncoderRawLeftForklift(): Int { return forkliftLeft.getSelectedSensorPosition(0); }
    fun getEncoderRawRightForklift(): Int { return forkliftRight.getSelectedSensorPosition(0); }



}
