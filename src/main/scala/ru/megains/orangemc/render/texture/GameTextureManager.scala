package ru.megains.orangemc.render.texture

import ru.megains.mge.render.texture.TextureManager

class GameTextureManager extends TextureManager{
    def init(): Unit ={
        loadTexture("texture/blocks.png", GameTextureManager.blocksTexture)

    }
}

object GameTextureManager{
    val blocksTexture: TextureMap = new TextureMap("texture/blocks.png")
}
