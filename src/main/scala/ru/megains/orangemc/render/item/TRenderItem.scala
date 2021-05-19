package ru.megains.orangemc.render.item

import ru.megains.mge.render.model.Model
import ru.megains.mge.render.shader.Shader
import ru.megains.orangemc.render.TTextureRegister


trait  TRenderItem {

   // def getItemGui: ItemGui

    def renderInInventory(shader: Shader): Unit

    def renderInWorld(shader: Shader): Unit

    def registerTexture(textureRegister: TTextureRegister): Unit

    def getInventoryModel:Model
}
