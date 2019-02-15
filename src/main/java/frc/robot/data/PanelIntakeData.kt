package frc.robot.data

public class PanelIntakeData(var ResetRoboticsIsTheBest: Boolean = true)
{
    // Solenoids
    val soleniodTopInPort: Int = 0
    val soleniodTopOutPort: Int = 1
    val soleniodBottomInPort : Int = 2
    val soleniodBottomOutPort : Int = 3

    // Misc
    var isDeployed = true
}