package frc.robot.Util


public data class AlbanyTestFile(val patchIsCool: Boolean = true)
{
    //Wrist
   val pidPWrist: Double = 0.05
   val pidIWrist: Double = 0.0
   val pidDWrist: Double = 0.0
   val pidFWrist: Double = 10.0
   val wristEncoderPidLoopEnabled = false
   


   val wristMotionMagicJoystickEnabled = false
   val joystickWristDx = 10

   val cruiseVelocityWrist = 19000
   val accelerationWrist = 11000

   //Arm
   val pidPArm: Double = 0.05
   val pidIArm: Double = 0.0
   val pidDArm: Double = 0.0
   val pidFArm: Double = 10.0
   val armEncoderPidLoopEnabled = false


   val armMotionMagicJoystickEnabled = false
   val joystickArmDx = 10

   val cruiseVelocityArm = 19000
   val accelerationArm = 11000


   //Elevator --MM Code Done, Manual needs work

   val pidPElevator: Double = 0.05
   val pidIElevator: Double = 0.0
   val pidDElevator: Double = 0.0
   val pidFElevator: Double = 10.0  

   //Manual PId Braking --- WIP needs controller output to be linked to motor
   val elevatorEncoderPidLoopEnable = false


    // Motion Magic ---WIP needs testing
   val elevatorMotionMagicJoystickEnable = false
   val joystickElevatorDx = 10

   val cruiseVelocityElevator = 19000
   val accelerationElevator = 11000


}