package ru.megains.orangemc.network.handler

import ru.megains.orangem.network.NetworkManager
import ru.megains.orangem.network.handler.INetHandlerStatusClient
import ru.megains.orangem.network.packet.status.client.CPacketPing
import ru.megains.orangem.network.packet.status.server.{SPacketPong, SPacketServerInfo}
import ru.megains.orangem.utils.Logger
import ru.megains.orangemc.OrangeMClient
import ru.megains.orangemc.network.ServerData

class NetHandlerStatusClient(server:ServerData,networkManager: NetworkManager) extends INetHandlerStatusClient with Logger[NetHandlerStatusClient]{

    var successful: Boolean = false
    var receivedStatus: Boolean = false
    var pingSentAt: Long = 0L

    override def handleServerInfo(packetIn: SPacketServerInfo) {
        if (receivedStatus) networkManager.closeChannel("ServerPinger" + "receivedStatus")
        else {
            receivedStatus = true

            this.pingSentAt = OrangeMClient.getSystemTime
            networkManager.sendPacket(new CPacketPing(pingSentAt))
            this.successful = true
        }
    }

    override def handlePong(packetIn: SPacketPong) {
        val i: Long = pingSentAt
        val j: Long = OrangeMClient.getSystemTime
        server.pingToServer = j - i
        networkManager.closeChannel("ServerPinger" + "handlePong")
    }

    override def onDisconnect(msg: String) {
        if (!successful) {
            log.error("Can\'t ping {}: {}", Array[AnyRef](server.serverIP, msg))
            server.serverMOTD = "Can\'t connect to server."
            server.populationInfo = ""
                    }
    }

    override def disconnect(msg: String): Unit = {

    }
}
