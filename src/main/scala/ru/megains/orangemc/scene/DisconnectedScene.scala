package ru.megains.orangemc.scene

import ru.megains.mge.Scene
import ru.megains.orangemc.OrangeMClient

class DisconnectedScene(multiPlayerScene:MultiPlayerScene,orangeM: OrangeMClient,text1:String,text2:String) extends Scene{
    override def runTickKeyboard(key: Int, action: Int, mods: Int): Unit = ???

    override def init(): Unit = ???

    override def render(): Unit = ???

    override def update(): Unit = ???

    override def destroy(): Unit = ???

    override def runTickMouse(button: Int, buttonState: Boolean): Unit = ???
}
