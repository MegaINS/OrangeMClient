package ru.megains.orangemc.render.gui

import java.awt.Color

import ru.megains.mge.Window
import ru.megains.mge.render.shader.Shader
import ru.megains.mge.render.text.Text
import ru.megains.orangem.entity.EntityPlayer
import ru.megains.orangem.utils.{Direction, FrameCounter}
import ru.megains.orangemc.render.ChunkRenderer
import ru.megains.orangemc.render.gui.base.{Gui, GuiUI}

class GuiDebugInfo() extends GuiUI {

    var name: Text = _
    var position: Text = _
    var position_x: Text = _
    var position_y: Text = _
    var position_z: Text = _
    var position_yaw: Text = _
    var position_pitch: Text = _
    var position_side: Text = _
    var fps: Text = _
    var memory: Text = _
    var chunkUpdate: Text = _
    var chunkRender: Text = _
    var blockRender: Text = _
    var player:EntityPlayer = _

    var x: Float = Float.MinValue
    var y: Float = Float.MinValue
    var z: Float = Float.MinValue
    var yaw: Float = Float.MinValue
    var pitch: Float = Float.MinValue
    var side:Direction = Direction.NONE

    var tickI: Int = 20
    override def init(): Unit = {
        player = gameScene.player
        addChildren(Gui.createRect(200, Window.height, Color.CYAN))
        val height = Window.height

        name = new Text("Name: ")

        position = new Text("Position:") {
            posY = 20
        }
        position_x = new Text("x:") {
            posY = 40
        }
        position_y = new Text("y:") {
            posY = 60
        }
        position_z = new Text("z:") {
            posY = 80
        }
        position_yaw = new Text("yaw:") {
            posY = 100
        }
        position_pitch = new Text("pitch:") {
            posY = 120
        }
        position_side = new Text("side:"){
            posY = 140
        }


        memory = new Text("Memory use:") {
            posY = height -30
        }

        fps = new Text("FPS:") {
            posY = height -50
        }

        chunkUpdate = new Text("Chunk update:") {
            posY = height -70
        }

        chunkRender = new Text("Chunk render:") {
            posY = height -90
        }

        blockRender = new Text("Block render:") {
            posY = height -110
        }
        addChildren(name, memory, fps, position, position_x, position_y, position_z, position_yaw, position_pitch,position_side,chunkUpdate,chunkRender,blockRender)


    }


    override def update() {
        if (player.posX != x) {
            x = player.posX
            position_x.text = "x: " + player.posX
        }
        if (player.posY != y) {
            y = player.posY
            position_y.text =("y: " + player.posY)
        }
        if (player.posZ != z) {
            z = player.posZ
            position_z.text =("z: " + player.posZ)
        }
        if (player.rotYaw != yaw) {
            yaw = player.rotYaw
            position_yaw.text =("yaw: " + player.rotYaw)
        }
        if (player.rotPitch != pitch) {
            pitch = player.rotPitch
            position_pitch.text =("pitch: " + player.rotPitch)
        }
        if (player.side != side) {
            side = player.side
            position_side.text =("side: " + side)
        }


        if (player.name != name.text) {
            name.text =("Name: " + player.name)
        }


        tickI += 1
        if (tickI > 19) {
            tickI = 0
            val usedBytes: Long = (Runtime.getRuntime.totalMemory - Runtime.getRuntime.freeMemory) / 1048576
            fps.text = ("FPS: " + FrameCounter.lastFrames)
            memory.text = ("Memory use: " + usedBytes + "/" + Runtime.getRuntime.totalMemory / 1048576 + "MB")

            chunkUpdate.text =("Chunk update: "+ChunkRenderer.chunkUpdateLast)

            chunkRender.text =("Chunk render: "+ChunkRenderer.chunkRender/ (if (FrameCounter.frames == 0) 1 else FrameCounter.frames))

            blockRender.text =("Block render: "+ChunkRenderer.blockRender/ (if (FrameCounter.frames == 0) 1 else FrameCounter.frames))
        }






    }

    override def render(shader: Shader): Unit = {
        super.render(shader)

    }

}
