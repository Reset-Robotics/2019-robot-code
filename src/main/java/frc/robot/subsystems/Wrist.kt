package frc.robot.subsystems

import org.sertain.command.Subsystem
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.*
import com.ctre.phoenix.motorcontrol.FeedbackDevice.*
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced


import frc.robot.IDs
import frc.robot.commands.Wrist.WristJoystick

public object Wrist : Subsystem()
{
    val wristMotor: WPI_TalonSRX = WPI_TalonSRX((IDs().wristMotorIDs.get("Main")) ?: 78) //temp    
    //setting controller deadzone
    var deadzone: Double = 0.1

    //configuring motion magic
    data class MotionData(val name: String, val data: Double)
    val cruiseVelocity = MotionData("Cruise-Velocity", 19000.0)
    var acceleration = MotionData("Acceleration", 11000.0)
    var topHeightPanel = MotionData("Panel-Top", 72000.0)
    var middleHeightPanel = MotionData("Panel-Middle", 35000.0)
    var bottomHeightPanel = MotionData("Panel-Bottom", 0.0)
    var topHeightCargo = MotionData("Cargo-Top", 72000.0)
    var middleHeightCargo = MotionData("Cargo-Middle", 35000.0)
    var bottomHeightCargo = MotionData("Cargo-Bottom", 0.0)

    //configuring PID Loop for motion magic to do- move to IDS
    var kPIDLoopIdx: Int = 0
    var kTimeoutMs: Int = 0
    var rightKSlotIdx: Int = 0
    var leftKSlotIdx: Int = 1
    var kGainskF: Double = 0.0
    var kGainskP: Double = 0.0
    var kGainskI: Double = 0.0
    var kGainskD: Double = 0.0 

    var wristState: String = "Bottom"
    var intakeType: Boolean = false //false is ball true is panel
    override fun onCreate()
    {
        wristMotor.configFactoryDefault()
        wristMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, kPIDLoopIdx, kTimeoutMs)

        wristMotor.setNeutralMode(NeutralMode.Brake)
        /*
		 * Configure Talon SRX Output and Sesnor direction accordingly
		 * Invert Motor to have green LEDs when driving Talon Forward / Requesting Postiive Output
		 * Phase sensor to have positive increment when driving Talon Forward (Green LED)
		 */
        wristMotor.setSensorPhase(true)
        wristMotor.setInverted(false)
        /* Set relevant frame periods to be at least as fast as periodic rate */
		wristMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, kTimeoutMs);
	    wristMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);
        // Set the peak and nominal voltage outputs- requires adjustment
		wristMotor.configNominalOutputForward(0.0, kTimeoutMs);
		wristMotor.configNominalOutputReverse(0.0, kTimeoutMs);
		wristMotor.configPeakOutputForward(1.0, kTimeoutMs);
		wristMotor.configPeakOutputReverse(-1.0, kTimeoutMs);
        /* Set Motion Magic gains in slot kSlotIdx */
		wristMotor.selectProfileSlot(leftKSlotIdx, kPIDLoopIdx);
		wristMotor.config_kF(leftKSlotIdx, kGainskF, kTimeoutMs);
		wristMotor.config_kP(leftKSlotIdx, kGainskP, kTimeoutMs);
		wristMotor.config_kI(leftKSlotIdx, kGainskI, kTimeoutMs);
		wristMotor.config_kD(leftKSlotIdx, kGainskD, kTimeoutMs);
        
        ResetEncoder()
    }

    fun ResetEncoder(){ wristMotor.setSelectedSensorPosition(0, kPIDLoopIdx, kTimeoutMs) }

    fun Wrist()
    {
        //current limiting
        wristMotor.configContinuousCurrentLimit(40,0) // desired current after limit
        wristMotor.configPeakCurrentLimit(35,0)//max current
        wristMotor.configPeakCurrentDuration(100,0)  // how long after max current to be limited (ms)
        wristMotor.enableCurrentLimit(true) 
    }

    fun move(speed: Double){ wristMotor.set(ControlMode.PercentOutput, speed) }
    fun killMotors(){ wristMotor.set(0.0) }

    //elevator Motion Magic
    fun wristMotionMagic (newWristState: MotionData)
    {
        var targetPos = newWristState
        when(targetPos.name)
        {
            "Panel-Top" -> {
                wristMotor.set(ControlMode.MotionMagic, topHeightPanel.data)
                wristMotor.set(ControlMode.MotionMagic, topHeightPanel.data)
                wristState = "Panel-Top"
            }
            "Panel-Middle" -> {
                wristMotor.set(ControlMode.MotionMagic, middleHeightPanel.data)
                wristMotor.set(ControlMode.MotionMagic, middleHeightPanel.data)
                wristState = "Panel-Middle"
            }
            "Panel-Bottom" -> {
                wristMotor.set(ControlMode.MotionMagic, bottomHeightPanel.data)
                wristMotor.set(ControlMode.MotionMagic, bottomHeightPanel.data)
                wristState = "Panel-Bottom"
            }
            "Cargo-Top" -> {
                wristMotor.set(ControlMode.MotionMagic, topHeightCargo.data)
                wristMotor.set(ControlMode.MotionMagic, topHeightCargo.data)
                wristState = "Cargo-Top"
            }
            "Cargo-Middle" -> {
                wristMotor.set(ControlMode.MotionMagic, middleHeightCargo.data)
                wristMotor.set(ControlMode.MotionMagic, middleHeightCargo.data)
                wristState = "Cargo-Middle"
            }
            "Cargo-Bottom" -> {
                wristMotor.set(ControlMode.MotionMagic, bottomHeightCargo.data)
                wristMotor.set(ControlMode.MotionMagic, bottomHeightCargo.data)
                wristState = "Cargo-Bottom"
            }
        }  
    }

    // Returning the state the arm is in 
    fun returnWristState(): String { return wristState; }

    // Functions for getting encoder values
    fun getEncoderRawWrist(): Int { return wristMotor.getSelectedSensorPosition(0); }

    override val defaultCommand = WristJoystick()
}