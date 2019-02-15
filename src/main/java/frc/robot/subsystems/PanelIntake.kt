package frc.robot.subsystems

import org.sertain.command.Subsystem
import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.DoubleSolenoid.Value
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.Timer
//import frc.robot.IDs
import frc.robot.data.PanelIntakeData

public object PanelIntake: Subsystem()
{
     val panelIntakeData : PanelIntakeData = PanelIntakeData()
     var solenoidTop: DoubleSolenoid = DoubleSolenoid(panelIntakeData.soleniodTopInPort, panelIntakeData.soleniodTopOutPort)
     var solenoidBottom: DoubleSolenoid = DoubleSolenoid(panelIntakeData.soleniodBottomInPort, panelIntakeData.soleniodBottomOutPort)
     
     
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
          if (panelIntakeData.isDeployed)
          {
               solenoidTop.set(Value.kReverse) 
               solenoidBottom.set(Value.kReverse)
               panelIntakeData.isDeployed = !panelIntakeData.isDeployed
		}
		else 
          {
			solenoidTop.set(Value.kForward) 
               solenoidBottom.set(Value.kForward) 
			panelIntakeData.isDeployed = !panelIntakeData.isDeployed
          }
     }

    fun getRBrakeStatus(): Boolean { return panelIntakeData.isDeployed; }
}
