package ru.megains.orangemc.render.gui.item

import ru.megains.mge.render.MContainer
import ru.megains.orangem.item.Item
import ru.megains.orangemc.register.GameRegisterRender

class GuiItem(item:Item) extends MContainer{
    addChildren( GameRegisterRender.getItemRender(item).getInventoryModel)
}
