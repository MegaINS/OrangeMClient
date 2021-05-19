package ru.megains.orangemc.render.gui

import ru.megains.mge.Window
import ru.megains.mge.render.MSprite
import ru.megains.mge.render.texture.Texture
import ru.megains.orangemc.render.gui.base.GuiUI

class GuiTarget extends GuiUI{

    var target: MSprite = _

    override def init(): Unit = {
        target = new MSprite(new Texture("textures/gui/target.png"), 40, 40)  {
            posY = Window.height /2-20
            posX = Window.wight  /2-20
        }
        addChildren(target)
    }




}
