package ru.megains.orangemc.scene

import ru.megains.mge.render.MContainer
import ru.megains.mge.{Mouse, Scene, Window}
import ru.megains.mge.render.camera.OrthographicCamera
import ru.megains.mge.render.shader.Shader
import ru.megains.orangemc.render.gui.element.MButton
import ru.megains.orangemc.render.shader.GuiShader
import org.lwjgl.opengl.GL11._
import ru.megains.orangemc.OrangeMClient

class PlayerSelectScene(orangeM:OrangeMClient) extends BaseScene {

    val buttonTest_1:MButton = new MButton("Test_1",300, 50,()=>{orangeM.playerName = "Test_1"; orangeM.setScene(new MainMenuScene(orangeM))})
    addChildren(buttonTest_1)

    override def resize(width:Int,height:Int): Unit = {
        buttonTest_1.posX = (width -300)/2
        buttonTest_1.posY = 240
    }
}
