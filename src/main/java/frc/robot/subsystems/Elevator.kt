package frc.robot.subsystems

import org.sertain.command.Subsystem
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.*
import com.ctre.phoenix.motorcontrol.FeedbackDevice.*
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import com.kauailabs.navx.frc.AHRS
//import frc.robot.commands.elevator.elevatorJoystick

import frc.robot.IDs

public object Elevator : Subsystem()
 {
    //importing ids
    val ids: IDs = IDs()
    val elevatorLeft: WPI_TalonSRX = WPI_TalonSRX((ids.elevatorMotorIDs.get("Left"))?: 5) //temp    
    val elevatorRight: WPI_TalonSRX = WPI_TalonSRX((ids.elevatorMotorIDs.get("Right"))?: 6) //temp

    //setting controller deadzone
    var deadzone: Double = 0.1

    // setting default command
    //val defautCommand = elevatorJoystick()

    //configuring motion magic
    var cruiseVelocity: Double = 1500.0  //temp
    var acceleration: Double = 6000.0   //temp
    var height: Double = 6000.0  //temp

    //configuring PID Loop for motion magic to do- move to IDS
    var kPIDLoopIdx: Int = 0
    var kTimeoutMs: Int = 0
    var kSlotIdx: Int = 0
    var kGainskF: Double = 0.0
    var kGainskP: Double = 0.0
    var kGainskI: Double = 0.0
    var kGainskD: Double = 0.0




    var elevatorState: Boolean = true //elevator starts in down position

    override fun onCreate()
    {
        //setting up talons to ensure no unexpected behavior
        this.elevatorLeft.configFactoryDefault()
        this.elevatorRight.configFactoryDefault()
        //Set up for encoders
        this.elevatorLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
											kPIDLoopIdx, 
											kTimeoutMs);
        this.elevatorRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
											kPIDLoopIdx, 
											kTimeoutMs);

        /**
		 * Configure Talon SRX Output and Sesnor direction accordingly
		 * Invert Motor to have green LEDs when driving Talon Forward / Requesting Postiive Output
		 * Phase sensor to have positive increment when driving Talon Forward (Green LED)
		 */
        this.elevatorLeft.setSensorPhase(true)
        this.elevatorRight.setSensorPhase(true)
        this.elevatorLeft.setInverted(false)
        this.elevatorRight.setInverted(false)
        /* Set relevant frame periods to be at least as fast as periodic rate */
		this.elevatorLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, kTimeoutMs);
	    this.elevatorLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);
		this.elevatorRight.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, kTimeoutMs);
	    this.elevatorRight.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);
        /* Set the peak and nominal outputs */
		this.elevatorRight.configNominalOutputForward(0.0, kTimeoutMs);
		this.elevatorRight.configNominalOutputReverse(0.0, kTimeoutMs);
		this.elevatorRight.configPeakOutputForward(1.0, kTimeoutMs);
		this.elevatorRight.configPeakOutputReverse(-1.0, kTimeoutMs);
		this.elevatorLeft.configNominalOutputForward(0.0, kTimeoutMs);
		this.elevatorLeft.configNominalOutputReverse(0.0, kTimeoutMs);
		this.elevatorLeft.configPeakOutputForward(1.0, kTimeoutMs);
		this.elevatorLeft.configPeakOutputReverse(-1.0, kTimeoutMs);
        /* Set Motion Magic gains in slot kSlotIdx */
		this.elevatorRight.selectProfileSlot(kSlotIdx, kPIDLoopIdx);
		this.elevatorRight.config_kF(kSlotIdx, kGainskF, kTimeoutMs);
		this.elevatorRight.config_kP(kSlotIdx, kGainskP, kTimeoutMs);
		this.elevatorRight.config_kI(kSlotIdx, kGainskI, kTimeoutMs);
		this.elevatorRight.config_kD(kSlotIdx, kGainskD, kTimeoutMs);
		this.elevatorLeft.selectProfileSlot(kSlotIdx, kPIDLoopIdx);
		this.elevatorLeft.config_kF(kSlotIdx, kGainskF, kTimeoutMs);
		this.elevatorLeft.config_kP(kSlotIdx, kGainskP, kTimeoutMs);
		this.elevatorLeft.config_kI(kSlotIdx, kGainskI, kTimeoutMs);
		this.elevatorLeft.config_kD(kSlotIdx, kGainskD, kTimeoutMs);

        ResetEnconder()
    }

    fun ResetEnconder()
    {
        elevatorLeft.setSelectedSensorPosition(0, kPIDLoopIdx, kTimeoutMs)
        elevatorRight.setSelectedSensorPosition(0, kPIDLoopIdx, kTimeoutMs)
    }
    fun elevator()
    {
        /* 
        //current limiting
        this.elevatorLeft.configContinuousCurrentLimit(40,0) // desired current after limit
        this.elevatorLeft.configPeakCurrentLimit(35,0)//max current
        this.elevatorLeft.configPeakCurrentDuration(100,0)  // how long after max current to be limited (ms)
        this.elevatorLeft.enableCurrentLimit(true) 
        this.elevatorRight.configContinuousCurrentLimit(40,0) // desired current after limit
        this.elevatorRight.configPeakCurrentLimit(35,0)//max current
        this.elevatorRight.configPeakCurrentDuration(100,0)  // how long after max current to be limited (ms)
        this.elevatorRight.enableCurrentLimit(true) 

        //set Talon Mode
        this.elevatorLeft.setNeutralMode(NuetralMode.Brake)
        this.elevatorRight.setNeutralMode(NuetralMode.Brake)
          */
        

    }
    
    fun lift(speed: Double) 
    {
        elevatorLeft.set(ControlMode.PercentOutput, speed)
        elevatorRight.set(ControlMode.PercentOutput, speed)
    }
    fun manualLift(inputValue: Double)
    {
        lift(inputValue)
    }

    fun whatIsElevatorState ():Boolean
    {
        return elevatorState
    }
    fun elevatorMM (state: Boolean)
    {
        elevatorState = state
        if (elevatorState == false)
        {
            elevatorLeft.set(ControlMode.MotionMagic, height)
            elevatorRight.set(ControlMode.MotionMagic, height)
        }
        if (elevatorState == true)
        {
            elevatorLeft.set(ControlMode.MotionMagic, 0.0)
            elevatorRight.set(ControlMode.MotionMagic, 0.0)
        }
    }

    //functions for getting encoder values
    fun getEncoderRawLeftelevator(): Int { return elevatorLeft.getSelectedSensorPosition(0); }
    fun getEncoderRawRightelevator(): Int { return elevatorRight.getSelectedSensorPosition(0); }



}
