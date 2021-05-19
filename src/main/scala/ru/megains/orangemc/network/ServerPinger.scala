package ru.megains.orangemc.network

import java.net.{InetAddress, UnknownHostException}

import ru.megains.orangem.network.{ConnectionState, NetworkManager}
import ru.megains.orangem.network.packet.handshake.client.CHandshake
import ru.megains.orangem.network.packet.status.client.CPacketServerQuery
import ru.megains.orangemc.OrangeMClient
import ru.megains.orangemc.network.handler.NetHandlerStatusClient
import ru.megains.orangemc.utils.Logger


class ServerPinger(orangeM: OrangeMClient) extends Logger[ServerPinger] {

    //  private val pingDestinations: util.List[NetworkManager] = Collections.synchronizedList[NetworkManager](Lists.newArrayList[NetworkManager])

    @throws[UnknownHostException]
    def ping(server: ServerData) {
        val serveraddress: ServerAddress = new ServerAddress(server.serverIP, 20000)
        // val networkmanager: NetworkManager = NetworkManager.createLocalClient(LocalAddress.ANY)

        val networkmanager: NetworkManager = NetworkManager.createNetworkManagerAndConnect(InetAddress.getByName(serveraddress.getIP), serveraddress.getPort,orangeM)
        // pingDestinations.add(networkmanager)

        server.serverMOTD = "Pinging..."
        server.pingToServer = -1L
        server.playerList = null
        networkmanager.setNetHandler(new NetHandlerStatusClient(server,networkmanager) )
        try {
            networkmanager.sendPacket(new CHandshake(210, serveraddress.getIP, serveraddress.getPort, ConnectionState.STATUS))
            networkmanager.sendPacket(new CPacketServerQuery)

        } catch {
            case throwable: Throwable =>
                log.error("error",throwable)
                throwable.printStackTrace()
        }
    }
}
