package ru.megains.orangemc.render.item

import ru.megains.mge.render.mesh.{Mesh, MeshMaker}
import ru.megains.mge.render.model.Model
import ru.megains.mge.render.shader.Shader
import ru.megains.mge.render.texture.TTexture
import ru.megains.orangem.item.Item
import ru.megains.orangemc.render.TTextureRegister
import ru.megains.orangemc.render.texture.GameTextureManager

class RenderItemStandart(val item: Item) extends TRenderItem {

    lazy val inventoryMesh: Mesh = createInventoryMesh()

    lazy val worldMesh: Mesh =  createWorldMesh()
    var aTexture: TTexture = _


    override def registerTexture(textureRegister: TTextureRegister): Unit = {
        aTexture = textureRegister.registerTexture(item.name)
    }

    override def renderInInventory(shader: Shader): Unit = {
        inventoryMesh.render(shader)
    }

    override def renderInWorld(shader: Shader): Unit = {
        worldMesh.render(shader)
    }

    def createWorldMesh(): Mesh = {

        val mm = MeshMaker.startMakeTriangles()


        val maxX = 0.5f
        val maxY = 0.5f
        val minX = -0.5f
        val minY = -0.5f
        val minZ = 0.1f
        val maxZ = -0.1f


        var minU: Float = 0
        var maxU: Float = 0
        var minV: Float = 0
        var maxV: Float = 0




        mm.setTexture(GameTextureManager.blocksTexture)

        minU = aTexture.minU
        maxU = aTexture.maxU
        minV = aTexture.minV
        maxV = aTexture.maxV

        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, minY, minZ, minU, maxV)
        mm.addVertexWithUV(minX, maxY, minZ, minU, minV)
        mm.addVertexWithUV(maxX, maxY, minZ, maxU, minV)
        mm.addVertexWithUV(maxX, minY, minZ, maxU, maxV)
        mm.addIndex(0, 2, 1)
        mm.addIndex(0, 3, 2)



        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, minY, maxZ, minU, maxV)
        mm.addVertexWithUV(minX, maxY, maxZ, minU, minV)
        mm.addVertexWithUV(maxX, maxY, maxZ, maxU, minV)
        mm.addVertexWithUV(maxX, minY, maxZ, maxU, maxV)
        mm.addIndex(0, 1, 2)
        mm.addIndex(0, 2, 3)





        mm.make()
    }

    def createInventoryMesh(): Mesh = {
        val mm = MeshMaker.startMakeTriangles()


        val maxX = 32
        val maxY = 32
        val minX = 0
        val minY = 0
        val zZero = 0.0f


        var minU: Float = 0
        var maxU: Float = 0
        var minV: Float = 0
        var maxV: Float = 0



        mm.setTexture(GameTextureManager.blocksTexture)

        minU = aTexture.minU
        maxU = aTexture.maxU
        minV = aTexture.minV
        maxV = aTexture.maxV

        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, minY, zZero, minU, maxV)
        mm.addVertexWithUV(minX, maxY, zZero, minU, minV)
        mm.addVertexWithUV(maxX, maxY, zZero, maxU, minV)
        mm.addVertexWithUV(maxX, minY, zZero, maxU, maxV)
        mm.addIndex(0, 2, 1)
        mm.addIndex(0, 3, 2)

        mm.make()
    }

    override def getInventoryModel: Model = new Model(inventoryMesh)


}
