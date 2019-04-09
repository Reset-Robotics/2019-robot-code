// Reset Robotics 2019
package frc.robot.subsystems

// Libraries
import org.sertain.command.Subsystem
import org.sertain.RobotLifecycle
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;


object CameraController : RobotLifecycle 
{
    override fun onCreate() 
    {
        var selectedStream = "ElevatorPOV"

        when (selectedStream) 
        {
            "ElevatorPOV" -> {
                var camera0 = CameraServer.getInstance().startAutomaticCapture("ElevatorPOV", 0)
            } 
            "ForkliftPOV" -> {
                var camera0 = CameraServer.getInstance().startAutomaticCapture("ForkliftPOV", 1)
            }
            "Elevator&Forklift" -> {
                var camera0 = CameraServer.getInstance().startAutomaticCapture("ElevatorPOV", 0)
                var camera1 = CameraServer.getInstance().startAutomaticCapture("ForkliftPOV", 1)
            }

            else -> {
                var camera0 = CameraServer.getInstance().startAutomaticCapture("ElevatorPOV", 0)
            }
        }

        fun setStream(stream: String){ selectedStream = stream }
        fun getStream(): String = return selectedStream;
    }
}