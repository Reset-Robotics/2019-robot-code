package frc.robot.subsystems

import org.sertain.command.Subsystem
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.*
import com.ctre.phoenix.motorcontrol.FeedbackDevice.*
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.PIDController

import frc.robot.data.ElevatorData
import frc.robot.commands.Elevator.ElevatorJoystick

public object Elevator : Subsystem()
 {
    val elevatorData: ElevatorData = ElevatorData()
    //PID LOOP
    //var turnController: PIDController = PIDController(elevatorData.pidP, elevatorData.pidI, elevatorData.pidD, elevatorData.pidF, ElevatorPidSource , ElevatorPidWrite , 0.05)
    
    
    //importing ids
    var elevatorLeft: WPI_TalonSRX = WPI_TalonSRX(elevatorData.leftMotor)  
    var elevatorRight: WPI_TalonSRX = WPI_TalonSRX(elevatorData.rightMotor)

    //setting controller deadzone
    var deadzone: Double = 0.1

    // setting default command
    //override val defaultCommand = ElevatorJoystick()

    //configuring motion magic
    val cruiseVelocity = elevatorData.cruiseVelocity.data.toInt()
    val acceleration = elevatorData.acceleration.data.toInt()

    var leftTarget: Double = 0.0
    var rightTarget: Double = 0.0

    //configuring PID Loop for motion magic to do- move to IDS
    val kPIDLoopIdx: Int = 0
    val kTimeoutMs: Int = 0
    val rightKSlotIdx: Int = 0
    val leftKSlotIdx: Int = 0
    val kGainskI: Double = elevatorData.pidI
    val kGainskD: Double = elevatorData.pidD
    val kGainskP: Double = elevatorData.pidP
    val kGainskF: Double = elevatorData.pidF

    //var elevatorState: Boolean = true //elevator starts in down position
    var elevatorState: String = " "

    //setting allowable leveling limit on the elevator
    var allowableLevelError: Double = 20.0
    override fun onCreate()
    {
        //setting up talons to ensure no unexpected behavior
        elevatorLeft.configFactoryDefault()
        elevatorRight.configFactoryDefault()
        
        //set up for encoders
        elevatorLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, kPIDLoopIdx, kTimeoutMs)
        elevatorRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, kPIDLoopIdx, kTimeoutMs)

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
		elevatorLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, kPIDLoopIdx, kTimeoutMs);
	    elevatorLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, kPIDLoopIdx, kTimeoutMs);
		elevatorRight.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, kPIDLoopIdx, kTimeoutMs);
	    elevatorRight.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, kPIDLoopIdx, kTimeoutMs);
        // Set the peak and nominal voltage outputs- requires adjustment
		elevatorRight.configNominalOutputForward(0.0, kTimeoutMs);
		elevatorRight.configNominalOutputReverse(0.0, kTimeoutMs);
		elevatorRight.configPeakOutputForward(12.0, kTimeoutMs);
		elevatorRight.configPeakOutputReverse(-12.0, kTimeoutMs);
		elevatorLeft.configNominalOutputForward(0.0, kTimeoutMs);
		elevatorLeft.configNominalOutputReverse(0.0, kTimeoutMs);
		elevatorLeft.configPeakOutputForward(12.0, kTimeoutMs);
		elevatorLeft.configPeakOutputReverse(-12.0, kTimeoutMs);

        // current limiting
        elevatorLeft.configContinuousCurrentLimit(30,0) // desired current after limit
        elevatorLeft.configPeakCurrentLimit(35,0)//max current
        elevatorLeft.configPeakCurrentDuration(100,0)  // how long after max current to be limited (ms)
        elevatorLeft.enableCurrentLimit(true) 
        elevatorRight.configContinuousCurrentLimit(30,0) // desired current after limit
        elevatorRight.configPeakCurrentLimit(35,0)//max current
        elevatorRight.configPeakCurrentDuration(100,0)  // how long after max current to be limited (ms)
        elevatorRight.enableCurrentLimit(true)  

        /* Set Motion Magic gains in slot kSlotIdx */
		elevatorRight.selectProfileSlot(kPIDLoopIdx, kPIDLoopIdx);
		elevatorRight.config_kF(kPIDLoopIdx, kGainskF, kTimeoutMs);
		elevatorRight.config_kP(kPIDLoopIdx, kGainskP, kTimeoutMs);
		elevatorRight.config_kI(kPIDLoopIdx, kGainskI, kTimeoutMs);
		elevatorRight.config_kD(kPIDLoopIdx, kGainskD, kTimeoutMs);
		elevatorLeft.selectProfileSlot(kPIDLoopIdx, kPIDLoopIdx);
		elevatorLeft.config_kF(kPIDLoopIdx, kGainskF, kTimeoutMs);
		elevatorLeft.config_kP(kPIDLoopIdx, kGainskP, kTimeoutMs);
		elevatorLeft.config_kI(kPIDLoopIdx, kGainskI, kTimeoutMs);
		elevatorLeft.config_kD(kPIDLoopIdx, kGainskD, kTimeoutMs);
        elevatorRight.configMotionCruiseVelocity(cruiseVelocity)
        elevatorLeft.configMotionCruiseVelocity(cruiseVelocity)
        elevatorRight.configMotionAcceleration(acceleration)
        elevatorLeft.configMotionAcceleration(acceleration)

        //limit switches
        elevatorLeft.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector,LimitSwitchNormal.NormallyOpen,1 ) 
        elevatorRight.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector,LimitSwitchNormal.NormallyOpen,1 ) 
        
        elevatorLeft.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector,LimitSwitchNormal.NormallyOpen,0 ) 
        elevatorRight.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector,LimitSwitchNormal.NormallyOpen,0 ) 
        

        //elevatorLeft.clearPositionOnLimitR()
        //elevatorRight.clearPositionOnLimitR()


        ResetEncoders()
    }

    fun ResetEncoders()
    {
        elevatorLeft.setSelectedSensorPosition(0, kPIDLoopIdx, kTimeoutMs)
        elevatorRight.setSelectedSensorPosition(0, kPIDLoopIdx, kTimeoutMs)
        elevatorLeft.clearMotionProfileTrajectories()
        elevatorRight.clearMotionProfileTrajectories()
        elevatorRight.configMotionCruiseVelocity(0)
        elevatorLeft.configMotionCruiseVelocity(0)
        elevatorRight.configMotionAcceleration(0)
        elevatorLeft.configMotionAcceleration(0)
        elevatorLeft.set(ControlMode.MotionMagic, 0.0)
        elevatorRight.set(ControlMode.MotionMagic, 0.0)

    }
    fun elevator()
    {

    }
    //lifting the elevator as a single entity
    fun lift(speedLeft: Double, speedRight: Double) 
    {
        /* 
        val joystickElevatorDx = 1
      
            leftTarget = %leftTarget + speedLeft * joystickElevatorDx
            rightTarget = rightTarget + speedRight * joystickElevatorDx
            elevatorLeft.set(ControlMode.MotionMagic, leftTarget)
            elevatorRight.set(ControlMode.MotionMagic, rightTarget)
            */

      
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
     // checking to see if elevator is moving due to Motion Magic-not implemented yet
    
    fun isElevatorInMM ():Boolean 
    {
        return false
    }
    
    //elevator Motion Magic
    fun elevatorMM (newElevatorState: String = "Null")
    {
        var targetPos = newElevatorState
        elevatorRight.configMotionCruiseVelocity(cruiseVelocity)
        elevatorLeft.configMotionCruiseVelocity(cruiseVelocity)
        elevatorRight.configMotionAcceleration(acceleration)
        elevatorLeft.configMotionAcceleration(acceleration)
        when(targetPos)
        {
            
            "Top" -> {
                elevatorLeft.set(ControlMode.MotionMagic, elevatorData.topHeight.data)
                elevatorRight.set(ControlMode.MotionMagic, elevatorData.topHeight.data)
                elevatorState="Top"
            }
            "TopCargo" -> {
                elevatorLeft.set(ControlMode.MotionMagic, elevatorData.topCargoPos.data)
                elevatorRight.set(ControlMode.MotionMagic, elevatorData.topCargoPos.data)
                elevatorState="TopCargo"
            }      
            "TopPanel" -> {
                elevatorLeft.set(ControlMode.MotionMagic, elevatorData.topPanelPos.data)
                elevatorRight.set(ControlMode.MotionMagic, elevatorData.topPanelPos.data)
                elevatorState="TopPanel"
            }    
            "Middle" -> {
                elevatorLeft.set(ControlMode.MotionMagic, elevatorData.middleHeight.data)
                elevatorRight.set(ControlMode.MotionMagic, elevatorData.middleHeight.data)
                elevatorState="Middle"
            }
            "MiddleCargo" -> {
                elevatorLeft.set(ControlMode.MotionMagic, elevatorData.middleCargoPos.data)
                elevatorRight.set(ControlMode.MotionMagic, elevatorData.middleCargoPos.data)
                elevatorState="MiddleCargo"
            }
            "MiddlePanel" -> {
                elevatorLeft.set(ControlMode.MotionMagic, elevatorData.middlePanelPos.data)
                elevatorRight.set(ControlMode.MotionMagic, elevatorData.middlePanelPos.data)
                elevatorState="MiddlePanel"
            }
            "Bottom" -> {
                elevatorLeft.set(ControlMode.MotionMagic, elevatorData.bottomHeight.data)
                elevatorRight.set(ControlMode.MotionMagic, elevatorData.bottomHeight.data)
                elevatorState="Bottom"
            }
            "BottomCargo" -> {
                elevatorLeft.set(ControlMode.MotionMagic, elevatorData.bottomCargoPos.data)
                elevatorRight.set(ControlMode.MotionMagic, elevatorData.bottomCargoPos.data)
                elevatorState="BottomCargo"
            }
            "BottomPanel" -> {
                elevatorLeft.set(ControlMode.MotionMagic, elevatorData.bottomPanelPos.data)
                elevatorRight.set(ControlMode.MotionMagic, elevatorData.bottomPanelPos.data)
                elevatorState="BottomPanel"
            }
            "CargoshipCargo" -> {
                elevatorLeft.set(ControlMode.MotionMagic, elevatorData.cargoshipCargo.data)
                elevatorRight.set(ControlMode.MotionMagic, elevatorData.cargoshipCargo.data)
                elevatorState="CargoshipCargo"
            }

            else -> return;
        }           
    }
    fun isElevatorLevel():Boolean
    {
        if (getElevatorError()>allowableLevelError){
            return false
        }
        return true
    }

    fun clearTalons()
    {
        elevatorLeft.configFactoryDefault()
        elevatorRight.configFactoryDefault()
    }

     //returning the state the elevator is in or was in last 
    fun whatIsElevatorState ():String{return elevatorState}

    fun setElevatorTargetNull() 
    { 
        elevatorMM() 
        elevatorState = " "    
    }

    // finding the error in the elevator leveling pos->left is too high neg->right is to high
    fun getElevatorError():Int{return elevatorLeft.getSelectedSensorPosition()-elevatorRight.getSelectedSensorPosition()}
    //functions for getting encoder values
    fun getEncoderRawLeftElevator(): Double { return (elevatorLeft.getSelectedSensorPosition(0)).toDouble(); }
    fun getEncoderRawRightElevator(): Double { return (elevatorRight.getSelectedSensorPosition(0)).toDouble(); }
    fun getAverageEncoderPosistion(): Double { return ((getEncoderRawLeftElevator()-getEncoderRawLeftElevator())/2.0)}

    fun killMotors()
    {
        elevatorLeft.set(0.0)
        elevatorRight.set(0.0)
    }
}
