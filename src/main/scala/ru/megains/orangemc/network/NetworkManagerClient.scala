package ru.megains.orangemc.network

import io.netty.bootstrap.Bootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import ru.megains.orangem.PacketProcess
import ru.megains.orangem.network.NetworkManager
import ru.megains.orangemc.network.protocol.OrangeMChannelInitializer

import java.net.InetAddress

object NetworkManagerClient {

    def createNetworkManagerAndConnect( address: InetAddress, serverPort: Int,packetProcess: PacketProcess): NetworkManager = {
        val networkManager: NetworkManager = new NetworkManager(packetProcess)

        val nioEventLoopGroup: NioEventLoopGroup = new NioEventLoopGroup
        networkManager.nioEventLoopGroup = nioEventLoopGroup
        new Bootstrap()
                .group(nioEventLoopGroup)
                .handler(new OrangeMChannelInitializer(networkManager))
                .channel(classOf[NioSocketChannel])
                .connect(address, serverPort)
                .syncUninterruptibly()
        networkManager

    }

}
