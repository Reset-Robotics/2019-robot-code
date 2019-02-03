package frc.robot.subsystems

import org.sertain.command.Subsystem

public object LEDs : Subsystem() {

  enum class Options(val value: Int){
    default(0),
    forkliftDeployed(1),
    elevatorLimitReached(2),
    cargointakeReady(3)
    pannelIntakeReady(4)
  }

  void sendToArduino(argument: Int){
    // SEND TO ARDUINO
  }
}
