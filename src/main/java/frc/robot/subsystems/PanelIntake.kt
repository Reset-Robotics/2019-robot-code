package frc.robot.subsytems

import org.sertain.command.Subsystem
import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.DoubleSolenoid.Value
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.Timer

public object PanelIntake : Subsytem()
{
     val ids: IDs = IDs()
     var solenoidTop: DoubleSolenoid = DoubleSolenoid(ids.panelIntakeSolenoid[0], ids.panelIntakeSolenoid[1])
     var solenoidBottom: DoubleSolenoid = DoubleSolenoid(ids.panelIntakeSolenoid[2], ids.panelIntakeSolenoid[3])
     var isDeployed: Boolean = false
     
     fun panelIntake() { }
    
     fun deployIn(){ deploySolenoid.set(Value.kForward) }
     fun deployOut(){ deploySolenoid.set(Value.kReverse) }

     fun deploy()
     {
          if (isDeployed)
          {
		     deploySolenoid.set(Value.kReverse)
			isDeployed = !isDeployed
		}
		else 
          {
			deploySolenoid.set(Value.kForward)
			isDeployed = !isDeployed
          }
     }

    fun getRBrakeStatus(): Boolean { return isDeployed; }
}
