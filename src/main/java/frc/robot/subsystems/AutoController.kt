package frc.robot.subsystems

import org.sertain.command.Subsystem
import org.sertain.RobotLifecycle
import org.sertain.command.Command
//import org.sertain.command.CommandBridgeMirror
import org.sertain.command.and
import org.sertain.command.then
import java.lang.reflect.Field
import frc.robot.commands.Sandstorm.Sequences.AutoDriveTest
import frc.robot.commands.Drive.Auto.DriveByTime



/*private fun CommandBridgeMirror.waitUntil(condition: () -> Boolean) = object : Command() {
    override fun execute() = condition()
} then this*/

object AutoController : RobotLifecycle 
{
    override fun onAutoStart() 
    {
        var selectedAuto = "AutoDriveTest"

        when (selectedAuto) 
        {
            "AutoDriveTest" -> DriveByTime(0.0, -1.0, 0.0, 1.0, 2.0) //xdir, ydir, angle, throttle, time

            else -> DriveByTime(0.0, -1.0, 0.0, 1.0, 1.0)

            //AutoMode.TEST_RIGHT -> TestRight()
        }.start()
    }
}