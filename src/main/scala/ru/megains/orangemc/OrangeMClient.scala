package ru.megains.orangemc

import org.lwjgl.glfw.GLFW.glfwPollEvents
import ru.megains.mge.{Game, Mouse, Scene, Window}
import ru.megains.orangem.PacketProcess
import ru.megains.orangem.network.NetworkManager
import ru.megains.orangemc.register.Bootstrap
import ru.megains.orangem.utils.Timer
import ru.megains.orangemc.render.texture.GameTextureManager
import ru.megains.orangemc.scene.PlayerSelectScene
import ru.megains.orangemc.utils.Logger

import scala.collection.mutable


class OrangeMClient(config: Configuration) extends Logger[OrangeMClient] with Game with  PacketProcess{


    var playerName: String = ""


    var networkManager:NetworkManager = _
    var running = true
    var window = new Window(config.width,config.height,config.title)
    var scene:Scene = _
    val timer = new Timer(20)
    val textureManager = new GameTextureManager()
    val futureTaskQueue: mutable.Queue[()=>Unit] = new mutable.Queue[()=>Unit]
    def start(): Unit ={
        if(init()){
            gameLoop()
            destroy()
        }else{
            error()
        }
    }

    private def gameLoop(): Unit ={

        while (running) {

            if (window.isClose) running = false

            timer.update()

            for (_ <- 0 until timer.tick) {
                update()
            }

            render()
        }
        stop()
    }


    private def init(): Boolean ={

        window.create()
        Mouse.init(this)
        Bootstrap.init()
        textureManager.init()
        setScene(new PlayerSelectScene(this))

        timer.init()
        true
    }

    def stop(): Unit ={
        if(networkManager!= null){
            networkManager.closeChannel("")
        }
    }

    private def update(): Unit = {
        futureTaskQueue synchronized {
            while (futureTaskQueue.nonEmpty){
                val a = futureTaskQueue.dequeue()
                if(a!= null) a()
            }

        }
        scene.update()
        Mouse.update()
    }

    private def render(): Unit = {

        scene.render()
        window.update()
    }

    private def destroy(): Unit = {
        window.destroy()
    }

    private def error(): Unit ={
    }

    override def runTickMouse(button: Int, buttonState: Boolean): Unit = scene.runTickMouse(button,buttonState)

    override def runTickKeyboard(key: Int, action: Int, mods: Int): Unit = scene.runTickKeyboard(key: Int, action: Int, mods: Int)

    override def addPacket(packet:()=>Unit): Unit = {
        futureTaskQueue += packet
    }

    override def setScene(sceneIn: Scene): Unit = {
        if(scene!= null){
            scene.destroy()
        }
        scene = sceneIn
        scene.init()
    }
}

object OrangeMClient{
    def getSystemTime: Long = System.currentTimeMillis
}
