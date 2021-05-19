package ru.megains.orangemc.render.block

import ru.megains.mge.render.mesh.MeshMaker
import ru.megains.mge.render.texture.TTexture
import ru.megains.orangem.block.BlockState
import ru.megains.orangem.utils.Direction
import ru.megains.orangem.world.World
import ru.megains.orangemc.render.TTextureRegister

trait TRenderBlock {

    def getATexture: TTexture

    def registerTexture(textureRegister: TTextureRegister): Unit

    def render(mm: MeshMaker, blockState: BlockState, world: World, xRIn: Float, yRIn: Float, zRIn: Float): Unit

    def getATexture(blockState: BlockState, direction: Direction, world: World): TTexture
}
