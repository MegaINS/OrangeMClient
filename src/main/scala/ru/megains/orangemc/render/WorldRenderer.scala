package ru.megains.orangemc.render

import ru.megains.mge.render.shader.Shader
import ru.megains.orangem.entity.EntityPlayer
import ru.megains.orangem.world.World
import ru.megains.orangem.world.chunk.Chunk
import ru.megains.orangemc.Options

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class WorldRenderer(world:World) {

    val chunkRenderer = new mutable.HashMap[Long, ChunkRenderer]()
    var playerRenderChunks:ArrayBuffer[ChunkRenderer] = new ArrayBuffer[ChunkRenderer]()
    var lastX:Int = Int.MinValue
    var lastY:Int = Int.MinValue
    var lastZ:Int = Int.MinValue



    def render(entityPlayer: EntityPlayer,shader:Shader): Unit ={

        ChunkRenderer.resetRenderTime()

        val chunks =  getRenderChunks(entityPlayer)




        chunks.foreach(_.render(shader))


//        for(x <- -world.length to world.length;
//            y <- -world.height to world.height;
//            z <- -world.width to world.width){
//
//            chunkRenderer(Chunk.getIndex(x, y, z)).render(shader)
//
//        }

    }

    def getRenderChunks(entityPlayer: EntityPlayer): ArrayBuffer[ChunkRenderer] = {
        //        // TODO:  OPTIMIZE
        val posX: Int = (entityPlayer.posX / 16 - (if (entityPlayer.posX < 0) 1 else 0)).toInt
        val posY: Int = (entityPlayer.posY / 16 - (if (entityPlayer.posY < 0) 1 else 0)).toInt
        val posZ: Int = (entityPlayer.posZ / 16 - (if (entityPlayer.posZ < 0) 1 else 0)).toInt
        val flag = playerRenderChunks.exists(_.isVoid)
        if(posX != lastX ||
                posY != lastY ||
                posZ != lastZ ||
                playerRenderChunks.isEmpty ||
                flag){
            lastX = posX
            lastY = posY
            lastZ = posZ
            playerRenderChunks.clear()

            val R =  Options.renderRangeH * Options.renderRangeH
            for(x <- posX - Options.renderRangeH to posX +  Options.renderRangeH;
                y <- posY - Options.renderRangeV to posY +  Options.renderRangeV;
                z <- posZ - Options.renderRangeH to posZ +  Options.renderRangeH){

                if((x - posX )*(x - posX)+(z-posZ)*(z-posZ)<=R){
                    playerRenderChunks += getRenderChunk(x, y, z)
                }

            }
            playerRenderChunks = playerRenderChunks.sortBy((c)=>Math.abs(posY-c.yPos) + (Math.abs(posX-c.xPos)+ Math.abs(posZ-c.zPos))/10 )
        }

        playerRenderChunks
    }

    def getRenderChunk(x: Int, y: Int, z: Int): ChunkRenderer = {
        val i: Long = Chunk.getIndex(x, y, z)
        if (chunkRenderer.contains(i) /*&& !chunkRenderer(i).isVoid*/) chunkRenderer(i)
        else {
            val  chunkRen = createChunkRen(x, y, z)
            chunkRenderer += i -> chunkRen
            chunkRen
        }
    }

    def createChunkRen(x: Int, y: Int, z: Int): ChunkRenderer = {
        new ChunkRenderer(world.getChunk(x,y,z))
    }

    def reRender(xIn: Int, yIn: Int, zIn: Int): Unit = {
        //TODO
        val x: Int = xIn >> 4
        val y: Int = yIn >> 4
        val z: Int = zIn >> 4
        getRenderChunk(x, y, z).reRender()
        getRenderChunk(x + 1, y, z).reRender()
        getRenderChunk(x - 1, y, z).reRender()
        getRenderChunk(x, y + 1, z).reRender()
        getRenderChunk(x, y - 1, z).reRender()
        getRenderChunk(x, y, z + 1).reRender()
        getRenderChunk(x, y, z - 1).reRender()
    }

}
