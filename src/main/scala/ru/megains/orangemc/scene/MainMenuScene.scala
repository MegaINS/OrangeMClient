package ru.megains.orangemc.scene

import ru.megains.mge.render.MContainer
import ru.megains.mge.render.camera.OrthographicCamera
import ru.megains.mge.{Mouse, Scene, Window}
import ru.megains.mge.render.shader.Shader
import ru.megains.orangemc.OrangeMClient
import ru.megains.orangemc.render.gui.element.MButton
import ru.megains.orangemc.render.shader.GuiShader
import org.lwjgl.opengl.GL11._

class MainMenuScene(orangeM: OrangeMClient) extends BaseScene{

    val buttonSingleGame: MButton =  new MButton("SingleGame",    300, 50, ()=>{orangeM.setScene(new SelectWorldScene(orangeM))})
    val buttonMultiPlayerGame: MButton =new MButton("MultiPlayerGame",    300, 50,()=>{orangeM.setScene(new MultiPlayerScene(orangeM))})
    val buttonOption: MButton =new MButton("Option",    300, 50,()=>{/*orangeM.setScene(new OptionScene())*/})
    val buttonExitGame: MButton =  new MButton("Exit game",    300, 50,()=>{orangeM.running = false})
    addChildren( buttonSingleGame,buttonMultiPlayerGame,buttonOption,buttonExitGame)

    override def resize(width:Int,height:Int): Unit = {

        buttonSingleGame.posX = (width -300)/2
        buttonSingleGame.posY = 240

        buttonMultiPlayerGame.posX = (width -300)/2
        buttonMultiPlayerGame.posY = 310

        buttonOption. posX = (width -300)/2
        buttonOption. posY = 380

        buttonExitGame.posX = (width -300)/2
        buttonExitGame.posY = 450

    }
}
