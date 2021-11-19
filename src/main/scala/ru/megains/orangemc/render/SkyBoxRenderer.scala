package ru.megains.orangemc.render

import org.joml.Vector3f
import org.lwjgl.opengl.GL11._
import ru.megains.mge.render.camera.Camera
import ru.megains.mge.render.mesh.MeshMaker
import ru.megains.mge.render.model.Model
import ru.megains.mge.render.texture.Texture
import ru.megains.orangemc.render.block.RenderBlock
import ru.megains.orangemc.render.shader.SkyBoxShader

class SkyBoxRenderer {

    var skyBoxShader = new SkyBoxShader()
    var skybox:Model = _
    val skyboxTexture = new Texture("textures/skybox/Daylight Box UV_0.png")

    def init(): Unit ={
        skyBoxShader.create()
        val minX = -300
        val maxX = 300
        val minY = -300
        val maxY = 300
        val minZ = -300
        val maxZ = 300

        val mm = MeshMaker.startMakeTriangles()
        mm.setTexture(skyboxTexture)

         RenderBlock.renderSideUp(mm,0,100,100,0,100,skyboxTexture)

        var minU = 0.25f
        var maxU = 0.5f
        var minV = 0f
        var maxV = 1f/3f

        //  //mm.addNormals(0, 1, 0)
        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, maxY, minZ, minU, maxV)
        mm.addVertexWithUV(minX, maxY, maxZ, minU, minV)
        mm.addVertexWithUV(maxX, maxY, minZ, maxU, maxV)
        mm.addVertexWithUV(maxX, maxY, maxZ, maxU, minV)
        mm.addIndex(0, 2, 1)
        mm.addIndex(1, 2, 3)


        minU = 0f
        maxU = 0.25f
        minV = 1f/3f
        maxV = 2f/3f

        ////mm.addNormals(-1, 0, 0)
        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, minY, minZ, maxU, maxV)
        mm.addVertexWithUV(minX, minY, maxZ, minU, maxV)
        mm.addVertexWithUV(minX, maxY, minZ, maxU, minV)
        mm.addVertexWithUV(minX, maxY, maxZ, minU, minV)

        mm.addIndex(0, 2, 1)
        mm.addIndex(1,2, 3)




        minU = 0.5f
        maxU = 0.75f

        //mm.addNormals(1, 0, 0)
        mm.setCurrentIndex()
        mm.addVertexWithUV(maxX, minY, minZ, minU, maxV)
        mm.addVertexWithUV(maxX, minY, maxZ, maxU, maxV)
        mm.addVertexWithUV(maxX, maxY, minZ, minU, minV)
        mm.addVertexWithUV(maxX, maxY, maxZ, maxU, minV)
        mm.addIndex(0, 1, 2)
        mm.addIndex(1, 3, 2)

        minU = 0.75f
        maxU = 1f

        //mm.addNormals(0, 0, 1)
        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, minY, maxZ, maxU, maxV)
        mm.addVertexWithUV(minX, maxY, maxZ, maxU, minV)
        mm.addVertexWithUV(maxX, minY, maxZ, minU, maxV)
        mm.addVertexWithUV(maxX, maxY, maxZ, minU, minV)

        mm.addIndex(0,1, 2)
        mm.addIndex(1, 3, 2)


        minU = 0.25f
        maxU = 0.5f
        //mm.addNormals(0, 0, -1)
        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, minY, minZ, minU, maxV)
        mm.addVertexWithUV(minX, maxY, minZ, minU, minV)
        mm.addVertexWithUV(maxX, minY, minZ, maxU, maxV)
        mm.addVertexWithUV(maxX, maxY, minZ, maxU, minV)
        mm.addIndex(0, 2, 1)
        mm.addIndex(1, 2, 3)

        minU = 0.25f
        maxU = 0.5f
        minV = 2f/3f
        maxV = 3f/3f
        // //mm.addNormals(0, -1, 0)
        mm.setCurrentIndex()

        mm.addVertexWithUV(minX, minY, minZ, maxU, maxV)
        mm.addVertexWithUV(minX, minY, maxZ, maxU, minV)
        mm.addVertexWithUV(maxX, minY, minZ, minU, maxV)
        mm.addVertexWithUV(maxX, minY, maxZ, minU, minV)
        mm.addIndex(0, 1,2)
        mm.addIndex(1, 3, 2)


        skybox = new Model(mm.make())
    }

    def render(worldCamera:Camera): Unit ={
        skyBoxShader.bind()
        skyBoxShader.setUniform(worldCamera)
        glEnable(GL_CULL_FACE)
       // glEnable(GL_STENCIL_TEST)
        glEnable(GL_BLEND)
        glEnable(GL_DEPTH_TEST)

//
        skyBoxShader.setUniform("ambientLight",new Vector3f(1,1,1))
        skybox.render(skyBoxShader)


        skyBoxShader.unbind()



    }
}
