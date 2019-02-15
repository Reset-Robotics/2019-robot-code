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
import frc.robot.data.DrivetrainData
import frc.robot.commands.Drive.ArcadeJoystickDrive


public object Drivetrain : Subsystem(), PIDOutput
{
    val driveData: DrivetrainData = DrivetrainData()

    // Drive motors
    val driveFrontLeft: WPI_TalonSRX = WPI_TalonSRX(driveData.motorFrontLeft) // 3
    val driveFrontRight: WPI_TalonSRX = WPI_TalonSRX(driveData.motorFrontRight) // 4
    val driveBackLeft: WPI_TalonSRX = WPI_TalonSRX(driveData.motorBackLeft) // 2
    val driveBackRight: WPI_TalonSRX = WPI_TalonSRX(driveData.motorBackRight) // 1

    // PID values for turning to angles
    var turnRate: Double = driveData.turnThreshold
    var driveAngle: Double = driveData.driveAngle

    // Misc Variables/Objects
    val navx: AHRS = AHRS(SPI.Port.kMXP) // "the robot knows where it is at all times."
    var turnController: PIDController = PIDController(driveData.pidP, driveData.pidI, driveData.pidD, driveData.pidD, navx, this, 0.05)
    var isFieldOriented: Boolean = false
    var isAngleLocked: Boolean = false
    var isDriftMode: Boolean = false
    var angleDeadzone: Double = 3.0 
    

    override fun onCreate()
    {
        
        // Set up encoders
        //this.driveFrontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0)
        //this.driveFrontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0)
        //this.driveBackLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0)
        //this.driveBackRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0)   

        // Configure PID loop
        turnController.setInputRange(-180.0, 180.0)
        turnController.setOutputRange(-1.0, 1.0)
        turnController.setAbsoluteTolerance(driveData.turnThreshold)
        turnController.setContinuous(true)
        
        // Zero gyro yaw
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

        // Reset Sensors
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
        
        if(isAngleLocked && !isDriftMode) 
            localSpinVal = turnRate

        cartesianDrive(localYVal, localXVal, localSpinVal, throttleVal)
    }

    fun cartesianDrive(yVal: Double, xVal: Double, spinVal: Double, throttleVal: Double)
    {
        var localDriftMode: Boolean = getDriftMode()

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
        if(localDriftMode)
        {
            driveFrontLeft.set(wheels[0])
            driveFrontRight.set(wheels[1])
            driveBackLeft.set(yVal)
            driveBackRight.set(yVal)
        }
        if(!localDriftMode)
        {
            driveFrontLeft.set(wheels[0])
            driveFrontRight.set(wheels[1])
            driveBackLeft.set(wheels[2])
            driveBackRight.set(wheels[3])
        }
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
	
	    /*while(Math.abs(navx.getAngle() - turnController.getSetpoint()) > driveData.turnThreshold)
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
        System.err.println(isFieldOriented)
        return isFieldOriented;
    }

    fun getDriftMode(): Boolean { return isDriftMode; }

    fun toggleDriftMode(): Boolean
    {
        isDriftMode = !isDriftMode
        return isDriftMode;
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