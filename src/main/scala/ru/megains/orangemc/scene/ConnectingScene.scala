package ru.megains.orangemc.scene

import java.net.{InetAddress, UnknownHostException}
import ru.megains.mge.render.MContainer
import ru.megains.mge.{Mouse, Scene, Window}
import ru.megains.mge.render.camera.OrthographicCamera
import ru.megains.mge.render.shader.Shader
import ru.megains.orangem.network.{ConnectionState, NetworkManager}
import ru.megains.orangem.network.packet.handshake.client.CHandshake
import ru.megains.orangem.network.packet.login.client.CPacketLoginStart
import ru.megains.orangemc.OrangeMClient
import ru.megains.orangemc.network.handler.NetHandlerLoginClient
import ru.megains.orangemc.network.{NetworkManagerClient, ServerAddress, ServerData}
import ru.megains.orangemc.render.gui.element.MButton
import ru.megains.orangemc.render.shader.GuiShader
import ru.megains.orangemc.utils.Logger
import org.lwjgl.opengl.GL11._

class ConnectingScene(multiPlayerScene: MultiPlayerScene, orangeM: OrangeMClient, serverDataIn: ServerData) extends BaseScene with Logger[ConnectingScene] {

    val serveraddress: ServerAddress = new ServerAddress(serverDataIn.serverIP, 20000)
    var networkManager: NetworkManager = _
    var cancel = false
    var error: Scene = _
    val buttonCancel: MButton = new MButton("Cancel", 300, 50, () => {
        orangeM.setScene(new MainMenuScene(orangeM))
    })

    connect(serveraddress.getIP, serveraddress.getPort)

    private def connect(ip: String, port: Int): Unit = {
        log.info("Connecting to {}, {}", Array[AnyRef](ip, Integer.valueOf(port)))
        new Thread("Server Connector #" /* + CONNECTION_ID.incrementAndGet*/) {
            override def run(): Unit = {
                var inetaddress: InetAddress = null
                try {
                    if (cancel) {
                        return
                    }

                    inetaddress = InetAddress.getByName(ip)
                    networkManager = NetworkManagerClient.createNetworkManagerAndConnect(inetaddress, port, orangeM)
                    networkManager.setNetHandler(new NetHandlerLoginClient(networkManager, orangeM, multiPlayerScene))
                    networkManager.sendPacket(new CHandshake(210, ip, port, ConnectionState.LOGIN))
                    networkManager.sendPacket(new CPacketLoginStart(orangeM.playerName))
                    orangeM.networkManager = networkManager
                }
                catch {
                    case unknownhostexception: UnknownHostException => {
                        if (cancel) {
                            return
                        }
                        log.error("Couldn\'t connect to server", unknownhostexception.asInstanceOf[Throwable])

                        error = new DisconnectedScene(multiPlayerScene, orangeM, "connect.failed", "Unknown host")
                    }
                    case exception: Exception => {
                        if (cancel) {
                            return
                        }
                        log.error("Couldn\'t connect to server", exception.asInstanceOf[Throwable])
                        var s: String = exception.toString
                        if (inetaddress != null) {
                            val s1: String = inetaddress + ":" + port
                            s = s.replaceAll(s1, "")
                        }
                        error = new DisconnectedScene(multiPlayerScene, orangeM, "connect.failed", s)
                    }
                }
            }
        }.start()
    }


    override def update(): Unit = {
        super.update()
        if (error != null) {
            orangeM.setScene(error)
        }
    }

    override def resize(width: Int, height: Int): Unit = {
        buttonCancel.posX = 500
        buttonCancel.posY = height - 70
    }

}
