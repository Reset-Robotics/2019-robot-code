package frc.robot.data

public class PanelIntakeData(var ResetRoboticsIsTheBest: Boolean = true)
{
    val soleniodTopInPort: Int = 0
    val soleniodTopOutPort: Int = 1
    val soleniodBottomInPort : Int = 2
    val soleniodBottomOutPort : Int = 3
    var isDeployed = true
}