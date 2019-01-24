package frc.robot.subsystems

import org.sertain.command.Subsystem
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.*
import com.ctre.phoenix.motorcontrol.FeedbackDevice.*
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import com.kauailabs.navx.frc.AHRS


import frc.robot.IDs
import frc.robot.commands.Elevator.ElevatorJoystick

public object Elevator : Subsystem()
 {
    
    //importing ids
    val ids: IDs = IDs()
    val elevatorLeft: WPI_TalonSRX = WPI_TalonSRX((ids.elevatorMotorIDs.get("Left"))?: 5) //temp    
    val elevatorRight: WPI_TalonSRX = WPI_TalonSRX((ids.elevatorMotorIDs.get("Right"))?: 6) //temp

    //setting controller deadzone
    var deadzone: Double = 0.1

    // setting default command
    val defautCommand = ElevatorJoystick()

    //configuring motion magic
    var cruiseVelocity: Double = 19000.0  //temp
    var acceleration: Double = 11000.0  //temp
    var topHeight: Double = 72000.0 //temp
    var middleHeight: Double = 35000.0//temp
    var bottomHeight: Double = 0.0//temp

    //configuring PID Loop for motion magic to do- move to IDS
    var kPIDLoopIdx: Int = 0
    var kTimeoutMs: Int = 0
    var rightKSlotIdx: Int = 0
    var leftKSlotIdx: Int = 0
    var kGainskF: Double = 0.0
    var kGainskP: Double = 0.0
    var kGainskI: Double = 0.0
    var kGainskD: Double = 0.0

    //var elevatorState: Boolean = true //elevator starts in down position
    var elevatorState: String = "Bottom"

    //setting allowable leveling limit on the elevator
    var allowableLevelError: Double = 20.0
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

        //sets Talons to hold posistion when pow = 0
        this.elevatorLeft.setNeutralMode(NeutralMode.Brake)
        this.elevatorRight.setNeutralMode(NeutralMode.Brake)
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
        // Set the peak and nominal voltage outputs- requires adjustment
		this.elevatorRight.configNominalOutputForward(0.0, kTimeoutMs);
		this.elevatorRight.configNominalOutputReverse(0.0, kTimeoutMs);
		this.elevatorRight.configPeakOutputForward(1.0, kTimeoutMs);
		this.elevatorRight.configPeakOutputReverse(-1.0, kTimeoutMs);
		this.elevatorLeft.configNominalOutputForward(0.0, kTimeoutMs);
		this.elevatorLeft.configNominalOutputReverse(0.0, kTimeoutMs);
		this.elevatorLeft.configPeakOutputForward(1.0, kTimeoutMs);
		this.elevatorLeft.configPeakOutputReverse(-1.0, kTimeoutMs);
        /* Set Motion Magic gains in slot kSlotIdx */
		this.elevatorRight.selectProfileSlot(rightKSlotIdx, kPIDLoopIdx);
		this.elevatorRight.config_kF(rightKSlotIdx, kGainskF, kTimeoutMs);
		this.elevatorRight.config_kP(rightKSlotIdx, kGainskP, kTimeoutMs);
		this.elevatorRight.config_kI(rightKSlotIdx, kGainskI, kTimeoutMs);
		this.elevatorRight.config_kD(rightKSlotIdx, kGainskD, kTimeoutMs);
		this.elevatorLeft.selectProfileSlot(leftKSlotIdx, kPIDLoopIdx);
		this.elevatorLeft.config_kF(leftKSlotIdx, kGainskF, kTimeoutMs);
		this.elevatorLeft.config_kP(leftKSlotIdx, kGainskP, kTimeoutMs);
		this.elevatorLeft.config_kI(leftKSlotIdx, kGainskI, kTimeoutMs);
		this.elevatorLeft.config_kD(leftKSlotIdx, kGainskD, kTimeoutMs);

        ResetEnconder()
    }

    fun ResetEnconder()
    {
        elevatorLeft.setSelectedSensorPosition(0, kPIDLoopIdx, kTimeoutMs)
        elevatorRight.setSelectedSensorPosition(0, kPIDLoopIdx, kTimeoutMs)
    }
    fun elevator()
    {
        //current limiting
        this.elevatorLeft.configContinuousCurrentLimit(40,0) // desired current after limit
        this.elevatorLeft.configPeakCurrentLimit(35,0)//max current
        this.elevatorLeft.configPeakCurrentDuration(100,0)  // how long after max current to be limited (ms)
        this.elevatorLeft.enableCurrentLimit(true) 
        this.elevatorRight.configContinuousCurrentLimit(40,0) // desired current after limit
        this.elevatorRight.configPeakCurrentLimit(35,0)//max current
        this.elevatorRight.configPeakCurrentDuration(100,0)  // how long after max current to be limited (ms)
        this.elevatorRight.enableCurrentLimit(true)  
    }
    //lifting the elevator as a single entity
    fun lift(speedLeft: Double = 1.0, speedRight: Double = 1.0) 
    {
        elevatorLeft.set(ControlMode.PercentOutput, speedLeft)
        elevatorRight.set(ControlMode.PercentOutput, speedRight)
    }
    //joystick input function
    /*  fun manualLift(inputValue: Double)
    {
        lift(inputValue,inputValue)
    }
    */
    //checking to see if the elevator is even or not- not yet implemented
    /* // checking to see if elevator is moving due to Motion Magic-not implemented yet
    fun isElevatorInMM ():Boolean 
    {
        return false
    }
    */
    //elevator Motion Magic
    fun elevatorMM (newElevatorState: String = "Null")
    {
        var targetPos = newElevatorState
        if(targetPos=="Bottom" && targetPos != elevatorState )
        {
            elevatorLeft.set(ControlMode.MotionMagic, bottomHeight)
            elevatorRight.set(ControlMode.MotionMagic, bottomHeight)
            elevatorState="Bottom"
        }
        if(targetPos=="Middle" && targetPos !=elevatorState)
        {
            elevatorLeft.set(ControlMode.MotionMagic, middleHeight)
            elevatorRight.set(ControlMode.MotionMagic, middleHeight)
            elevatorState="Middle"
        }
        if(targetPos=="Top" && targetPos != elevatorState)
        {
            elevatorLeft.set(ControlMode.MotionMagic, topHeight)
            elevatorRight.set(ControlMode.MotionMagic, topHeight)
            elevatorState="Bottom"
        }

        /* elevatorState = state
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
        */
        
    }
    fun isElevatorLevel():Boolean
    {
        if (getElevatorError()>allowableLevelError){
            return false
        }
        return true
    }
     //returning the state the elevator is in or was in last 
    fun whatIsElevatorState ():String{return elevatorState}
    // finding the error in the elevator leveling pos->left is too high neg->right is to high
    fun getElevatorError():Int{return elevatorLeft.getSelectedSensorPosition()-elevatorRight.getSelectedSensorPosition()}
    //functions for getting encoder values
    fun getEncoderRawLeftelevator(): Int { return elevatorLeft.getSelectedSensorPosition(0); }
    fun getEncoderRawRightelevator(): Int { return elevatorRight.getSelectedSensorPosition(0); }
}
