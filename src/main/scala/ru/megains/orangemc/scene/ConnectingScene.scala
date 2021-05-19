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
import ru.megains.orangemc.network.{ServerAddress, ServerData}
import ru.megains.orangemc.render.gui.element.MButton
import ru.megains.orangemc.render.shader.GuiShader
import ru.megains.orangemc.utils.Logger
import org.lwjgl.opengl.GL11._

class ConnectingScene(multiPlayerScene:MultiPlayerScene,orangeM: OrangeMClient,serverDataIn: ServerData) extends Scene with Logger[ConnectingScene]{
    val Z_FAR: Float = 100
    var shader: Shader = new GuiShader()
    var camera: OrthographicCamera = new OrthographicCamera(0, Window.wight,Window.height, 0, -100, Z_FAR)

    val container:MContainer = new MContainer

    val serveraddress: ServerAddress = new ServerAddress(serverDataIn.serverIP, 20000)
    var networkManager: NetworkManager = _
    var cancel = false
    var error: Scene = _


    connect(serveraddress.getIP, serveraddress.getPort)

    override def runTickKeyboard(key: Int, action: Int, mods: Int): Unit = {

    }

    override def init(): Unit = {
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        shader.create()
        container.addChildren(  new MButton("Cancel",    300, 50,()=>{orangeM.setScene(new MainMenuScene(orangeM))}){
            posX = 500
            posY = (orangeM.window.height -70)
        })

    }
    private def connect(ip: String, port: Int) {
        log.info("Connecting to {}, {}", Array[AnyRef](ip, Integer.valueOf(port)))
        new Thread("Server Connector #" /* + CONNECTION_ID.incrementAndGet*/) {
            override def run() {
                var inetaddress: InetAddress = null
                try {
                    if (cancel) {
                        return
                    }

                    inetaddress = InetAddress.getByName(ip)
                    networkManager = NetworkManager.createNetworkManagerAndConnect(inetaddress, port,orangeM)
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

                        error = new DisconnectedScene(multiPlayerScene,orangeM, "connect.failed", "Unknown host")
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
                        error = new DisconnectedScene(multiPlayerScene,orangeM, "connect.failed", s)
                    }
                }
            }
        }.start()
    }
    override def render(): Unit = {
        glEnable(GL_STENCIL_TEST)
        glEnable(GL_BLEND)
        glEnable(GL_CULL_FACE)
        glDisable(GL_DEPTH_TEST)
        camera.setOrtho(0, Window.wight,Window.height, 0, -100, Z_FAR)
        shader.bind()
        shader.setUniform(camera)

        container.render(shader)

        shader.unbind()

        glDisable(GL_BLEND)
        glDisable(GL_CULL_FACE)
        glEnable(GL_DEPTH_TEST)
    }

    override def update(): Unit = {
        container.update()
        if (error != null) {
            orangeM.setScene(error)
        }
    }

    override def destroy(): Unit = {

    }

    override def runTickMouse(button: Int, buttonState: Boolean): Unit = {
        if(buttonState){
            container.mouseClick(Mouse.getX,Mouse.getY)
        }

    }
}
