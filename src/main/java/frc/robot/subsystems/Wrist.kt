package frc.robot.subsystems

import org.sertain.command.Subsystem
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.*
import com.ctre.phoenix.motorcontrol.FeedbackDevice.*
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import frc.robot.commands.Wrist.WristJoystick
import frc.robot.data.WristData

public object Wrist : Subsystem()
{
    var wristData: WristData = WristData()
    val wristMotor: WPI_TalonSRX = WPI_TalonSRX(wristData.motor) //temp    

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
        
        ResetEncoder()
    }

    fun ResetEncoder(){ wristMotor.setSelectedSensorPosition(0, wristData.kPIDLoopIdx, wristData.kTimeoutMs) }

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
    fun wristMotionMagic (newWristState: WristData.MMData)
    {
        var targetPos = newWristState
        when(targetPos.name)
        {
            "Panel-Top" -> {
                wristMotor.set(ControlMode.MotionMagic, wristData.topHeightPanel.data)
                wristMotor.set(ControlMode.MotionMagic, wristData.topHeightPanel.data)
                wristData.wristState = "Panel-Top"
            }
            "Panel-Middle" -> {
                wristMotor.set(ControlMode.MotionMagic, wristData.middleHeightPanel.data)
                wristMotor.set(ControlMode.MotionMagic, wristData.middleHeightPanel.data)
                wristData.wristState = "Panel-Middle"
            }
            "Panel-Bottom" -> {
                wristMotor.set(ControlMode.MotionMagic, wristData.bottomHeightPanel.data)
                wristMotor.set(ControlMode.MotionMagic, wristData.bottomHeightPanel.data)
                wristData.wristState = "Panel-Bottom"
            }
            "Cargo-Top" -> {
                wristMotor.set(ControlMode.MotionMagic, wristData.topHeightCargo.data)
                wristMotor.set(ControlMode.MotionMagic, wristData.topHeightCargo.data)
                wristData.wristState = "Cargo-Top"
            }
            "Cargo-Middle" -> {
                wristMotor.set(ControlMode.MotionMagic, wristData.middleHeightCargo.data)
                wristMotor.set(ControlMode.MotionMagic, wristData.middleHeightCargo.data)
                wristData.wristState = "Cargo-Middle"
            }
            "Cargo-Bottom" -> {
                wristMotor.set(ControlMode.MotionMagic, wristData.bottomHeightCargo.data)
                wristMotor.set(ControlMode.MotionMagic, wristData.bottomHeightCargo.data)
                wristData.wristState = "Cargo-Bottom"
            }
        }  
    }

    // Returning the state the arm is in 
    fun returnWristState(): String { return wristData.wristState; }

    // Functions for getting encoder values
    fun getEncoderRawWrist(): Int { return wristMotor.getSelectedSensorPosition(0); }

    override val defaultCommand = WristJoystick()
}