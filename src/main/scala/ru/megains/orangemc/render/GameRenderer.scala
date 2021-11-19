package ru.megains.orangemc.render

import imgui.gl3.ImGuiImplGl3
import imgui.glfw.ImGuiImplGlfw
import imgui.ImGui
import imgui.flag.ImGuiConfigFlags
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW._
import ru.megains.mge.Window
import ru.megains.mge.render.camera.PerspectiveCamera
import ru.megains.mge.render.shader.Shader
import ru.megains.orangemc.render.shader.{SkyBoxShader, WorldShader}
import ru.megains.orangemc.scene.GameScene
import ru.megains.orangemc.utils.Logger
import org.lwjgl.opengl.GL11._
import ru.megains.mge.render.mesh.MeshMaker
import ru.megains.mge.render.model.Model
import ru.megains.mge.render.texture.Texture
import ru.megains.orangemc.render.block.RenderBlock

import java.awt.Color

class GameRenderer(gameScene: GameScene) extends Logger[GameRenderer] {


    val Z_NEAR: Float = 0.01f
    val Z_FAR: Float = 1000f
    val FOV: Float = Math.toRadians(45.0f).toFloat

    var worldShader: Shader = new WorldShader()
    var worldCamera: PerspectiveCamera = new PerspectiveCamera(FOV, Window.width, Window.height, Z_NEAR, Z_FAR)
    var worldRenderer: WorldRenderer = new WorldRenderer(gameScene.world)
    var skyBoxRenderer = new SkyBoxRenderer()
    var rayTraceRender: RayTraceRender = new RayTraceRender(gameScene)
    var blockPlaceSetRender: BlockSetPositionRender = new BlockSetPositionRender()




    def init(): Unit = {

        worldShader.create()

        rayTraceRender.init()
        skyBoxRenderer.init()

    }


    def update(): Unit = {
        rayTraceRender.update(gameScene.rayTrace)
        blockPlaceSetRender.update(gameScene.blockSetPosition)
    }

    def render(): Unit = {

        renderWorld()
        skyBoxRenderer.render(worldCamera)
    }


    def renderWorld(): Unit = {

        worldCamera.setPerspective(FOV, Window.width, Window.height, Z_NEAR, Z_FAR)
        worldCamera.setPos(gameScene.player.posX, gameScene.player.posY + gameScene.player.levelView, gameScene.player.posZ)
        worldCamera.setRot(gameScene.player.rotPitch, gameScene.player.rotYaw, 0)

        glEnable(GL_DEPTH_TEST)
        glEnable(GL_CULL_FACE)

        worldShader.bind()
        worldShader.setUniform(worldCamera)

        worldRenderer.render(gameScene.player, worldShader)

        glDisable(GL_DEPTH_TEST)
        rayTraceRender.render(worldShader)
        blockPlaceSetRender.render(worldShader)
        worldShader.unbind()
    }

}


