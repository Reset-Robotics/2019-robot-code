package frc.robot.commands.Drive

import org.sertain.command.Command
import frc.robot.subsystems.Drivetrain
import frc.robot.OI
import edu.wpi.first.wpilibj.PIDController
import edu.wpi.first.wpilibj.PIDOutput

//public object distanceControllerY : PIDController(0.006, 0.0, 0.0, Drivetrain.navx, distanceControllerX, 0.50)
public object distanceControllerY : PIDOutput
{
    val pidValP: Double = 0.006
    val pidValI: Double = 0.0
    val pidValD: Double = 0.0
    val pidValF: Double = 0.0
    var cycleTime: Double = 0.05
    var throttle: Double = 1.0
    var distanceControllerY: PIDController = PIDController(pidValP, pidValI, pidValD, pidValF, Drivetrain.navx, this, cycleTime)
    //public var throttle: Double = 0.0

    override fun pidWrite(output: Double){ throttle = output }

}
//very sketchy do not use