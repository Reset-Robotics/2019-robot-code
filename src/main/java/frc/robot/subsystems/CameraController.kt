package frc.robot.subsystems

import org.sertain.command.Subsystem
import org.sertain.RobotLifecycle


object CameraController : RobotLifecycle 
{
    override fun onCreate() 
    {
        var selectedStream = "ElevatorPOV"

        when (selectedStream) 
        {
            "ElevatorPOV" -> public var camera0 = CameraServer.getInstance().startAutomaticCapture("ElevatorPOV", 0)
            "ForkliftPOV" -> public var camera0 = CameraServer.getInstance().startAutomaticCapture("ForkliftPOV", 1)
            "Elevator&Forklift" -> {
                public var camera0 = CameraServer.getInstance().startAutomaticCapture("ElevatorPOV", 0)
                public var camera1 = CameraServer.getInstance().startAutomaticCapture("ForkliftPOV", 1)
            }

            else -> public var camera0 = CameraServer.getInstance().startAutomaticCapture("ElevatorPOV", 0)
        }
    }

    fun setStream(stream: String){ selectedStream = stream }
    fun getStream(){ return selectedStream }
}