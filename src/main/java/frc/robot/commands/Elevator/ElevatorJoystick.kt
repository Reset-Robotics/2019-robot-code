package frc.robot.commands.Elevator

import org.sertain.command.Command
import frc.robot.OI
import frc.robot.subsystems.Elevator

public class ElevatorJoystick: Command ()
{
    init 
    {
        requires(Elevator)
    }
    var isElevatorLevel: Boolean = true
    var leftCorrection: Double = 1.0
    var rightCorrection: Double = 1.0
    override fun execute(): Boolean
    {
        
       
        //joystick input
         var joystickInput: Double = OI().joystickRight.getY()
        if (Math.abs(joystickInput) < Elevator.deadzone)
            joystickInput = -.1

         //auto leveling when using the joystick
        /*if (!Elevator.isElevatorLevel()){
            var error = Elevator.getElevatorError()
            if(error>0.0){//left side is too hgh
                leftCorrection = 0.8
            }
            if (error<0.0){//right side is too high
                rightCorrection = 0.8
            }
            isElevatorLevel = false
        }
        else{
            isElevatorLevel = true
            leftCorrection = 1.0
            rightCorrection = 1.0
        }*/

       
        //Elevator.lift(leftCorrection*joystickInput, -rightCorrection*joystickInput)
        //Elevator.lift(joystickInput, joystickInput)
        
        return false; 
    }
}