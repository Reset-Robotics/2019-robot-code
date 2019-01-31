package frc.robot.subsystems

import org.sertain.command.Subsystem
import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.DoubleSolenoid.Value
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.Timer
import frc.robot.IDs

public object PanelIntake: Subsystem()
{
     var solenoidTop: DoubleSolenoid = DoubleSolenoid(IDs().panelIntakeSolenoid[0], IDs().panelIntakeSolenoid[1])
     var solenoidBottom: DoubleSolenoid = DoubleSolenoid(IDs().panelIntakeSolenoid[2], IDs().panelIntakeSolenoid[3])
     var isDeployed: Boolean = false
     
     fun panelIntake() { }
    
     fun deployIn()
     { 
          solenoidTop.set(Value.kForward) 
          solenoidBottom.set(Value.kForward) 
     }
     fun deployOut()
     {
          solenoidTop.set(Value.kReverse) 
          solenoidBottom.set(Value.kReverse)
     }

     fun deploy()
     {
          if (isDeployed)
          {
               solenoidTop.set(Value.kReverse) 
               solenoidBottom.set(Value.kReverse)
               isDeployed = !isDeployed
		}
		else 
          {
			solenoidTop.set(Value.kForward) 
               solenoidBottom.set(Value.kForward) 
			isDeployed = !isDeployed
          }
     }

    fun getRBrakeStatus(): Boolean { return isDeployed; }
}
