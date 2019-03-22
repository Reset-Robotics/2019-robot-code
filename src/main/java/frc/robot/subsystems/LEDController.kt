package frc.robot.subsystems

import org.sertain.command.Subsystem
import edu.wpi.first.wpilibj.I2C
import frc.robot.Orthus

public object LEDController : Subsystem() 
{
  data class Pattern(val address: Int, val name: String)

  val defaultPattern = Pattern(0, "Default")
  val forkDeployPattern = Pattern(1, "Forklift-Deployed")
  val elevatorLimitPattern = Pattern(2, "Elevator-Limit-Reached")
  val cargoReadyPattern = Pattern(3, "Cargo-Intake-Ready")
  val panelReadyPattern = Pattern(4, "Panel-Intake-Ready")

  fun sendToArduino(pattern: String)
  {
    var patternAddress: Int = 0
    when(pattern)
    {
      "Default" -> patternAddress = defaultPattern.address
      "Forklift-Deployed" -> patternAddress = forkDeployPattern.address
      "Elevator-Limit-Reached" -> patternAddress = elevatorLimitPattern.address
      "Cargo-Intake-Ready" -> patternAddress = cargoReadyPattern.address
      "Panel-Intake-Ready" -> patternAddress = panelReadyPattern.address
    }

    //Mag.arduino.write(63, patternAddress)
  }
}
