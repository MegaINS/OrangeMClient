package ru.megains.orangemc.network.protocol

import io.netty.channel.{ChannelInitializer, ChannelOption}
import io.netty.channel.socket.nio.NioSocketChannel
import ru.megains.orangem.network.NetworkManager
import ru.megains.orangem.network.protocol.{OrangeMCodec, OrangeMMessageCodec}


class OrangeMChannelInitializer(networkManager:NetworkManager) extends ChannelInitializer[NioSocketChannel]{


    override def initChannel(ch: NioSocketChannel): Unit = {
        ch.pipeline()
                .addLast("serverCodec",new OrangeMCodec)
                .addLast("messageCodec", new OrangeMMessageCodec)
                .addLast("packetHandler", networkManager)
        ch.config.setOption(ChannelOption.TCP_NODELAY, Boolean.box(true))
    }
}
