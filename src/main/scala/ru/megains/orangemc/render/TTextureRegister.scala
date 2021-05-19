package ru.megains.orangemc.render

import ru.megains.mge.render.texture.TTexture

trait TTextureRegister {


    def registerTexture(textureName : String): TTexture


}
