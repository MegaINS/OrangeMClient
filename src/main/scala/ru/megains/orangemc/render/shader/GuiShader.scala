package ru.megains.orangemc.render.shader

import ru.megains.mge.render.camera.Camera
import ru.megains.mge.render.shader.Shader

class GuiShader extends Shader {

    override val dir: String = "shaders/2d"

    override def createUniforms(): Unit = {
        createUniform("projectionMatrix")
        createUniform("modelMatrix")
        createUniform("dataTC")

    }

    override def setUniform(camera: Camera): Unit = {
        setUniform("projectionMatrix", camera.buildProjectionMatrix())
    }

}