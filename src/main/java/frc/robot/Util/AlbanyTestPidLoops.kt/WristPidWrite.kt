package frc.robot.Util.AlbanyTestPidLoops

import edu.wpi.first.wpilibj.PIDOutput
import edu.wpi.first.wpilibj.PIDSource
import edu.wpi.first.wpilibj.PIDSourceType

public object WristPidWrite : PIDOutput
{
    private var PIDOutputLocal: Double = 0.0
    override fun pidWrite(output: Double){PIDOutputLocal = output}

    fun getOutput(): Double {return PIDOutputLocal}

}
