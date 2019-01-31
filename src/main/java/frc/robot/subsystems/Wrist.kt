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
    val wristMotor: WPI_TalonSRX = WPI_TalonSRX(IDs().wristMotorIDs.get("Main")!!) //temp    
    //setting controller deadzone
    var deadzone: Double = 0.1


    //configuring motion magic
    var cruiseVelocity: Double = 19000.0  //temp
    var acceleration: Double = 11000.0  //temp
    var topHeightPanel: Double = 72000.0 //temp
    var middleHeightPanel: Double = 35000.0//temp
    var bottomHeightPanel: Double = 0.0//temp
    var topHeightCargo: Double = 72000.0 //temp
    var middleHeightCargo: Double = 35000.0//temp
    var bottomHeightCargo: Double = 0.0//temp

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
    fun wristMotionMagic (newWristState: String = "Null")
    {
        var targetPos = newWristState
        if (intakeType) //panel
        {
            if(targetPos=="Bottom" && targetPos != wristState )
            {
                wristMotor.set(ControlMode.MotionMagic, bottomHeightPanel)
                wristMotor.set(ControlMode.MotionMagic, bottomHeightPanel)
                wristState="Bottom"
            }
            if(targetPos=="Middle" && targetPos !=wristState)
            {
                wristMotor.set(ControlMode.MotionMagic, middleHeightPanel)
                wristMotor.set(ControlMode.MotionMagic, middleHeightPanel)
                wristState="Middle"
            }
            if(targetPos=="Top" && targetPos != wristState)
            {
                wristMotor.set(ControlMode.MotionMagic, topHeightPanel)
                wristMotor.set(ControlMode.MotionMagic, topHeightPanel)
                wristState="Bottom"
            }     
        }
        else  //cargo
        {
            if(targetPos=="Bottom" && targetPos != wristState )
            {
                wristMotor.set(ControlMode.MotionMagic, bottomHeightCargo)
                wristMotor.set(ControlMode.MotionMagic, bottomHeightCargo)
                wristState="Bottom"
            }
            if(targetPos=="Middle" && targetPos !=wristState)
            {
                wristMotor.set(ControlMode.MotionMagic, middleHeightCargo)
                wristMotor.set(ControlMode.MotionMagic, middleHeightCargo)
                wristState="Middle"
            }
            if(targetPos=="Top" && targetPos != wristState)
            {
                wristMotor.set(ControlMode.MotionMagic, topHeightCargo)
                wristMotor.set(ControlMode.MotionMagic, topHeightCargo)
                wristState="Bottom"
            }    
        }
    }

    fun toggleIntakeType() : Boolean
    {
        intakeType = !intakeType
        return intakeType
    }
    // Returning the state the arm is in 
    fun returnWristState(): String { return wristState; }

    // Functions for getting encoder values
    fun getEncoderRawWrist(): Int { return wristMotor.getSelectedSensorPosition(0); }

    override val defaultCommand = WristJoystick()
}