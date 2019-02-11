package frc.robot.commands.Drive

import org.sertain.command.Command
import frc.robot.subsystems.Drivetrain
import frc.robot.OI
import edu.wpi.first.wpilibj.PIDController
import edu.wpi.first.wpilibj.PIDOutput
import frc.robot.Util.PIDSourceX
import frc.robot.Util.PIDWriteX

//public object distanceControllerX : PIDController(0.006, 0.0, 0.0, Drivetrain.navx, distanceControllerX, 0.50)
public object distanceController
{
    val pidValP: Double = 0.006
    val pidValI: Double = 0.0
    val pidValD: Double = 0.0
    val pidValF: Double = 0.0
    var pidSourceX: PIDSourceX = PIDSourceX
    var pidWriteX: PIDWriteX = PIDWriteX
    //var cycleTime: Double = 0.05
    public var throttle: Double = 1.0
    val cycleTime: Double = Math.pow(1.49925037,-9.0).toDouble()
    var distanceControllerX: PIDController = PIDController(pidValP, pidValI, pidValD, pidValF, pidSourceX, pidWriteX, cycleTime)
    //public var throttle: Double = 0.0

    //override fun pidWrite(output: Double) {throttle = output}

    /* fun XPidWrite() = object: PIDOutput
    {
        override fun pidWrite(output: Double) {throttle = output}
    }
    */
}
