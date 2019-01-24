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
    // constants (move local constants to IDS later)
    val ids: IDs = IDs()
    
    // import oi reference
    val oi: OI = OI()

    // variables/objects
    var deploySolenoid: DoubleSolenoid = DoubleSolenoid(ids.rBrakeSolenoid[0], ids.rBrakeSolenoid[1])
    var secondarySolenoid: DoubleSolenoid = DoubleSolenoid(ids.rBrakeSolenoid[3], ids.rBrakeSolenoid[2])
    val rBrakeMotor: WPI_TalonSRX = WPI_TalonSRX(0) // 3
    var isDeployed: Boolean = false 
    val deadzone: Double = 0.1
    

    override fun onCreate()
    {
        rBrakeMotor.configFactoryDefault()
        rBrakeMotor.configContinuousCurrentLimit(40,0) // desired current after limit
		rBrakeMotor.configPeakCurrentLimit(35, 0) // max current
		rBrakeMotor.configPeakCurrentDuration(100, 0) // how long after max current to be limited (ms)
		rBrakeMotor.enableCurrentLimit(true)
    }

    fun rBrake()
    {
        // Set Talon Modes
		
        // Current Limiting

        resetEncoders()
    }

    fun driveRBrake(pow: Double)
    {
        rBrakeMotor.set(pow)
    }
    
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
		//if(deploySolenoid.get() == Value.kForward) 
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

    fun killMotors()
    {
        // set each motor to 0.0 using .set()
    }

    fun resetMotorPositions()
    {
        // set each motor encoder position to 0.0 using .set()
    }

    fun resetEncoders()
    {
        // set each encoder sensor/position to (0, 0, 0) using setSelectedSensorPosition()
    }

    // example for getting raw encoder values
    //fun getEncoderRawFrontLeft(): Int { return driveFrontLeft.getSelectedSensorPosition(0); }

    //  example for getting speed on drive motor
    //fun getSpeedFrontLeft(): Double { return driveFrontLeft.get(); }
    fun getRBrakeStatus(): Boolean
    {
        return isDeployed
    }
    override val defaultCommand = RBrakeSlave()
}