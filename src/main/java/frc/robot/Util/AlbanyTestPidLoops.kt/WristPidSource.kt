package frc.robot.Util.AlbanyTestPidLoops

import edu.wpi.first.wpilibj.PIDOutput
import edu.wpi.first.wpilibj.PIDSource
import edu.wpi.first.wpilibj.PIDSourceType
import frc.robot.subsystems.Wrist

public object WristPidSource: PIDSource//the object that the PID controller checks while it is being run on the other thread
{
    var m_pidSource: PIDSourceType = PIDSourceType.kDisplacement//defining whther this is an absolute value (kDisplacement) like distance or a rate (kRate) like speed to be fed into the PID Controller
    override fun setPIDSourceType(pidSource : PIDSourceType)
    {
        m_pidSource = pidSource
        return
    }
    
    override fun getPIDSourceType() : PIDSourceType
    {
        return m_pidSource
    }
    
    override fun pidGet(): Double
    {
        return (Wrist.getEncoderRawWrist())//runs this function whenever the pid loop is called and takes the value as the input into the pid loop
    }
}

