package frc.robot.subsystems

import org.sertain.command.Subsystem
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.*
import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.DoubleSolenoid.Value
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.DigitalInput

import frc.robot.Mag
import frc.robot.IDs
import frc.robot.commands.RBrake.RBrakeSlave
import frc.robot.OI
import frc.robot.data.RBrakeData

public object RBrake : Subsystem()
{
    // Variables/Objects
    var rBrakeData: RBrakeData = RBrakeData()

    var deploySolenoid: DoubleSolenoid = DoubleSolenoid(rBrakeData.solenoid[0], rBrakeData.solenoid[1])
    val rBrakeMotor: WPI_TalonSRX = WPI_TalonSRX(rBrakeData.motor)
    var isDeployed: Boolean = true
    var antiMode: Boolean = false
  
    override fun onCreate()
    {
        rBrakeMotor.configFactoryDefault()
        rBrakeMotor.configContinuousCurrentLimit(40,0) // Desired current after limit
		rBrakeMotor.configPeakCurrentLimit(35, 0) // Max current
		rBrakeMotor.configPeakCurrentDuration(100, 0) // How long after max current to be limited (ms)
		rBrakeMotor.enableCurrentLimit(true)
    }

    fun rBrake() { resetEncoders() }

    fun driveRBrake(pow: Double) { rBrakeMotor.set(pow) }
    
    fun deployIn()
    { 
        deploySolenoid.set(Value.kForward) 
        isDeployed = false
    }
    fun deployOut()
    { 
        deploySolenoid.set(Value.kReverse) 
        isDeployed = true
    }

    fun deploy()
    {
        if (isDeployed)
        {
			deploySolenoid.set(Value.kReverse)
            //secondarySolenoid.set(Value.kReverse)
			isDeployed = !isDeployed
		}
		else 
        {
			deploySolenoid.set(Value.kForward)
            //secondarySolenoid.set(Value.kForward)
			isDeployed = !isDeployed
        }
    }

    fun antilockModeEnable()
    {  
        antiMode = true
        runAntilockMode()
    }
    fun antilockModeDisable(){ antiMode = false }

    fun runAntilockMode()
    {
        var startTime = System.currentTimeMillis()

        if (antiMode && isDeployed == false)
            deployOut()

        when(antiMode)
        {
            true -> { driveRBrake(1.0)
                if (System.currentTimeMillis() - startTime > 0.05 ) 
                {// arbitrary delay; needs testing
                    killMotors()
                }
            }
        }

        if (antiMode == false)
        {
            killMotors()
        }
    }

    /* fun isLimitSwitchTriggered(): Boolean
    {
        return limitSwitch.get()
    }*/
    

    fun killMotors() { rBrakeMotor.set(0.0) }
    fun resetMotorPositions() { rBrakeMotor.set(0.0) }
    fun resetEncoders() { this.rBrakeMotor.setSelectedSensorPosition(0, 0, 0) }
    fun getEncoder(): Int { return rBrakeMotor.getSelectedSensorPosition(0); }
    fun getSpeed(): Double { return rBrakeMotor.get(); }
    fun getAntilockMode(): Boolean { return antiMode }
    fun getRBrakeStatus(): Boolean { return isDeployed }
    
    
    override val defaultCommand = RBrakeSlave()
}