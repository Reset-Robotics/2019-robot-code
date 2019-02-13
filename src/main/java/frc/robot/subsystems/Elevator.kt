package frc.robot.subsystems

import org.sertain.command.Subsystem
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.*
import com.ctre.phoenix.motorcontrol.FeedbackDevice.*
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.DigitalInput

import frc.robot.data.ElevatorData
import frc.robot.IDs
import frc.robot.commands.Elevator.ElevatorJoystick

public object Elevator : Subsystem()
 {
    val elevatorData: ElevatorData = ElevatorData()
    
    //importing ids
    val elevatorLeft: WPI_TalonSRX = WPI_TalonSRX((IDs().elevatorMotorIDs.get("Left")) ?: 1) //temp    
    val elevatorRight: WPI_TalonSRX = WPI_TalonSRX((IDs().elevatorMotorIDs.get("Right")) ?: 3) //temp
    //val elevatorLeft: WPI_TalonSRX = WPI_TalonSRX(1) //temp    
    //val elevatorRight: WPI_TalonSRX = WPI_TalonSRX(3) //temp

    //setting controller deadzone
    var deadzone: Double = 0.1

    // setting default command
    override val defaultCommand = ElevatorJoystick()

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
    var leftKSlotIdx: Int = 1
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
        elevatorLeft.configFactoryDefault()
        elevatorRight.configFactoryDefault()
        
        //set up for encoders
        //elevatorLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
											//kPIDLoopIdx, 
											//kTimeoutMs)
        //elevatorRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
											//kPIDLoopIdx, 
											//kTimeoutMs)

        //sets Talons to hold posistion when pow = 0
        elevatorLeft.setNeutralMode(NeutralMode.Brake)
        elevatorRight.setNeutralMode(NeutralMode.Brake)
        /*
		 * Configure Talon SRX Output and Sesnor direction accordingly
		 * Invert Motor to have green LEDs when driving Talon Forward / Requesting Postiive Output
		 * Phase sensor to have positive increment when driving Talon Forward (Green LED)
		 */
        elevatorLeft.setSensorPhase(true)
        elevatorRight.setSensorPhase(false)
        elevatorLeft.setInverted(false)
        elevatorRight.setInverted(true)
        /* Set relevant frame periods to be at least as fast as periodic rate */
		elevatorLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, kTimeoutMs);
	    elevatorLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);
		elevatorRight.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, kTimeoutMs);
	    elevatorRight.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);
        // Set the peak and nominal voltage outputs- requires adjustment
		elevatorRight.configNominalOutputForward(0.0, kTimeoutMs);
		elevatorRight.configNominalOutputReverse(0.0, kTimeoutMs);
		elevatorRight.configPeakOutputForward(1.0, kTimeoutMs);
		elevatorRight.configPeakOutputReverse(-1.0, kTimeoutMs);
		elevatorLeft.configNominalOutputForward(0.0, kTimeoutMs);
		elevatorLeft.configNominalOutputReverse(0.0, kTimeoutMs);
		elevatorLeft.configPeakOutputForward(1.0, kTimeoutMs);
		elevatorLeft.configPeakOutputReverse(-1.0, kTimeoutMs);
        /* Set Motion Magic gains in slot kSlotIdx */
		elevatorRight.selectProfileSlot(rightKSlotIdx, kPIDLoopIdx);
		elevatorRight.config_kF(rightKSlotIdx, kGainskF, kTimeoutMs);
		elevatorRight.config_kP(rightKSlotIdx, kGainskP, kTimeoutMs);
		elevatorRight.config_kI(rightKSlotIdx, kGainskI, kTimeoutMs);
		elevatorRight.config_kD(rightKSlotIdx, kGainskD, kTimeoutMs);
		elevatorLeft.selectProfileSlot(leftKSlotIdx, kPIDLoopIdx);
		elevatorLeft.config_kF(leftKSlotIdx, kGainskF, kTimeoutMs);
		elevatorLeft.config_kP(leftKSlotIdx, kGainskP, kTimeoutMs);
		elevatorLeft.config_kI(leftKSlotIdx, kGainskI, kTimeoutMs);
		elevatorLeft.config_kD(leftKSlotIdx, kGainskD, kTimeoutMs);
        
        ResetEncoders()
    }

    fun ResetEncoders()
    {
        elevatorLeft.setSelectedSensorPosition(0, kPIDLoopIdx, kTimeoutMs)
        elevatorRight.setSelectedSensorPosition(0, kPIDLoopIdx, kTimeoutMs)
    }
    fun elevator()
    {
        
        //current limiting
        elevatorLeft.configContinuousCurrentLimit(40,0) // desired current after limit
        elevatorLeft.configPeakCurrentLimit(35,0)//max current
        elevatorLeft.configPeakCurrentDuration(100,0)  // how long after max current to be limited (ms)
        elevatorLeft.enableCurrentLimit(true) 
        elevatorRight.configContinuousCurrentLimit(40,0) // desired current after limit
        elevatorRight.configPeakCurrentLimit(35,0)//max current
        elevatorRight.configPeakCurrentDuration(100,0)  // how long after max current to be limited (ms)
        elevatorRight.enableCurrentLimit(true)  
    }
    //lifting the elevator as a single entity
    fun lift(speedLeft: Double, speedRight: Double) 
    {
        var localSpeedLeft = speedLeft
        var localSpeedRight = speedRight
        elevatorLeft.set(ControlMode.PercentOutput, localSpeedLeft)
        elevatorRight.set(ControlMode.PercentOutput, localSpeedRight)
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
