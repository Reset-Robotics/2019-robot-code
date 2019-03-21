package frc.robot.subsystems

import org.sertain.command.Subsystem
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.*
import com.ctre.phoenix.motorcontrol.FeedbackDevice.*
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import frc.robot.commands.Forklift.ForkliftJoystick
import frc.robot.data.ForkliftData


public object Forklift : Subsystem()
 {
    val forkliftData: ForkliftData = ForkliftData()
    val forkliftLeft: WPI_VictorSPX = WPI_VictorSPX(forkliftData.leftMotor)
    val forkliftRight: WPI_VictorSPX = WPI_VictorSPX(forkliftData.rightMotor)

    fun Forklift()
    {
        //set Talon Mode
        forkliftLeft.setNeutralMode(NeutralMode.Brake)
        forkliftRight.setNeutralMode(NeutralMode.Brake)
    }

    override fun onCreate()
    {
        
        // Setting up talons to ensure no unexpected behavior
        this.forkliftLeft.configFactoryDefault()
        this.forkliftRight.configFactoryDefault()
        
        /**
		 * Configure Talon SRX Output and Sesnor direction accordingly
		 * Invert Motor to have green LEDs when driving Talon Forward / Requesting Postiive Output
		 * Phase sensor to have positive increment when driving Talon Forward (Green LED)
		 */
         
        
    }

    fun ResetEncoders()
    {
        /* 
        forkliftLeft.setSelectedSensorPosition(0, forkliftData.kPIDLoopIdx, forkliftData.kTimeoutMs)
        forkliftRight.setSelectedSensorPosition(0, forkliftData.kPIDLoopIdx, forkliftData.kTimeoutMs)
        */
    }
    
    fun lift(speed: Double) 
    {
        forkliftLeft.set(-speed)
        forkliftRight.set(speed)
        System.out.print(speed)
    }

    fun manualLift(inputValue: Double) { lift(inputValue) }

    fun deployForks()
    {
        // do something
    }

    /*fun forkliftMM(state: Boolean)
    {
        forkliftState = state
        if (forkliftState == false)
        {
            forkliftLeft.set(ControlMode.MotionMagic, forkliftData.height)
            forkliftRight.set(ControlMode.MotionMagic, forkliftData.height)
        }
        if (forkliftState == true)
        {
            forkliftLeft.set(ControlMode.MotionMagic, 0.0)
            forkliftRight.set(ControlMode.MotionMagic, 0.0)
        }
    }*/

    fun getEncoderRawLeftForklift(): Int { return forkliftLeft.getSelectedSensorPosition(0); }
    fun getEncoderRawRightForklift(): Int { return forkliftRight.getSelectedSensorPosition(0); }

    override val defaultCommand = ForkliftJoystick()
}
