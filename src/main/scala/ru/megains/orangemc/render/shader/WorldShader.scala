package ru.megains.orangemc.render.shader

import ru.megains.mge.render.camera.Camera
import ru.megains.mge.render.shader.Shader


class WorldShader extends Shader {


    override val dir: String = "shaders/basic"

    override def createUniforms(): Unit = {
        createUniform("projectionMatrix")
        createUniform("viewMatrix" )
        createUniform("modelMatrix")
        createUniform("dataTC")
    }

    override def setUniform(camera: Camera): Unit = {
        setUniform("projectionMatrix", camera.buildProjectionMatrix())
        setUniform("viewMatrix", camera.buildViewMatrix())
    }

}
