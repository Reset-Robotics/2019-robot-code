
package frc.robot.Util

import edu.wpi.first.hal.DIOJNI;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.DigitalSource

public class UltrasonicBase(channel: Int): DigitalSource()
{
    var m_channel: Int = 0 
    var m_handle: Int = 0
    fun UltrasonicBase(channel: Int)
    {
        m_channel = channel
        //m_handle = DIOJNI.initializeDIOPort(HAL.getPort((byte) channel), true)
        m_handle = DIOJNI.initializeDIOPort(channel, true)

        HAL.report(tResourceType.kResourceType_DigitalInput, channel)
        setName("Ultrasonic", channel)
    }

    override fun close() {
        super.close()
        if (m_interrupt != 0) 
        {
        cancelInterrupts()
        }
    }

    override fun getChannel() : Int
    {
        return m_channel;
    }

    override fun getAnalogTriggerTypeForRouting(): Int
    {
        return 0;
    }

    fun get():Boolean
    {
    return DIOJNI.getDIO(m_handle);
    }
    override fun isAnalogTrigger(): Boolean
    {
        return false;
    }

    override fun getPortHandleForRouting() : Int
    {
        return m_handle;
    }

    override fun readRisingTimestamp(): Double
    {
        return this.readRisingTimestamp();
    }

    fun readFallingTimeStamp(): Double
    {
        return this.readFallingTimestamp();
    }

    override fun allocateInterrupts(watcher: Boolean)
    {
        this.allocateInterrupts(watcher)
    }




    override fun initSendable(builder:SendableBuilder) {
    builder.setSmartDashboardType("Digital Input")
    builder.addBooleanProperty("Value", this::get, null)
    }
}