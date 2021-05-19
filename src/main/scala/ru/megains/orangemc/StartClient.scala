package ru.megains.orangemc

import ru.megains.mge.File

object StartClient extends App {


    val config = new Configuration(){
        filePath = System.getProperty("user.dir").replaceAll("\\\\", "/") + "/src/main/resources/"

    }

    File.gamePath = config.filePath
    val game = new OrangeMClient(config)
    game.start()

}
