package frc.robot.subsystems

import org.sertain.command.Subsystem
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.*
import com.ctre.phoenix.motorcontrol.FeedbackDevice.*
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import frc.robot.data.ArmData
import frc.robot.commands.Arm.ArmJoystick
import frc.robot.Util.AlbanyTestFile
import edu.wpi.first.wpilibj.PIDController
import frc.robot.Util.AlbanyTestPidLoops.*


public object Arm : Subsystem()
{ 
    val armData : ArmData = ArmData()
    val armMotor: WPI_TalonSRX = WPI_TalonSRX(armData.motor)   
    //PID Loop
    var turnController: PIDController = PIDController(albanyTestFile.pidPArm, albanyTestFile.pidIArm, albanyTestFile.pidDArm, albanyTestFile.pidFArm, ArmPidSource , ArmPidWrite , 0.05)

    //MotionMagic
    var armTargetPosistionJoystick: Double = 0.0
    override fun onCreate()
    {
        armMotor.configFactoryDefault()
        armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, armData.kPIDLoopIdx, armData.kTimeoutMs)

        armMotor.setNeutralMode(NeutralMode.Brake)
        armMotor.setSensorPhase(true)
        armMotor.setInverted(false)
        /* Set relevant frame periods to be at least as fast as periodic rate */
		armMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, armData.kTimeoutMs);
	    armMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, armData.kTimeoutMs);
        // Set the peak and nominal voltage outputs- requires adjustment
		armMotor.configNominalOutputForward(0.0, armData.kTimeoutMs);
		armMotor.configNominalOutputReverse(0.0, armData.kTimeoutMs);
		armMotor.configPeakOutputForward(1.0, armData.kTimeoutMs);
		armMotor.configPeakOutputReverse(-1.0, armData.kTimeoutMs);
        /* Set Motion Magic gains in slot kSlotIdx */
		armMotor.selectProfileSlot(armData.leftKSlotIdx, armData.kPIDLoopIdx);
		armMotor.config_kF(armData.leftKSlotIdx, armData.kGainskF, armData.kTimeoutMs);
		armMotor.config_kP(armData.leftKSlotIdx, armData.kGainskP, armData.kTimeoutMs);
		armMotor.config_kI(armData.leftKSlotIdx, armData.kGainskI, armData.kTimeoutMs);
		armMotor.config_kD(armData.leftKSlotIdx, armData.kGainskD, armData.kTimeoutMs);
        armMotor.configMotionCruiseVelocity(armData.cruiseVelocity)
        armMotor.configMotionAcceleration(armData.acceleration)
        
        ResetEncoder()
    }

    fun ResetEncoder(){ armMotor.setSelectedSensorPosition(0, armData.kPIDLoopIdx, armData.kTimeoutMs) }

    fun Arm()
    {
        //current limiting
        armMotor.configContinuousCurrentLimit(25,0) // desired current after limit
        armMotor.configPeakCurrentLimit(24,0)//max current
        armMotor.configPeakCurrentDuration(100,0)  // how long after max current to be limited (ms)
        armMotor.enableCurrentLimit(true) 
    }

    fun move(speed: Double)
    {
        if (albanyTestFile.armMotionMagicJoystickEnabled)
        {
            armTargetPosistionJoystick = armTargetPosistionJoystick+speed* albanyTestFile.joystickArmDx
            armMotor.set(ControlMode.MotionMagic, armTargetPosistionJoystick)
        }
        else
            armMotor.set(ControlMode.PercentOutput, speed) 
    }
    fun killMotors(){ armMotor.set(0.0) }

    //elevator Motion Magic
    fun armMotionMagic (newArmState: String)
    {
        var targetPos = newArmState
        when(targetPos)
        {
            "Top" -> {
                armMotor.set(ControlMode.MotionMagic, armData.topHeight.data)
                armData.armState = "Top"
            }
            "Middle" -> {
                armMotor.set(ControlMode.MotionMagic, armData.middleHeight.data)
                armData.armState = "Middle"
            }
            "Bottom" -> {
                armMotor.set(ControlMode.MotionMagic, armData.bottomHeight.data)
                armData.armState = "Bottom"
            }
            "Scoring" -> {
                armMotor.set(ControlMode.MotionMagic, armData.scoringHeight.data) 
                armData.armState = "Scoring"
            }
        } 
    }

    // Returning the state the arm is in 
    fun returnArmState(): String { return armData.armState; }

    // Functions for getting encoder values
    fun getEncoderRawArm(): Double { return armMotor.getSelectedSensorPosition(0).toDouble(); }

    override val defaultCommand = ArmJoystick()
}
