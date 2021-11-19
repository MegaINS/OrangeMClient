package ru.megains.orangemc.scene

import ru.megains.mge.render.MContainer
import ru.megains.mge.{Mouse, Scene, Window}
import ru.megains.mge.render.camera.OrthographicCamera
import ru.megains.mge.render.shader.Shader
import ru.megains.mge.render.text.Text
import ru.megains.orangemc.OrangeMClient
import ru.megains.orangemc.network.{ServerData, ServerPinger}
import ru.megains.orangemc.render.gui.element.MButton
import ru.megains.orangemc.render.shader.GuiShader
import org.lwjgl.opengl.GL11._

class MultiPlayerScene(orangeM: OrangeMClient) extends BaseScene {

    val server: ServerData = new ServerData("localhost", "localhost", true)
    val pinger = new ServerPinger(orangeM)
    var pingVal: Long = -1

    val pingText: Text = new Text(pingVal.toString)
    val buttonPing: MButton = new MButton("Ping", 300, 50, () => {
        ping()
    })
    val buttonCancel: MButton = new MButton("Cancel", 300, 50, () => {
        orangeM.setScene(new MainMenuScene(orangeM))
    })
    val buttonConnect: MButton = new MButton("Connect", 300, 50, () => {
        connectToServer(server)
    })
    addChildren(buttonPing, buttonCancel, buttonConnect, pingText)

    def connectToServer(server: ServerData): Unit = {
        orangeM.setScene(new ConnectingScene(this, orangeM, server))
    }

    def ping(): Unit = {
        try {
            pinger.ping(server)
        } catch {
            case e: Throwable =>
            //e.printStackTrace()

        }
    }

    override def update(): Unit = {
        super.update()
        if (pingVal != server.pingToServer) {
            pingVal = server.pingToServer
            pingText.text = pingVal.toString
        }
    }

    override def resize(width: Int, height: Int): Unit = {
        pingText.posX = 100
        pingText.posY = 100

        buttonPing.posX = width / 2 + 50
        buttonPing.posY = height - 150

        buttonCancel.posX = width / 2 + 50
        buttonCancel.posY = height - 70

        buttonConnect.posX = width / 2 - 350
        buttonConnect.posY = height - 70

    }
}
