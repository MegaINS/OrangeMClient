package ru.megains.orangemc.render.gui.base

import java.awt.Color

import ru.megains.mge.render.MContainer
import ru.megains.mge.render.mesh.MeshMaker
import ru.megains.mge.render.model.Model
import ru.megains.orangemc.scene.GameScene

private[base] abstract class Gui extends MContainer {



    var gameScene: GameScene = _

    def init(gameSceneIn: GameScene): Unit ={
        gameScene = gameSceneIn
        init()
    }

    def init(): Unit

    def resize(width:Int,height:Int):Unit

}

object Gui{
    val xZero = 0.0f
    val yZero = 0.0f
    val zZero = 0.0f
    def createRect(width: Int, height: Int, color: Color): Model = {

        val mm = MeshMaker.startMakeTriangles()
        mm.addColorRGBA(color.getRed, color.getGreen, color.getBlue, color.getAlpha)
        mm.addVertex(xZero, zZero, zZero)
        mm.addVertex(xZero, height, zZero)
        mm.addVertex(width, height, zZero)
        mm.addVertex(width, zZero, zZero)
        mm.addIndex(0, 1, 2)
        mm.addIndex(0, 2, 3)
        new Model(mm.make())

    }

}
