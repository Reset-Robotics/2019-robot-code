package frc.robot.subsystems

import org.sertain.command.Subsystem
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.*
import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.Timer

import frc.robot.Mag
import frc.robot.IDs
import frc.robot.commands.RBrake.RBrakeSlave
import frc.robot.OI


public object RBrake : Subsystem()
{
    // Constants 
    val ids: IDs = IDs()
    
    // Local instance of OI
    val oi: OI = OI()

    // Variables/Objects
    var deploySolenoid: DoubleSolenoid = DoubleSolenoid(ids.rBrakeSolenoid[0], ids.rBrakeSolenoid[1])
    var secondarySolenoid: DoubleSolenoid = DoubleSolenoid(ids.rBrakeSolenoid[3], ids.rBrakeSolenoid[2])
    val rBrakeMotor: WPI_TalonSRX = WPI_TalonSRX(11) // 3
    var isDeployed: Boolean = false 
    val deadzone: Double = ids.deadzones.get("R-Brake") :? 0.1
    

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
        secondarySolenoid.set(Value.kForward)
    }

    fun deployOut()
    {
        deploySolenoid.set(Value.kReverse)
        secondarySolenoid.set(Value.kReverse)
    }

    fun deploy()
    {
        if (isDeployed)
        {
			deploySolenoid.set(Value.kReverse)
            secondarySolenoid.set(Value.kReverse)
			isDeployed = !isDeployed
		}
		else 
        {
			deploySolenoid.set(Value.kForward)
            secondarySolenoid.set(Value.kForward)
			isDeployed = !isDeployed
        }
    }

    fun killMotors() { rBrakeMotor.set(0.0) }
    fun resetMotorPositions() { rBrakeMotor.set(0.0) }
    fun resetEncoders() { this.rBrakeMotor.setSelectedSensorPosition(0, 0, 0) }
    fun getEncoder(): Int { return rBrakeMotor.getSelectedSensorPosition(0); }
    fun getSpeed(): Double { return rBrakeMotor.get(); }
    fun getRBrakeStatus(): Boolean { return isDeployed }
    
    override val defaultCommand = RBrakeSlave()
}