package frc.robot.subsystems

import org.sertain.command.Subsystem
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.*
import com.ctre.phoenix.motorcontrol.FeedbackDevice.*
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import frc.robot.commands.Wrist.WristJoystick
import frc.robot.data.WristData
import edu.wpi.first.wpilibj.PIDController

public object Wrist : Subsystem()
{
    var wristData: WristData = WristData()
    val wristMotor: WPI_TalonSRX = WPI_TalonSRX(wristData.motor) //temp    
    //var isHoldPosistion : Boolean = false

    //PID LOOP
    //var turnController: PIDController = PIDController(albanyTestFile.pidPWrist, albanyTestFile.pidIWrist, albanyTestFile.pidDWrist, albanyTestFile.pidFWrist, WristPidSource , WristPidWrite, 0.05)

    //MotionMagic Joystick 

    var joystickTarget = 0.0


    override fun onCreate()
    {
        wristMotor.configFactoryDefault()
        wristMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, wristData.kPIDLoopIdx, wristData.kTimeoutMs)

        wristMotor.setNeutralMode(NeutralMode.Brake)
        /*
		 * Configure Talon SRX Output and Sesnor direction accordingly
		 * Invert Motor to have green LEDs when driving Talon Forward / Requesting Postiive Output
		 * Phase sensor to have positive increment when driving Talon Forward (Green LED)
		 */
        wristMotor.setSensorPhase(true)
        wristMotor.setInverted(false)
        /* Set relevant frame periods to be at least as fast as periodic rate */
		wristMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, wristData.kTimeoutMs);
	    wristMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, wristData.kTimeoutMs);
        // Set the peak and nominal voltage outputs- requires adjustment
		wristMotor.configNominalOutputForward(0.0, wristData.kTimeoutMs);
		wristMotor.configNominalOutputReverse(0.0, wristData.kTimeoutMs);
		wristMotor.configPeakOutputForward(1.0, wristData.kTimeoutMs);
		wristMotor.configPeakOutputReverse(-1.0, wristData.kTimeoutMs);
        /* Set Motion Magic gains in slot kSlotIdx */
		wristMotor.selectProfileSlot(wristData.leftKSlotIdx, wristData.kPIDLoopIdx);
		wristMotor.config_kF(wristData.leftKSlotIdx, wristData.kGainskF, wristData.kTimeoutMs);
		wristMotor.config_kP(wristData.leftKSlotIdx, wristData.kGainskP, wristData.kTimeoutMs);
		wristMotor.config_kI(wristData.leftKSlotIdx, wristData.kGainskI, wristData.kTimeoutMs);
		wristMotor.config_kD(wristData.leftKSlotIdx, wristData.kGainskD, wristData.kTimeoutMs);
        wristMotor.configMotionCruiseVelocity(wristData.cruiseVelocity)
        wristMotor.configMotionAcceleration(wristData.acceleration)
        //current limiting
        wristMotor.configContinuousCurrentLimit(10,0) // desired current after limit
        wristMotor.configPeakCurrentLimit(15,0)//max current
        wristMotor.configPeakCurrentDuration(100,0)  // how long after max current to be limited (ms)
        wristMotor.enableCurrentLimit(true) 
        
        
        ResetEncoder()
    }

    fun ResetEncoder(){ wristMotor.setSelectedSensorPosition(0, wristData.kPIDLoopIdx, wristData.kTimeoutMs) }

    fun Wrist()
    {
        
    }

    fun move(speed: Double)
    { 
        if (true)
        {
            joystickTarget = joystickTarget + (speed*10)
            wristMotor.set(ControlMode.MotionMagic, joystickTarget)
        }
        else
        {
            wristMotor.set(ControlMode.PercentOutput, speed) 
        }
    }

    fun killMotors(){ wristMotor.set(0.0) }

    //elevator Motion Magic
    fun wristMotionMagic (newWristState: String)
    {
        var targetPos = newWristState
        when(targetPos)
        {
            "TopPanel" -> {
                wristMotor.set(ControlMode.MotionMagic, wristData.topHeightPanel.data)
                wristData.wristState = "TopPanel"
            }
            "MiddlePanel" -> {
                wristMotor.set(ControlMode.MotionMagic, wristData.middleHeightPanel.data)
                wristData.wristState = "MiddlePanel"
            }
            "BottomPanel" -> {
                wristMotor.set(ControlMode.MotionMagic, wristData.bottomHeightPanel.data)
                wristData.wristState = "BottomPanel"
            }
            "FloorPanel" -> {
                wristMotor.set(ControlMode.MotionMagic, wristData.floorPanel.data)
                wristData.wristState = "FloorPanel"
            }
            "TopCargo" -> {
                wristMotor.set(ControlMode.MotionMagic, wristData.topHeightCargo.data)
                wristData.wristState = "TopCargo"
            }
            "MiddleCargo" -> {
                wristMotor.set(ControlMode.MotionMagic, wristData.middleHeightCargo.data)
                wristData.wristState = "MiddleCargo"
            }
            "BottomCargo" -> {
                wristMotor.set(ControlMode.MotionMagic, wristData.bottomHeightCargo.data)
                wristData.wristState = "BottomCargo"
            }
            "CargoshipCargo" -> {
                wristMotor.set(ControlMode.MotionMagic, wristData.cargoshipCargo.data)
                wristData.wristState = "CargoshipCargo"
            }
        }  
    }

    // Returning the state the arm is in 
    fun returnWristState(): String { return wristData.wristState; }

    // Functions for getting encoder values
    fun getEncoderRawWrist(): Double { return (wristMotor.getSelectedSensorPosition(0)).toDouble(); }

    /* 
    fun brake(): Boolean
    {
        if (isHoldPosistion)
        {
            turnController.enable()
            turnController.setSetpoint(getEncoderRawWrist())
            isHoldPosistion = true
        }
        return isHoldPosistion;
    }
    fun unBrake(): Boolean
    {
        isHoldPosistion = false
        return isHoldPosistion;
    }
    */
    

    override val defaultCommand = WristJoystick()
}