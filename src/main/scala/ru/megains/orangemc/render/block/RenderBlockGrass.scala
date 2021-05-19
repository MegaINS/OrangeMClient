package ru.megains.orangemc.render.block

import ru.megains.mge.render.texture.TTexture
import ru.megains.orangem.block.BlockState
import ru.megains.orangem.utils.Direction
import ru.megains.orangem.world.World
import ru.megains.orangemc.render.TTextureRegister

class RenderBlockGrass(name: String) extends RenderBlockStandart(name) {


    var aTextureUp: TTexture = _
    var aTextureDown: TTexture = _


    override def registerTexture(textureRegister: TTextureRegister): Unit = {
        texture = textureRegister.registerTexture(name + "_side")
        aTextureUp = textureRegister.registerTexture(name + "_up")
        aTextureDown = textureRegister.registerTexture(name + "_down")
    }

    override def getATexture(blockState: BlockState,direction: Direction,world: World): TTexture = {
        direction match {
            case Direction.UP => aTextureUp
            case Direction.DOWN => aTextureDown
            case _ => texture
        }

    }


}
