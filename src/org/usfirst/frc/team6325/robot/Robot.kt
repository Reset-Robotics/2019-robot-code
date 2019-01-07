// Rename to <robotname>.kt once we pick a robot name
package org.usfirst.frc.team6325.robot

import org.sertain.*
import org.sertain.command.Command
import edu.wpi.first.wpilibj.command.Scheduler

// import commands
import org.usfirst.frc.team6325.robot.subsystems.*


class Robot : Robot()
{
    // Initialize subsystem instance objects for this script
    public val drivetrain: Drivetrain = Drivetrain()

    // auto command/chooser initilization goes here later?

    // OI Initialization
    public var oi: OI = OI()
    
    // Runs on robot initialization; WPILib robotInit() equivalent
    override fun onCreate()
    {
        // put any data to dashboard here
    }

    // Runs periodically when the robot is disabled; WPILib disabledPeriodic() equivalent
    override fun executeDisabled()
    {
        Scheduler.getInstance().run()
        // any dashboard data population here too
    }

    // Runs on autonomous(sandstorm) initialization; WPILib autonomousInit() equivalent
    override fun onAutoStart()
    {
        /* auto code goes here later. for now, have a banana
        
         _
        //\
        V  \
         \  \_
          \,'.`-.
           |\ `. `.
           ( \  `. `-.                        _,.-:\
            \ \   `.  `-._             __..--' ,-';/
             \ `.   `-.   `-..___..---'   _.--' ,'/
              `. `.    `-._        __..--'    ,' /
                `. `-_     ``--..''       _.-' ,'
                  `-_ `-.___        __,--'   ,'
                     `-.__  `----"""    __.-'
        hh                `--..____..--'
         */
    }

    // Runs periodically during autonomous(sandstorm); WPILib autonomousPeriodic() equivalent
    override fun executeAuto()
    {
        Scheduler.getInstance().run()
        // put any dashboard data
        // something to allow for interruption and transition to 'teleop' either at the end of the sandstorm or as soon as the driver takes control
    }

    // Runs on teleop initialization; WPILib teleopInit() equivalent
    override fun onStart()
    {
        // zero navx yaw
        // reset drivetrain encoders
        // reset elevator encoders
        // any other starting configurations
        // nullcheck auto command and cancel it since telop is starting; this can eventually be replaced with smoother transition optimization to allow for a few seconds longer in auto control to allow for the sandstorm barrier to be fully up before drivers take control
    }

    // Runs periodically during teleop; WPILib teleopPeriodic() equivalent
    override fun executeTeleop()
    {
        Scheduler.getInstance.run()
        // put dashboard data here
    }

}