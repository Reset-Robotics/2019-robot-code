// Reset Robotics 2019
package frc.robot.Util

// Libraries
import edu.wpi.first.wpilibj.PIDOutput
import edu.wpi.first.wpilibj.PIDSource
import edu.wpi.first.wpilibj.PIDSourceType
// Subsystems
import frc.robot.subsystems.Drivetrain


public object PIDWriteY : PIDOutput
{
    private var PIDOutputLocal: Double = 0.0
    
    override fun pidWrite(output: Double) { PIDOutputLocal = output }

    fun getOutput(): Double = return PIDOutputLocal;
}
