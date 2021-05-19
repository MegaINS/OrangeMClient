package ru.megains.orangemc.render

import java.awt.Color

import ru.megains.mge.render.mesh.MeshMaker
import ru.megains.mge.render.model.Model
import ru.megains.mge.render.shader.Shader
import ru.megains.orangem.utils.{RayTraceResult, RayTraceType}
import ru.megains.orangemc.render.block.RenderBlock
import ru.megains.orangemc.scene.GameScene
import org.lwjgl.opengl.GL11._

class RayTraceRender(gameScene: GameScene) {


    var selectPos: Array[Model] = new Array[Model](6)
    var blockMouseOver: Model = new Model()
    var rayTrace: RayTraceResult = RayTraceResult.VOID


    def init() {
        val zero = 0f
        val neZero = 0.01f
        val one = 1 / 16f

        val mmSideDown = MeshMaker.startMakeTriangles()
        mmSideDown.addColor(Color.BLACK)
        RenderBlock.renderSideDown(mmSideDown, zero, one, -neZero, zero, one)
        selectPos(0) = new Model(mmSideDown.make())

        val mmSideUp = MeshMaker.startMakeTriangles()
        mmSideUp.addColor(Color.BLACK)
        RenderBlock.renderSideUp(mmSideUp, zero, one, neZero, zero, one)
        selectPos(1) = new Model(mmSideUp.make())


        val mmSideNorth = MeshMaker.startMakeTriangles()
        mmSideNorth.addColor(Color.BLACK)
        RenderBlock.renderSideNorth(mmSideNorth, zero, one, zero, one, -neZero)
        selectPos(2) = new Model(mmSideNorth.make())

        val mmSideSouth = MeshMaker.startMakeTriangles()
        mmSideSouth.addColor(Color.BLACK)
        RenderBlock.renderSideSouth(mmSideSouth, zero, one, zero, one, neZero)
        selectPos(3) = new Model(mmSideSouth.make())

        val mmSideWest = MeshMaker.startMakeTriangles()
        mmSideWest.addColor(Color.BLACK)
        RenderBlock.renderSideWest(mmSideWest, -neZero, zero, one, zero, one)
        selectPos(4) = new Model(mmSideWest.make())

        val mmSideEast = MeshMaker.startMakeTriangles()
        mmSideEast.addColor(Color.BLACK)
        RenderBlock.renderSideEast(mmSideEast, neZero, zero, one, zero, one)
        selectPos(5) = new Model(mmSideEast.make())
    }

    def update(rayTraceNew: RayTraceResult): Unit = {

        if (rayTrace != rayTraceNew) {
            rayTrace = rayTraceNew

            rayTrace.traceType match {
                case RayTraceType.VOID =>

                case RayTraceType.BLOCK =>
                    if (blockMouseOver.mesh != null) {
                        blockMouseOver.mesh.cleanUp()
                    }
                    val mm = MeshMaker.startMake(GL_LINES)

                    val aabb = rayTrace.blockState.getBoundingBox

                    val minX = aabb.minX - 0.01f
                    val minY = aabb.minY - 0.01f
                    val minZ = aabb.minZ - 0.01f
                    val maxX = aabb.maxX + 0.01f
                    val maxY = aabb.maxY + 0.01f
                    val maxZ = aabb.maxZ + 0.01f


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


                    blockMouseOver.mesh = mm.make()


                    blockMouseOver.posX = rayTrace.blockState.x
                    blockMouseOver.posY = rayTrace.blockState.y
                    blockMouseOver.posZ = rayTrace.blockState.z

                    selectPos.foreach(
                        m => {
                            //TODO Проблема с округлением
                            m.posX = rayTrace.blockState.x + (rayTrace.hitVec.x * 16).toInt / 16f
                            m.posY = rayTrace.blockState.y + (rayTrace.hitVec.y * 16).toInt / 16f
                            m.posZ = rayTrace.blockState.z + (rayTrace.hitVec.z * 16).toInt / 16f
                        }
                    )


                case RayTraceType.ENTITY =>
            }
        }


    }

    def render(currentShaderProgram: Shader): Unit = {
        if (rayTrace != RayTraceResult.VOID) {
            renderBlockMouseOver(currentShaderProgram)
            renderSelectPos(currentShaderProgram)
        }
    }


    def renderBlockMouseOver(shader: Shader): Unit = {
        blockMouseOver.render(shader)
    }

    def renderSelectPos(shader: Shader): Unit = {
        selectPos(rayTrace.sideHit.id).render(shader)
    }

}
