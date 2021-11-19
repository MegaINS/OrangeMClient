package ru.megains.orangemc.render

import ru.megains.mge.render.mesh.{Mesh, MeshMaker}
import ru.megains.mge.render.model.TModel
import ru.megains.mge.render.shader.Shader
import ru.megains.orangem.block.{BlockAir, BlockCell}
import ru.megains.orangem.world.chunk.{Chunk, ChunkVoid}
import ru.megains.orangemc.register.GameRegisterRender
import ru.megains.orangemc.render.texture.GameTextureManager
import ru.megains.orangemc.utils.Logger


class ChunkRenderer(chunk: Chunk) extends TModel with Logger[ChunkRenderer] {

    xPos = chunk.pos.blockPosX
    yPos = chunk.pos.blockPosY
    zPos = chunk.pos.blockPosZ

    var isReRender = true
    var mesh: Mesh = _
    var blocks = 0
    var notEmpty = false

    def isVoid: Boolean = chunk == ChunkVoid

    def makeChunk(): Unit = {
        blocks = 0

        val mm: MeshMaker = MeshMaker.startMakeTriangles()

        mm.setTexture(GameTextureManager.blocksTexture)
        if (!chunk.isEmpty) {

            for (x <- 0 to 15;
                 y <- 0 to 15;
                 z <- 0 to 15) {


                val blockState = chunk.getBlockByLocPos(x, y, z)
                blockState.block match {
                    case BlockAir =>
                    case cell: BlockCell =>
                        cell.getChildrenBlocksState.foreach(
                            b => {
                                GameRegisterRender.getBlockRender(b.block).render(mm, b, chunk.world, x, y, z)
                                blocks += 1
                            }
                        )

                    case block =>
                        GameRegisterRender.getBlockRender(block).render(mm, blockState, chunk.world, x, y, z)
                        blocks += 1
                }
            }
            if (blocks != 0) {
                ChunkRenderer.chunkUpdate += 1
                mesh = mm.make()
                notEmpty = true
            } else {
                notEmpty = false
            }
        }
    }


    def reRender() {
        isReRender = true
    }

    def render(shader: Shader): Unit = {

        if (isReRender) {
            if (ChunkRenderer.isNext) {
                if (mesh != null) {
                    mesh.cleanUp()
                }
                makeChunk()
                isReRender = false
            }
        }

        if (notEmpty) {
            if (mesh != null) {
                shader.setUniform("modelMatrix", buildViewMatrix())
                mesh.render(shader)
                ChunkRenderer.chunkRender += 1
                ChunkRenderer.blockRender += blocks

            }

        }


    }


}

object ChunkRenderer {


    def isNext: Boolean = {
        if (isRender && (System.currentTimeMillis() - startTime) < (1000f / 60f)) {
            true
        } else {
            isRender = false
            isRender
        }
    }

    private var isRender = true
    var startTime: Long = 0
    var blockRender: Long = 0
    var chunkUpdate = 0
    var chunkRender = 0

    var blockRenderLast:Long = 0
    var chunkUpdateLast = 0
    var chunkRenderLast = 0

    def reset(): Unit = {
        blockRenderLast = blockRender
        chunkUpdateLast = chunkUpdate
        chunkRenderLast = chunkRender
        blockRender = 0
        chunkUpdate = 0
        chunkRender = 0
    }

    def resetRenderTime(): Unit = {
        isRender = true
        startTime = System.currentTimeMillis()
    }
}
