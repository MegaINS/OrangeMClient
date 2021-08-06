package ru.megains.orangemc.render.block

import ru.megains.mge.render.mesh.MeshMaker
import ru.megains.mge.render.texture.TTexture
import ru.megains.orangem.block.BlockState
import ru.megains.orangem.utils.Direction
import ru.megains.orangem.world.World
import ru.megains.orangemc.render.TTextureRegister

class RendererMiniBlock(name: String) extends TRenderBlock {
        var texture: TTexture = _

        override def registerTexture(textureRegister: TTextureRegister): Unit = {
            texture = textureRegister.registerTexture(name)
        }


        override def render(mm: MeshMaker , blockStateIn: BlockState, world: World, xRIn: Float, yRIn: Float, zRIn: Float): Unit = {

            //todo head.
            val box = blockStateIn.getBoundingBox.head.sum(xRIn, yRIn , zRIn )


           // if (!world.getCell(blockState.x - 16, blockState.y, blockState.z).isOpaqueCube) {
                RenderBlock.renderSideWest(mm,box.minX, box.minY, box.maxY, box.minZ, box.maxZ, getATexture(blockStateIn,Direction.WEST,world))
           // }

           // if (!world.getCell(blockState.x + 16, blockState.y, blockState.z).isOpaqueCube) {
                RenderBlock.renderSideEast(mm,box.maxX, box.minY, box.maxY, box.minZ, box.maxZ, getATexture(blockStateIn,Direction.EAST,world))
           // }

           // if (!world.getCell(blockState.x, blockState.y, blockState.z - 16).isOpaqueCube) {
                RenderBlock.renderSideNorth(mm,box.minX, box.maxX, box.minY, box.maxY, box.minZ, getATexture(blockStateIn,Direction.NORTH,world))
           // }

           // if (!world.getCell(blockState.x, blockState.y, blockState.z + 16).isOpaqueCube) {
                RenderBlock.renderSideSouth(mm,box.minX, box.maxX, box.minY, box.maxY, box.maxZ, getATexture(blockStateIn,Direction.SOUTH,world))
           // }

           // if (!world.getCell(blockState.x, blockState.y - 16, blockState.z).isOpaqueCube) {
                RenderBlock.renderSideDown(mm,box.minX, box.maxX, box.minY, box.minZ, box.maxZ, getATexture(blockStateIn,Direction.DOWN,world))
           // }

           // if (!world.getCell(blockState.x, blockState.y + 16, blockState.z).isOpaqueCube) {
                RenderBlock.renderSideUp(mm,box.minX, box.maxX, box.maxY, box.minZ, box.maxZ, getATexture(blockStateIn,Direction.UP,world))
           // }
        }

        override def getATexture: TTexture = texture

        override def getATexture(blockState: BlockState, direction: Direction, world: World): TTexture = texture
}
