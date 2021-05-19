package ru.megains.orangemc.render.block

import ru.megains.mge.render.mesh.MeshMaker
import ru.megains.mge.render.texture.TTexture


object RenderBlock {



    def renderSideUp(mm: MeshMaker,minX: Float, maxX: Float, maxY: Float, minZ: Float, maxZ: Float, texture: TTexture): Unit = {

        val minU = texture.minU
        val maxU = texture.maxU
        val minV = texture.minV
        val maxV = texture.maxV

      //  //mm.addNormals(0, 1, 0)
        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, maxY, minZ, maxU, maxV)
        mm.addVertexWithUV(minX, maxY, maxZ, maxU, minV)
        mm.addVertexWithUV(maxX, maxY, minZ, minU, maxV)
        mm.addVertexWithUV(maxX, maxY, maxZ, minU, minV)
        mm.addIndex(0, 1, 2)
        mm.addIndex(1, 3, 2)

    }


    def renderSideUp(mm: MeshMaker,minX: Float, maxX: Float, maxY: Float, minZ: Float, maxZ: Float): Unit = {


        val averageX = (minX + maxX) / 2
        val averageZ = (minZ + maxZ) / 2

        ////mm.addNormals(0, 1, 0)

        mm.setCurrentIndex()
        mm.addVertex(minX, maxY, minZ)
        mm.addVertex(minX, maxY, maxZ)
        mm.addVertex(maxX, maxY, minZ)
        mm.addVertex(maxX, maxY, maxZ)
        mm.addVertex(averageX, maxY, averageZ)
        mm.addIndex(0, 1, 4)
        mm.addIndex(3, 2, 4)
        mm.addIndex(1, 3, 4)
        mm.addIndex(2, 0, 4)
    }

    def renderSideDown(mm: MeshMaker,minX: Float, maxX: Float, minY: Float, minZ: Float, maxZ: Float, texture: TTexture): Unit = {

        val minU = texture.minU
        val maxU = texture.maxU
        val minV = texture.minV
        val maxV = texture.maxV

       // //mm.addNormals(0, -1, 0)
        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, minY, minZ, minU, maxV)
        mm.addVertexWithUV(minX, minY, maxZ, minU, minV)
        mm.addVertexWithUV(maxX, minY, minZ, maxU, maxV)
        mm.addVertexWithUV(maxX, minY, maxZ, maxU, minV)
        mm.addIndex(0, 2,1)
        mm.addIndex(1, 2, 3)
    }


    def renderSideDown(mm: MeshMaker,minX: Float, maxX: Float, minY: Float, minZ: Float, maxZ: Float): Unit = {


        val averageX = (minX + maxX) / 2
        val averageZ = (minZ + maxZ) / 2


       // //mm.addNormals(0, -1, 0)
        mm.setCurrentIndex()
        mm.addVertex(minX, minY, minZ)
        mm.addVertex(minX, minY, maxZ)
        mm.addVertex(maxX, minY, minZ)
        mm.addVertex(maxX, minY, maxZ)
        mm.addVertex(averageX, minY, averageZ)
        mm.addIndex(1, 0, 4)
        mm.addIndex(2, 3, 4)
        mm.addIndex(3, 1, 4)
        mm.addIndex(0, 2, 4)
    }

    def renderSideWest(mm: MeshMaker,minX: Float, minY: Float, maxY: Float, minZ: Float, maxZ: Float, texture: TTexture): Unit = {


        val minU = texture.minU
        val maxU = texture.maxU
        val minV = texture.minV
        val maxV = texture.maxV

      ////mm.addNormals(-1, 0, 0)
        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, minY, minZ, minU, maxV)
        mm.addVertexWithUV(minX, minY, maxZ, maxU, maxV)
        mm.addVertexWithUV(minX, maxY, minZ, minU, minV)
        mm.addVertexWithUV(minX, maxY, maxZ, maxU, minV)

         mm.addIndex(0, 1, 2)
        mm.addIndex(1,3, 2)

    }

    def renderSideWest(mm: MeshMaker,minX: Float, minY: Float, maxY: Float, minZ: Float, maxZ: Float): Unit = {


        val averageY = (minY + maxY) / 2
        val averageZ = (minZ + maxZ) / 2


        //mm.addNormals(-1, 0, 0)
        mm.setCurrentIndex()
        mm.addVertex(minX, minY, minZ)
        mm.addVertex(minX, minY, maxZ)
        mm.addVertex(minX, maxY, minZ)
        mm.addVertex(minX, maxY, maxZ)
        mm.addVertex(minX, averageY, averageZ)
        mm.addIndex(0, 1, 4)
        mm.addIndex(1, 3, 4)
        mm.addIndex(3, 2, 4)
        mm.addIndex(2, 0, 4)

    }


    def renderSideEast(mm: MeshMaker,maxX: Float, minY: Float, maxY: Float, minZ: Float, maxZ: Float, texture: TTexture): Unit = {
        val minU = texture.minU
        val maxU = texture.maxU
        val minV = texture.minV
        val maxV = texture.maxV

        //mm.addNormals(1, 0, 0)
        mm.setCurrentIndex()
        mm.addVertexWithUV(maxX, minY, minZ, maxU, maxV)
        mm.addVertexWithUV(maxX, minY, maxZ, minU, maxV)
        mm.addVertexWithUV(maxX, maxY, minZ, maxU, minV)
        mm.addVertexWithUV(maxX, maxY, maxZ, minU, minV)
        mm.addIndex(0, 2, 1)
        mm.addIndex(1, 2, 3)

    }

    def renderSideEast(mm: MeshMaker,maxX: Float, minY: Float, maxY: Float, minZ: Float, maxZ: Float): Unit = {

        val averageY = (minY + maxY) / 2
        val averageZ = (minZ + maxZ) / 2


        //mm.addNormals(1, 0, 0)
        mm.setCurrentIndex()
        mm.addVertex(maxX, minY, minZ)
        mm.addVertex(maxX, minY, maxZ)
        mm.addVertex(maxX, maxY, minZ)
        mm.addVertex(maxX, maxY, maxZ)
        mm.addVertex(maxX, averageY, averageZ)
        mm.addIndex(1, 0, 4)
        mm.addIndex(3, 1, 4)
        mm.addIndex(2, 3, 4)
        mm.addIndex(0, 2, 4)
    }

    def renderSideSouth(mm: MeshMaker,minX: Float, maxX: Float, minY: Float, maxY: Float, maxZ: Float, texture: TTexture): Unit = {


        val minU = texture.minU
        val maxU = texture.maxU
        val minV = texture.minV
        val maxV = texture.maxV
        //mm.addNormals(0, 0, 1)
        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, minY, maxZ, minU, maxV)
        mm.addVertexWithUV(minX, maxY, maxZ, minU, minV)
        mm.addVertexWithUV(maxX, minY, maxZ, maxU, maxV)
        mm.addVertexWithUV(maxX, maxY, maxZ, maxU, minV)

        mm.addIndex(0,2, 1)
        mm.addIndex(1, 2, 3)
    }


    def renderSideSouth(mm: MeshMaker,minX: Float, maxX: Float, minY: Float, maxY: Float, maxZ: Float): Unit = {

        val averageX = (minX + maxX) / 2
        val averageY = (minY + maxY) / 2

        //mm.addNormals(0, 0, 1)
        mm.setCurrentIndex()
        mm.addVertex(minX, minY, maxZ)
        mm.addVertex(minX, maxY, maxZ)
        mm.addVertex(maxX, minY, maxZ)
        mm.addVertex(maxX, maxY, maxZ)
        mm.addVertex(averageX, averageY, maxZ)
        mm.addIndex(1, 0, 4)
        mm.addIndex(4, 0, 2)
        mm.addIndex(4, 2, 3)
        mm.addIndex(3, 1, 4)
    }

    def renderSideNorth(mm: MeshMaker,minX: Float, maxX: Float, minY: Float, maxY: Float, minZ: Float, texture: TTexture): Unit = {

        val minU = texture.minU
        val maxU = texture.maxU
        val minV = texture.minV
        val maxV = texture.maxV

        //mm.addNormals(0, 0, -1)
        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, minY, minZ, maxU, maxV)
        mm.addVertexWithUV(minX, maxY, minZ, maxU, minV)
        mm.addVertexWithUV(maxX, minY, minZ, minU, maxV)
        mm.addVertexWithUV(maxX, maxY, minZ, minU, minV)
        mm.addIndex(0, 1, 2)
        mm.addIndex(1, 3, 2)
    }


    def renderSideNorth(mm: MeshMaker,minX: Float, maxX: Float, minY: Float, maxY: Float, minZ: Float): Unit = {


        val averageX = (minX + maxX) / 2
        val averageY = (minY + maxY) / 2

        //mm.addNormals(0, 0, -1)
        mm.setCurrentIndex()
        mm.addVertex(minX, minY, minZ)
        mm.addVertex(minX, maxY, minZ)
        mm.addVertex(maxX, minY, minZ)
        mm.addVertex(maxX, maxY, minZ)
        mm.addVertex(averageX, averageY, minZ)
        mm.addIndex(0, 1, 4)
        mm.addIndex(0, 4, 2)
        mm.addIndex(2, 4, 3)
        mm.addIndex(1, 3, 4)
    }

}
