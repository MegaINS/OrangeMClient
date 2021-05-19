package ru.megains.orangemc.render.block

import ru.megains.mge.render.mesh.MeshMaker
import ru.megains.mge.render.texture.TTexture
import ru.megains.orangem.block.BlockState
import ru.megains.orangem.utils.Direction
import ru.megains.orangem.world.World
import ru.megains.orangemc.render.TTextureRegister

class RenderBlockStandart(name: String) extends TRenderBlock {

    var texture: TTexture = _

    override def registerTexture(textureRegister: TTextureRegister): Unit = {
        texture = textureRegister.registerTexture(name)
    }

    override def render(mm: MeshMaker, blockState: BlockState, world: World, xRIn: Float, yRIn: Float, zRIn: Float): Unit = {

        val box = blockState.getBoundingBox.sum(xRIn, yRIn, zRIn)

        if (!world.getBlock(blockState.x - 1, blockState.y, blockState.z).isOpaqueCube) {
            RenderBlock.renderSideWest(mm, box.minX, box.minY, box.maxY, box.minZ, box.maxZ, getATexture(blockState, Direction.WEST, world))
        }

        if (!world.getBlock(blockState.x + 1, blockState.y, blockState.z).isOpaqueCube) {
            RenderBlock.renderSideEast(mm, box.maxX, box.minY, box.maxY, box.minZ, box.maxZ, getATexture(blockState, Direction.EAST, world))
        }

        if (!world.getBlock(blockState.x, blockState.y, blockState.z - 1).isOpaqueCube) {
            RenderBlock.renderSideNorth(mm, box.minX, box.maxX, box.minY, box.maxY, box.minZ, getATexture(blockState, Direction.NORTH, world))
        }

        if (!world.getBlock(blockState.x, blockState.y, blockState.z + 1).isOpaqueCube) {
            RenderBlock.renderSideSouth(mm, box.minX, box.maxX, box.minY, box.maxY, box.maxZ, getATexture(blockState, Direction.SOUTH, world))
        }

        if (!world.getBlock(blockState.x, blockState.y - 1, blockState.z).isOpaqueCube) {
            RenderBlock.renderSideDown(mm, box.minX, box.maxX, box.minY, box.minZ, box.maxZ, getATexture(blockState, Direction.DOWN, world))
        }

        if (!world.getBlock(blockState.x, blockState.y + 1, blockState.z).isOpaqueCube) {
            RenderBlock.renderSideUp(mm, box.minX, box.maxX, box.maxY, box.minZ, box.maxZ, getATexture(blockState, Direction.UP, world))
        }
    }

    override def getATexture(blockState: BlockState, direction: Direction, world: World): TTexture = texture

    override def getATexture: TTexture = texture
}
