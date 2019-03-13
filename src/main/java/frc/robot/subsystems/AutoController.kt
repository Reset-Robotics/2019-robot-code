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
    enum class StartPosition(val nameVerbose: String, val nameShortened: String)
    {
        LEVEL1LEFT("Hab Level 1, Left Side", "Level1-Left"),
        LEVEL1MIDDLE("Hab Level 1, Middle Side", "Level1-Middle"),
        LEVEL1RIGHT("Hab Level 1, Right Side", "Level1-Right"),
        LEVEL2LEFT("Hab Level 2, Left Side", "Level2-Left"),
        LEVEL2RIGHT("Hab Level 2, Right Side", "Level2-Right")
    }

    enum class ScoringObjective(val nameVerbose: String, val nameShortened: String)
    {
        CARGOSHIPLEFT("Cargo Ship, Left Side", "Cargo-Left"),
        CARGOSHIPRIGHT("Cargo Ship, Right Side", "Cargo-Right"),
        ROCKETLEFT("Rocket Ship, Left Side", "Rocket-Left"),
        ROCKETRIGHT("Rocket Ship, Right Side", "Rocket-Right")
    }

    enum class RocketPositionsHatches(val nameVerbose: String, val nameShortened: String)
    {
        LEVEL1LEFT("Rocket Level 1, Left Side", "Level1-Left"),
        LEVEL1RIGHT("Rocket Level 1, Right Side", "Level1-Right"),
        LEVEL2LEFT("Rocket Level 2, Left Side", "Level2-Left"),
        LEVEL2RIGHT("Rocket Level 2, Right Side", "Level2-Right"),
        LEVEL3LEFT("Rocket Level 3, Left Side", "Level3-Left"),
        LEVEL3RIGHT("Rocket Level 3, Right Side", "Level3-Right")
    }

    enum class RocketPositionsCargo(val nameVerbose: String, val nameShortened: String)
    {
        LEVEL1LEFT("Rocket Level 1", "Level1"),
        LEVEL2LEFT("Rocket Level 2", "Level2"),
        LEVEL3LEFT("Rocket Level 3", "Level3"),
    }

    enum class CargoshipPositions(val nameVerbose: String, val nameShortened: String)
    {
        POD1LEFT("Cargoship Pod 1, Front Left Side", "Pod1-FrontLeft"),
        POD2LEFT("Cargoship Pod 2, Left Side", "Pod2-Left"),
        POD3LEFT("Cargoship Pod 3, Left Side", "Pod3-Left"),
        POD4LEFT("Cargoship Pod 4, Left Side", "Pod4-Left"),
        POD1RIGHT("Cargoship Pod 1, Front Right Side", "Pod1-FrontRight"),
        POD2RIGHT("Cargoship Pod 2, Right Side", "Pod2-Right"),
        POD3RIGHT("Cargoship Pod 3, Right Side", "Pod3-Right"),
        POD4RIGHT("Cargoship Pod 4, Right Side", "Pod4-Right")
    }

    enum class GamePieceObjectives(val description: String, val nameShortened: String)
    {
        CARGO1("One Cargo in desired position", "OneCargo"),
        CARGO2("Two Cargo. One in the desired position, and one in the nearest scoring position", "TwoCargo"),
        PANEL1("One Hatch in the desired position", "OneHatch"),
        PANEL2("Two Hatches. One in the desired position, and one in the nearest scoring position", "TwoHatch"),
        PANEL1CARGO1("One Hatch, One Cargo. One Hatch in the desired position, and a cargo in the same scoring position", "OnePanelOneCargo")
    }

    override fun onAutoStart() 
    {
        var selectedAuto = "AutoDriveTest"

        when (selectedAuto) 
        {
            "AutoDriveTest" -> AutoDriveTest() //xdir, ydir, angle, throttle, time
            //"L1L-LeaveHabForward" -> StartPos.Level1Left.LeaveHabForwardFacing()
            //"L1R-LeaveHabBackward" -> StartPos.Level1Right.LeaveHabBackwardFacing()

            else -> DriveByTime(0.0, -1.0, 0.0, 1.0, 2.0)

            //AutoMode.TEST_RIGHT -> TestRight()
        }.start()
    }
}