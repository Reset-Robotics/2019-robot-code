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
        //current limiting 
        /*this.forkliftLeft.configContinuousCurrentLimit(40,0) // Desired current after limit
        this.forkliftLeft.configPeakCurrentLimit(35,0) // Max current
        this.forkliftLeft.configPeakCurrentDuration(100,0)  // How long after max current to be limited (ms)
        this.forkliftLeft.enableCurrentLimit(true) 
        this.forkliftRight.configContinuousCurrentLimit(40,0)
        this.forkliftRight.configPeakCurrentLimit(35,0)
        this.forkliftRight.configPeakCurrentDuration(100,0)
        this.forkliftRight.enableCurrentLimit(true) */

        //set Talon Mode
        this.forkliftLeft.setNeutralMode(NeutralMode.Brake)
        this.forkliftRight.setNeutralMode(NeutralMode.Brake)
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
