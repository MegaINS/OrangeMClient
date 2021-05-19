package ru.megains.orangemc.module.NEI

import ru.megains.mge.Window
import ru.megains.orangemc.render.gui.base.GuiOverlay

class GuiNEI extends GuiOverlay{

    val container  = new ContainerNEI

    def init(): Unit = {
        posX = Window.wight - 250
}

//    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {
//        posX =   Window.wight - 250
//        container.slots.foreach(
//            slot => {
//               // drawItemStack(slot.itemPack, slot.xPos, slot.yPos)
//            }
//        )
//
//    }

//    override def mouseClicked(x: Int, y: Int, button: Int, player: EntityPlayer): Unit = {
//        container.mouseClicked((x-posX).toInt, (y-posY).toInt, button, player)
//    }
    def isMouseOverSlot(slot: SlotNEI, mouseX: Int, mouseY: Int): Boolean = {
        mouseX >= slot.xPos && mouseX <= slot.xPos + 40 && mouseY >= slot.yPos && mouseY <= slot.yPos + 40
    }
//    override def cleanup(): Unit = {
//    }
    override def update(): Unit = {

   }
}
