package frc.robot.subsystems

import org.sertain.command.Subsystem
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.*
import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.PIDController
import edu.wpi.first.wpilibj.PIDOutput
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.Timer

import frc.robot.Mag
import frc.robot.IDs
import frc.robot.commands.Drive.ArcadeJoystickDrive


public object Drivetrain : Subsystem(), PIDOutput
{
    val ids: IDs = IDs()

    val pidValP: Double = ids.drivetrainPID.get("P") ?: 0.006
    val pidValI: Double = ids.drivetrainPID.get("I") ?: 0.0
    val pidValD: Double = ids.drivetrainPID.get("D") ?: 0.0
    val pidValF: Double = ids.drivetrainPID.get("F") ?: 0.0
    val wheelCircumference: Double = 18.8495559215
    val deadzone: Double = ids.deadzones.get("Drivetrain") ?: 0.1

    // drive motors
    // motor 0 is the climber
    val driveFrontLeft: WPI_TalonSRX = WPI_TalonSRX((ids.driveMotorIDs.get("Front-Left")) ?: 3) // 3
    val driveFrontRight: WPI_TalonSRX = WPI_TalonSRX((ids.driveMotorIDs.get("Front-Right")) ?: 4) // 4
    val driveBackLeft: WPI_TalonSRX = WPI_TalonSRX((ids.driveMotorIDs.get("Back-Left")) ?: 2) // 2
    val driveBackRight: WPI_TalonSRX = WPI_TalonSRX((ids.driveMotorIDs.get("Back-Right")) ?: 1) // 1

    // PID values for turning to angles; PIDF stored in IDs()
    val turnThreshold: Double = 2.0 // how many degrees the robot has to be within for it to stop looking for the required angle
    var turnRate: Double = 0.0
    var driveAngle: Double = 0.0

    // other assorted vars/objects
    val navx: AHRS = AHRS(SPI.Port.kMXP) // "the robot knows where it is at all times."
    var turnController: PIDController = PIDController(pidValP, pidValI, pidValD, pidValF, navx, this, 0.05)
    var isFieldOriented: Boolean = false
    var isAngleLocked: Boolean = false
    //var isProfileFinished: Boolean = false
    //var angleDeadzone: Double = 3.0 
    

    override fun onCreate()
    {
        
        // Set up encoders
        this.driveFrontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0)
        this.driveFrontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0)
        this.driveBackLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0)
        this.driveBackRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0)   

        // configure PID loop
        
        turnController.setInputRange(-180.0, 180.0)
        turnController.setOutputRange(-1.0, 1.0)
        turnController.setAbsoluteTolerance(turnThreshold)
        turnController.setContinuous(true)
        

        //zero gyro yaw
        resetGyro()
        
    }

    fun Drivetrain()
    {
        // Set Talon Mode
        this.driveFrontLeft.setNeutralMode(NeutralMode.Brake)
        this.driveFrontRight.setNeutralMode(NeutralMode.Brake)
        this.driveBackLeft.setNeutralMode(NeutralMode.Brake)
        this.driveBackRight.setNeutralMode(NeutralMode.Brake)
		
        // Current Limiting
		this.driveFrontLeft.configContinuousCurrentLimit(40,0) // desired current after limit
		this.driveFrontLeft.configPeakCurrentLimit(35, 0) // max current
		this.driveFrontLeft.configPeakCurrentDuration(100, 0) // how long after max current to be limited (ms)
		this.driveFrontLeft.enableCurrentLimit(true)
		this.driveFrontRight.configContinuousCurrentLimit(40,0)
		this.driveFrontRight.configPeakCurrentLimit(35, 0)
		this.driveFrontRight.configPeakCurrentDuration(100, 0)
		this.driveFrontRight.enableCurrentLimit(true)
		this.driveBackLeft.configContinuousCurrentLimit(40,0)
		this.driveBackLeft.configPeakCurrentLimit(35, 0)
		this.driveBackLeft.configPeakCurrentDuration(100, 0)
		this.driveBackLeft.enableCurrentLimit(true)
		this.driveBackRight.configContinuousCurrentLimit(40,0)
		this.driveBackRight.configPeakCurrentLimit(35, 0)
		this.driveBackRight.configPeakCurrentDuration(100, 0)
		this.driveBackRight.enableCurrentLimit(true)   

		resetGyro()
        resetEncoders()
    }

    fun drive(yVal: Double, xVal: Double, spinVal: Double, throttleVal: Double)
    {
        var localYVal: Double = yVal
        var localXVal: Double = xVal
        var localSpinVal: Double = spinVal
        var localFieldOriented: Boolean = getFieldOriented()

        if(localFieldOriented)
        {
            var angle: Double = navx.getAngle() * Math.PI / 180
            var rotatedYVal: Double = yVal * Math.cos(angle) + xVal * Math.sin(angle)
            var rotatedXVal: Double = -yVal * Math.sin(angle) + xVal * Math.cos(angle)

            localYVal = rotatedYVal
            localXVal = rotatedXVal
        }

        if(isAngleLocked) localSpinVal = turnRate

        cartesianDrive(localYVal, localXVal, localSpinVal, throttleVal)
    }

    fun cartesianDrive(yVal: Double, xVal: Double, spinVal: Double, throttleVal: Double)
    {
        val wheels: DoubleArray = doubleArrayOf(((-yVal - xVal + spinVal) * throttleVal), 
        ((-yVal + xVal + spinVal) * throttleVal), 
        (-((-yVal - xVal - spinVal) * throttleVal)), 
        (-((-yVal + xVal - spinVal) * throttleVal)))
        var max: Double = 0.0
        for(v: Double in wheels)
            if(Math.abs(v) > max) max = Math.abs(v)

        if(max > 1.0)
        {   
            wheels[0] = wheels[0] / max
            wheels[1] = wheels[1] / max
            wheels[2] = wheels[2] / max
            wheels[3] = wheels[3] / max
        }

        driveFrontLeft.set(wheels[0])
        driveFrontRight.set(wheels[3])
        driveBackLeft.set(wheels[1])
        driveBackRight.set(wheels[2])
    }

    fun fieldOrientedDrive(yVal: Double, xVal: Double, spinVal: Double, throttleVal: Double)
    {
        val angle: Double = navx.getAngle() * Math.PI / 180
		val rotatedYVal: Double = yVal * Math.cos(angle) + xVal * Math.sin(angle)
		val rotatedXVal: Double = -yVal * Math.sin(angle) + xVal * Math.cos(angle)
		
        cartesianDrive(rotatedYVal, rotatedXVal, spinVal, throttleVal)
    }

    /* 
    fun driveAtAngle(yVal: Double, xVal: Double, angle: Double, throttleVal: Double)
    {
        //if(!turnController.isEnabled())
		//	turnController.enable()
		
		//if(turnController.getSetpoint() != angle)
		//	turnController.setSetpoint(angle)
		
		val spin: Double = -getAngle() * 0.02
		
        cartesianDrive(yVal, xVal, spin, throttleVal)
    }
    */
    fun fieldOrientedDriveAtAngle(yVal: Double, xVal: Double, angle: Double, throttleVal: Double)
    {
		val gyroAngle: Double = navx.getAngle() * Math.PI / 180 //convert degrees to radians
		
		//rotate the coordinates by the gyro angle
		val rotatedYVal: Double = yVal * Math.cos(gyroAngle) + xVal * Math.sin(gyroAngle)
		val rotatedXVal: Double = -yVal * Math.sin(gyroAngle) + xVal * Math.cos(gyroAngle)
		
		//driveAtAngle(rotatedYVal, rotatedXVal, angle, throttleVal)
    }

    fun turnToAngle(angle: Double, throttleVal: Double)
    {
	    killMotors()
	
	    lockAngle()
	
	    /*while(Math.abs(navx.getAngle() - turnController.getSetpoint()) > turnThreshold)
		    drive(0.0, 0.0, 0.0, throttleVal);*/
		
	    killMotors()
    }

	fun polarDrive(angle: Double, spin: Double, speed: Double)
    {
		var localAngle: Double = angle + 90
		localAngle = localAngle * Math.PI / 180
		
		val yVal: Double = Math.cos(localAngle) * speed
		val xVal: Double = Math.sin(localAngle) * speed
		
		cartesianDrive(yVal, xVal, spin, 1.0)
    }

    fun killMotors()
    {
        driveFrontLeft.set(0.0)
        driveFrontRight.set(0.0)
        driveBackLeft.set(0.0)
        driveBackRight.set(0.0)
    }

    fun getFieldOriented(): Boolean { return isFieldOriented; }
    
    fun toggleFieldOriented(): Boolean
    { 
        isFieldOriented = !isFieldOriented
        return isFieldOriented;
    }

    fun resetGyro()
    {
        navx.reset()
        navx.zeroYaw()
    }

    fun resetMotorPositions()
    {
        driveFrontLeft.set(0.0)
        driveFrontRight.set(0.0)
        driveBackLeft.set(0.0)
        driveBackRight.set(0.0)
    }

    fun resetEncoders()
    {
        this.driveFrontLeft.setSelectedSensorPosition(0, 0, 0)
        this.driveFrontRight.setSelectedSensorPosition(0, 0, 0)
        this.driveBackLeft.setSelectedSensorPosition(0, 0, 0)
        this.driveBackRight.setSelectedSensorPosition(0, 0, 0)
    }

    fun lockAtAngle(targetPos: Double): Boolean
    {
        if (!isAngleLocked)
        {
            driveAngle = targetPos
            turnController.enable()
            turnController.setSetpoint(driveAngle)
            isAngleLocked = true
        }
        return isAngleLocked;
    }
    
    fun lockAngle(): Boolean
    {
        if (!isAngleLocked)
        {
        driveAngle = getAngle()
        turnController.enable()
        turnController.setSetpoint(driveAngle)
        isAngleLocked = true
        }
        else 
        {
            isAngleLocked = false
            turnController.disable()
        }
        return isAngleLocked;
    }

    fun unlockAngle(): Boolean
    {
        turnController.disable()
        isAngleLocked = false
        return isAngleLocked;
    }

    fun getAngle(): Double { return navx.getAngle() * Math.PI / 180; } 

    fun getEncoderRawFrontLeft(): Int { return driveFrontLeft.getSelectedSensorPosition(0); }
    fun getEncoderRawFrontRight(): Int { return driveFrontRight.getSelectedSensorPosition(0); }
    fun getEncoderRawBackLeft(): Int { return driveBackLeft.getSelectedSensorPosition(0); }
    fun getEncoderRawBackRight(): Int { return driveBackRight.getSelectedSensorPosition(0); }

    fun getSpeedFrontLeft(): Double { return driveFrontLeft.get(); }
    fun getSpeedFrontRight(): Double { return driveFrontRight.get(); }
    fun getSpeedBackLeft(): Double { return driveBackLeft.get(); }
    fun getSpeedBackRight(): Double { return driveBackRight.get(); }

    override fun pidWrite(output: Double){ turnRate = output }

    override val defaultCommand = ArcadeJoystickDrive()
}