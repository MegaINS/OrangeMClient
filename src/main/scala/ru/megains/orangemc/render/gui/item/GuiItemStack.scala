package ru.megains.orangemc.render.gui.item

import java.awt.Color

import ru.megains.mge.render.MContainer
import ru.megains.mge.render.model.Model
import ru.megains.mge.render.text.Text
import ru.megains.orangem.item.itemstack.ItemStack
import ru.megains.orangemc.render.gui.base.Gui

class GuiItemStack(itemStack:ItemStack) extends MContainer{

    val stackSize: Text = new Text(itemStack.stackSize.toString){
        posY = 26
        scale = 0.7f
    }
    val stackMass: Text = new Text(itemStack.stackMass.toString){
        posX = 23
        posY = 26
        scale = 0.7f
    }
    val cubeSize: Model = Gui.createRect(40,15,Color.WHITE)
        cubeSize.posY = 25

    addChildren(new GuiItem(itemStack.item))
    addChildren(cubeSize,stackSize,stackMass)

}
