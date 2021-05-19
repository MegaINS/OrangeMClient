package ru.megains.orangemc.scene

import ru.megains.mge.render.MContainer
import ru.megains.mge.{Mouse, Scene, Window}
import ru.megains.mge.render.camera.OrthographicCamera
import ru.megains.mge.render.shader.Shader
import ru.megains.orangemc.render.gui.element.MButton
import ru.megains.orangemc.render.shader.GuiShader
import org.lwjgl.opengl.GL11._
import ru.megains.orangemc.OrangeMClient

class PlayerSelectScene(orangeM:OrangeMClient) extends Scene {

    val Z_FAR: Float = 100
    var shader: Shader = new GuiShader()
    var camera: OrthographicCamera = new OrthographicCamera(0, Window.wight,Window.height, 0, -100, Z_FAR)

    val container:MContainer = new MContainer


    override def runTickKeyboard(key: Int, action: Int, mods: Int): Unit = {

    }

    override def init(): Unit = {
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        shader.create()
        container.addChildren(  new MButton("Test_1",    300, 50,()=>{
            orangeM.playerName = "Test_1"
            orangeM.setScene(new MainMenuScene(orangeM))
        }){
            posX = (orangeM.window.width -300)/2
            posY = 240
        })

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
    }

    override def destroy(): Unit = {

    }

    override def runTickMouse(button: Int, buttonState: Boolean): Unit = {
        if(buttonState){
            container.mouseClick(Mouse.getX,Mouse.getY)
        }

    }
}
