package frc.robot.subsystems

import org.sertain.command.Subsystem
import org.sertain.RobotLifecycle
import org.sertain.command.Command
import org.sertain.command.and
import org.sertain.command.then
import java.lang.reflect.Field
import frc.robot.commands.Sandstorm.Sequences.AutoDriveTest
import frc.robot.commands.Drive.Auto.DriveByTime
import frc.robot.commands.Sandstorm.Sequences.*


object AutoController : RobotLifecycle 
{
    override fun onAutoStart() 
    {
        var selectedAuto = "AutoDriveTest"

        when (selectedAuto) 
        {
            "AutoDriveTest" -> AutoDriveTest() //xdir, ydir, angle, throttle, time
            "L1L-LeaveHabForward" -> Level1Left.LeaveHabForwardFacing()
            "L1R-LeaveHabBackward" -> Level1Right.LeaveHabBackwardFacing()

            else -> DriveByTime(0.0, -1.0, 0.0, 1.0, 2.0)

            //AutoMode.TEST_RIGHT -> TestRight()
        }.start()
    }
}