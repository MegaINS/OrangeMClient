package ru.megains.orangemc

import org.lwjgl.glfw.GLFW._
import ru.megains.mge.Mouse
import ru.megains.orangem.entity.GameType
import ru.megains.orangem.utils.RayTraceType
import ru.megains.orangemc.render.gui.GuiInGameMenu
import ru.megains.orangemc.scene.GameScene

class PlayerController(val gameScene: GameScene) {



    def runTickKeyboard(key: Int, action: Int, mods: Int): Unit ={
        if (action == GLFW_PRESS) {
            key match {
                case GLFW_KEY_E => gameScene.guiRenderer.openPlayerInventory()


                case GLFW_KEY_ESCAPE =>
                    val gui = new GuiInGameMenu()
                    gui.init(gameScene)
                    gameScene.guiRenderer.openGui(gui)
               // case GLFW_KEY_R => gameScene.gameRenderer.worldRenderer.reRenderWorld()
                case GLFW_KEY_N => Mouse.setGrabbed(true)
                case GLFW_KEY_M =>  Mouse.setGrabbed(false)
                //case GLFW_KEY_L => renderer.isLight = !renderer.isLight
                case GLFW_KEY_C => gameScene.player.gameType = if(gameScene.player.gameType.isCreative) GameType.SURVIVAL else GameType.CREATIVE
                // case GLFW_KEY_O =>  guiManager.setGuiScreen(new GuiTestSet())
                case _ =>
            }
        }
    }

    def runTickMouse(button: Int, buttonState: Boolean): Unit = {


        if (button == GLFW_MOUSE_BUTTON_2 && buttonState && gameScene.blockSetPosition!=null) {
            gameScene.world.setBlock(gameScene.blockSetPosition)
            gameScene.gameRenderer.worldRenderer.reRender(gameScene.blockSetPosition.x,gameScene.blockSetPosition.y,gameScene.blockSetPosition.z)
        }

        if (button == GLFW_MOUSE_BUTTON_1 && buttonState && gameScene.rayTrace.traceType != RayTraceType.VOID) {
            gameScene.world.removeBlock(gameScene.rayTrace.blockState)
            gameScene.gameRenderer.worldRenderer.reRender(gameScene.rayTrace.blockState.x,gameScene.rayTrace.blockState.y,gameScene.rayTrace.blockState.z)
        }

    }
}
