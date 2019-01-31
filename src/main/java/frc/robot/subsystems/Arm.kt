package frc.robot.subsystems

import org.sertain.command.Subsystem
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.*
import com.ctre.phoenix.motorcontrol.FeedbackDevice.*
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced


import frc.robot.IDs
import frc.robot.commands.Arm.ArmJoystick

public object Arm : Subsystem()
{ 
    val armMotor: WPI_TalonSRX = WPI_TalonSRX(IDs().armMotorIDs.get("Main")!!) //temp    
    //setting controller deadzone
    var deadzone: Double = 0.1


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

    var armState: String = "Bottom"

    override fun onCreate()
    {
        armMotor.configFactoryDefault()
        armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, kPIDLoopIdx, kTimeoutMs)

        armMotor.setNeutralMode(NeutralMode.Brake)
        /*
		 * Configure Talon SRX Output and Sesnor direction accordingly
		 * Invert Motor to have green LEDs when driving Talon Forward / Requesting Postiive Output
		 * Phase sensor to have positive increment when driving Talon Forward (Green LED)
		 */
        armMotor.setSensorPhase(true)
        armMotor.setInverted(false)
        /* Set relevant frame periods to be at least as fast as periodic rate */
		armMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, kTimeoutMs);
	    armMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);
        // Set the peak and nominal voltage outputs- requires adjustment
		armMotor.configNominalOutputForward(0.0, kTimeoutMs);
		armMotor.configNominalOutputReverse(0.0, kTimeoutMs);
		armMotor.configPeakOutputForward(1.0, kTimeoutMs);
		armMotor.configPeakOutputReverse(-1.0, kTimeoutMs);
        /* Set Motion Magic gains in slot kSlotIdx */
		armMotor.selectProfileSlot(leftKSlotIdx, kPIDLoopIdx);
		armMotor.config_kF(leftKSlotIdx, kGainskF, kTimeoutMs);
		armMotor.config_kP(leftKSlotIdx, kGainskP, kTimeoutMs);
		armMotor.config_kI(leftKSlotIdx, kGainskI, kTimeoutMs);
		armMotor.config_kD(leftKSlotIdx, kGainskD, kTimeoutMs);
        
        ResetEncoder()
    }

    fun ResetEncoder(){ armMotor.setSelectedSensorPosition(0, kPIDLoopIdx, kTimeoutMs) }

    fun Arm()
    {
        //current limiting
        armMotor.configContinuousCurrentLimit(40,0) // desired current after limit
        armMotor.configPeakCurrentLimit(35,0)//max current
        armMotor.configPeakCurrentDuration(100,0)  // how long after max current to be limited (ms)
        armMotor.enableCurrentLimit(true) 
    }

    fun move(speed: Double){ armMotor.set(ControlMode.PercentOutput, speed) }
    fun killMotors(){ armMotor.set(0.0) }

    //elevator Motion Magic
    fun armMotionMagic (newArmState: String = "Null")
    {
        var targetPos = newArmState
        if(targetPos=="Bottom" && targetPos != armState )
        {
            armMotor.set(ControlMode.MotionMagic, bottomHeight)
            armState="Bottom"
        }
        if(targetPos=="Middle" && targetPos !=armState)
        {
            armMotor.set(ControlMode.MotionMagic, middleHeight)
            armState="Middle"
        }
        if(targetPos=="Top" && targetPos != armState)
        {
            armMotor.set(ControlMode.MotionMagic, topHeight)
            armState="Bottom"
        }     
    }

    // Returning the state the arm is in 
    fun returnArmState(): String { return armState; }

    // Functions for getting encoder values
    fun getEncoderRawArm(): Int { return armMotor.getSelectedSensorPosition(0); }

    override val defaultCommand = ArmJoystick()
}
