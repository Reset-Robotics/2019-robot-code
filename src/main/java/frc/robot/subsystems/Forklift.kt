package frc.robot.subsystems

import org.sertain.command.Subsystem
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.*
import com.ctre.phoenix.motorcontrol.FeedbackDevice.*
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
//import com.kauailabs.navx.frc.AHRS
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
   val defautCommand = ForkliftJoystick()

    //configuring motion magic
    var cruiseVelocity: Int = 1500 //temp
    var acceleration: Int = 6000  //temp
    var height: Double = 6000.0  //temp

    //configuring PID Loop for motion magic to do- move to IDS
    var kPIDLoopIdx: Int = 0
    var kTimeoutMs: Int = 0
    var kSlotIdx: Int = 0
    var kGainskF: Double = 0.0
    var kGainskP: Double = 0.0
    var kGainskI: Double = 0.0
    var kGainskD: Double = 0.0


    var forkliftState: Boolean = true //forklift starts in down position

    override fun onCreate()
    {
        //setting up talons to ensure no unexpected behavior
        this.forkliftLeft.configFactoryDefault()
        this.forkliftRight.configFactoryDefault()
        //Set up for encoders
        this.forkliftLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
											kPIDLoopIdx, 
											kTimeoutMs);
        this.forkliftRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
											kPIDLoopIdx, 
											kTimeoutMs);

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
		this.forkliftLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, kTimeoutMs);
	    this.forkliftLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);
		this.forkliftRight.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, kTimeoutMs);
	    this.forkliftRight.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);
        /* Set the peak and nominal outputs */
		this.forkliftRight.configNominalOutputForward(0.0, kTimeoutMs);
		this.forkliftRight.configNominalOutputReverse(0.0, kTimeoutMs);
		this.forkliftRight.configPeakOutputForward(1.0, kTimeoutMs);
		this.forkliftRight.configPeakOutputReverse(-1.0, kTimeoutMs);
		this.forkliftLeft.configNominalOutputForward(0.0, kTimeoutMs);
		this.forkliftLeft.configNominalOutputReverse(0.0, kTimeoutMs);
		this.forkliftLeft.configPeakOutputForward(1.0, kTimeoutMs);
		this.forkliftLeft.configPeakOutputReverse(-1.0, kTimeoutMs);
        /* Set Motion Magic gains in slot kSlotIdx */
		this.forkliftRight.selectProfileSlot(kSlotIdx, kPIDLoopIdx);
		this.forkliftRight.config_kF(kSlotIdx, kGainskF, kTimeoutMs);
		this.forkliftRight.config_kP(kSlotIdx, kGainskP, kTimeoutMs);
		this.forkliftRight.config_kI(kSlotIdx, kGainskI, kTimeoutMs);
		this.forkliftRight.config_kD(kSlotIdx, kGainskD, kTimeoutMs);
		this.forkliftLeft.selectProfileSlot(kSlotIdx, kPIDLoopIdx);
		this.forkliftLeft.config_kF(kSlotIdx, kGainskF, kTimeoutMs);
		this.forkliftLeft.config_kP(kSlotIdx, kGainskP, kTimeoutMs);
		this.forkliftLeft.config_kI(kSlotIdx, kGainskI, kTimeoutMs);
		this.forkliftLeft.config_kD(kSlotIdx, kGainskD, kTimeoutMs);
        /* Set acceleration and vcruise velocity - see documentation */
		this.forkliftLeft.configMotionCruiseVelocity(cruiseVelocity, kTimeoutMs);
		this.forkliftLeft.configMotionAcceleration(acceleration, kTimeoutMs);
		this.forkliftRight.configMotionCruiseVelocity(cruiseVelocity, kTimeoutMs);
		this.forkliftRight.configMotionAcceleration(acceleration, kTimeoutMs);
        ResetEnconder()
    }

    fun ResetEnconder()
    {
        forkliftLeft.setSelectedSensorPosition(0, kPIDLoopIdx, kTimeoutMs)
        forkliftRight.setSelectedSensorPosition(0, kPIDLoopIdx, kTimeoutMs)
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

    fun whatIsForkliftState ():Boolean
    {
        return forkliftState
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
            forkliftLeft.set(ControlMode.MotionMagic, 0.0)
            forkliftRight.set(ControlMode.MotionMagic, 0.0)
        }
    }

    //functions for getting encoder values
    fun getEncoderRawLeftForklift(): Int { return forkliftLeft.getSelectedSensorPosition(0); }
    fun getEncoderRawRightForklift(): Int { return forkliftRight.getSelectedSensorPosition(0); }



}
