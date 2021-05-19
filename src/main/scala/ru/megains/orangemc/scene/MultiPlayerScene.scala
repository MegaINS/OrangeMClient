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

class MultiPlayerScene(orangeM:OrangeMClient) extends Scene{
    val Z_FAR: Float = 100
    var shader: Shader = new GuiShader()
    var camera: OrthographicCamera = new OrthographicCamera(0, Window.wight,Window.height, 0, -100, Z_FAR)

    val container:MContainer = new MContainer

    val server: ServerData = new ServerData("localhost", "localhost", true)
    val pinger = new ServerPinger(orangeM)
    var pingVal: Long = -1
    var pingText: Text =new Text(pingVal.toString){
        posX =100
        posY  = 100
    }

    override def runTickKeyboard(key: Int, action: Int, mods: Int): Unit = {

    }

    override def init(): Unit = {
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        shader.create()
        container.addChildren(  new MButton("Ping",    300, 50,()=>{ping()}){
            posX = 500
            posY = (orangeM.window.height -150)
        })

        container.addChildren(  new MButton("Cancel",    300, 50,()=>{orangeM.setScene(new MainMenuScene(orangeM))}){
            posX = 500
            posY = (orangeM.window.height -70)
        })
        container.addChildren(  new MButton("Connect",    300, 50,()=>{connectToServer(server)}){
            posX = 100
            posY = (orangeM.window.height -70)
        })

        container.addChildren(pingText)
    }
    def connectToServer(server: ServerData) {
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
        if (pingVal != server.pingToServer) {
            pingVal = server.pingToServer
            pingText.text = pingVal.toString
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
