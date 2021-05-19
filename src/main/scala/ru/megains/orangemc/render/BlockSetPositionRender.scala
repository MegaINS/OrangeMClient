package ru.megains.orangemc.render

import java.awt.Color

import ru.megains.mge.render.mesh.MeshMaker
import ru.megains.mge.render.model.Model
import ru.megains.mge.render.shader.Shader
import ru.megains.orangem.block.BlockState
import ru.megains.orangem.physics.BoundingBox
import org.lwjgl.opengl.GL11._

class BlockSetPositionRender {

    var blockSet: Model = new Model()
    var blockStateOld:BlockState = _

    def update(blockState: BlockState): Unit = {
        if(blockState != null && blockState != blockStateOld){
            blockSet.posX = blockState.x
            blockSet.posY = blockState.y
            blockSet.posZ = blockState.z
            val mm = MeshMaker.startMake(GL_LINES)


            val aabb:BoundingBox = blockState.getBoundingBox

            // for(aabb<-aabbs.hashSet) {
            var minX = aabb.minX
            var minY = aabb.minY
            var minZ = aabb.minZ
            var maxX = aabb.maxX
            var maxY = aabb.maxY
            var maxZ = aabb.maxZ







            mm.setCurrentIndex()
            mm.addColor(Color.BLACK)
            mm.addVertex(minX, minY, minZ)
            mm.addVertex(minX, minY, maxZ)
            mm.addVertex(minX, maxY, minZ)
            mm.addVertex(minX, maxY, maxZ)
            mm.addVertex(maxX, minY, minZ)
            mm.addVertex(maxX, minY, maxZ)
            mm.addVertex(maxX, maxY, minZ)
            mm.addVertex(maxX, maxY, maxZ)

            mm.addIndex(0, 1)
            mm.addIndex(0, 2)
            mm.addIndex(0, 4)

            mm.addIndex(6, 2)
            mm.addIndex(6, 4)
            mm.addIndex(6, 7)

            mm.addIndex(3, 1)
            mm.addIndex(3, 2)
            mm.addIndex(3, 7)

            mm.addIndex(5, 1)
            mm.addIndex(5, 4)
            mm.addIndex(5, 7)

            //  }
            blockSet.mesh = mm.make()


        }else{
            if (blockSet.mesh != null) {
                blockSet.mesh.cleanUp()
                blockSet.mesh = null
            }
        }

    }
    def render(shader: Shader): Unit = {
        if (blockSet.mesh != null) {
            blockSet.render(shader)
        }
    }
}
