package ru.megains.orangemc.scene

import ru.megains.mge.render.MContainer
import ru.megains.mge.{Mouse, Scene, Window}
import ru.megains.mge.render.camera.OrthographicCamera
import ru.megains.mge.render.shader.Shader
import ru.megains.orangemc.render.gui.element.MButton
import ru.megains.orangemc.render.shader.GuiShader
import org.lwjgl.opengl.GL11._
import ru.megains.orangemc.OrangeMClient

class SelectWorldScene(orangeM: OrangeMClient) extends Scene {
    val Z_FAR: Float = 100
    var shader: Shader = new GuiShader()
    var camera: OrthographicCamera = new OrthographicCamera(0, Window.wight, Window.height, 0, -100, Z_FAR)

    val container: MContainer = new MContainer


    override def runTickKeyboard(key: Int, action: Int, mods: Int): Unit = {

    }

    override def init(): Unit = {
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        shader.create()
        container.addChildren(new MButton("Select", 300, 50, () => {
            orangeM.setScene(new GameScene(orangeM))
            //            val saveHandler = tar.saveLoader.getSaveLoader(selectWorld.worldName)
            //            //tar.loadWorld(new World(saveHandler))
            //            tar.guiManager.setGuiScreen(null)
        }) {
            posX = 100
            posY = (orangeM.window.height - 150)
        })
        container.addChildren(new MButton("Delete", 300, 50, () => {
            //                    tar.saveLoader.deleteWorldDirectory(selectWorld.worldName)
            //                    tar.guiManager.setGuiScreen(this)
        }) {
            posX = 100
            posY = (orangeM.window.height - 70)
        })
        container.addChildren(new MButton("CreateWorld", 300, 50, () => {
            //                    val saveHandler = tar.saveLoader.getSaveLoader("World " + (worldsSlot.length + 1))
            //                    //tar.loadWorld(new World(saveHandler))
            //                    tar.guiManager.setGuiScreen(null)

        }) {
            posX = 500
            posY = (orangeM.window.height - 150)
        })
        container.addChildren(new MButton("Cancel", 300, 50, () => {
            orangeM.setScene(new MainMenuScene(orangeM))
        }) {
            posX = 500
            posY = (orangeM.window.height - 70)
        })


    }

    override def render(): Unit = {
        glEnable(GL_STENCIL_TEST)
        glEnable(GL_BLEND)
        glEnable(GL_CULL_FACE)
        glDisable(GL_DEPTH_TEST)
        camera.setOrtho(0, Window.wight, Window.height, 0, -100, Z_FAR)
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
        if (buttonState) {
            container.mouseClick(Mouse.getX, Mouse.getY)
        }

    }
}
