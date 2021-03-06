package ru.megains.orangemc.network.handler

import ru.megains.mge.Scene
import ru.megains.orangem.network.NetworkManager
import ru.megains.orangem.network.handler.{INetHandler, INetHandlerPlayClient}
import ru.megains.orangem.network.packet.Packet
import ru.megains.orangem.network.packet.play.server.{SPacketBlockChange, SPacketChangeGameState, SPacketChunkData, SPacketHeldItemChange, SPacketJoinGame, SPacketMultiBlockChange, SPacketPlayerListItem, SPacketPlayerPosLook, SPacketSetSlot, SPacketWindowItems}
import ru.megains.orangemc.OrangeMClient
import ru.megains.orangemc.utils.Logger

class NetHandlerPlayClient(gameController: OrangeMClient, previousScene: Scene, val netManager: NetworkManager) extends INetHandlerPlayClient with Logger[NetHandlerPlayClient] {


  //  var clientWorldController: WorldClient = _
    var doneLoadingTerrain: Boolean = false


    def handleHeldItemChange(packetIn: SPacketHeldItemChange): Unit = {
       // if (InventoryPlayer.isHotBar(packetIn.heldItemHotbarIndex)) this.gameController.player.inventory.stackSelect = packetIn.heldItemHotbarIndex
    }

    override def onDisconnect(msg: String): Unit = {

    }

    override def sendPacket(packetIn: Packet[_ <: INetHandler]) {
        netManager.sendPacket(packetIn)
    }

    override def handleJoinGame(packetIn: SPacketJoinGame): Unit = {

//        gameController.playerController = new PlayerControllerSP(gameController, this)
//        clientWorldController = new WorldClient(this)
//        gameController.loadWorld(clientWorldController)
//        gameController.guiManager.setGuiScreen(new GuiDownloadTerrain(this))
//        // gameController.player.setEntityId(packetIn.getPlayerId)
//        //  currentServerMaxPlayers = packetIn.getMaxPlayers
//        // gameController.player.setReducedDebug(packetIn.isReducedDebugInfo)
//
//        //  gameController.gameSettings.sendSettingsToServer()
//        //  netManager.sendPacket(new CPacketCustomPayload("MC|Brand", new PacketBuffer(Unpooled.buffer).writeString(ClientBrandRetriever.getClientModName)))
    }

    override def handlePlayerPosLook(packetIn: SPacketPlayerPosLook): Unit = {

//       // val entityplayer: EntityPlayer = gameController.player
//        var d0: Float = packetIn.x
//        var d1: Float = packetIn.y
//        var d2: Float = packetIn.z
//        var f: Float = packetIn.yaw
//        var f1: Float = packetIn.pitch
//        if (packetIn.flags.contains(SPacketPlayerPosLook.EnumFlags.X)) d0 += entityplayer.posX
//        else entityplayer.motionX = 0.0f
//        if (packetIn.flags.contains(SPacketPlayerPosLook.EnumFlags.Y)) d1 += entityplayer.posY
//        else entityplayer.motionY = 0.0f
//        if (packetIn.flags.contains(SPacketPlayerPosLook.EnumFlags.Z)) d2 += entityplayer.posZ
//        else entityplayer.motionZ = 0.0f
//        if (packetIn.flags.contains(SPacketPlayerPosLook.EnumFlags.X_ROT)) f1 += entityplayer.rotationPitch
//        if (packetIn.flags.contains(SPacketPlayerPosLook.EnumFlags.Y_ROT)) f += entityplayer.rotationYaw
//      //  entityplayer.setPositionAndRotation(d0, d1, d2, f, f1)
//        // netManager.sendPacket(new CPacketConfirmTeleport(packetIn.getTeleportId))
//
//        netManager.sendPacket(new CPacketPlayer.PositionRotation(entityplayer.posX, entityplayer.body.minY, entityplayer.posZ, entityplayer.rotationYaw, entityplayer.rotationPitch, false))
//        if (!doneLoadingTerrain) {
//            gameController.player.prevPosX = gameController.player.posX
//            gameController.player.prevPosY = gameController.player.posY
//            gameController.player.prevPosZ = gameController.player.posZ
//            doneLoadingTerrain = true
//            gameController.guiManager.setGuiScreen(null)
//        }
    }

    def handleChunkData(packetIn: SPacketChunkData): Unit = {

//        clientWorldController.doPreChunk(packetIn.position, loadChunk = true)
//        if(!packetIn.chunkVoid){
//            val chunk = clientWorldController.getChunk(packetIn.position)
//            chunk.blockStorage = packetIn.blockStorage
//            packetIn.tileEntityMap.foreach(chunk.addTileEntity)
//            gameController.worldRenderer.reRender(packetIn.position)
//        }



    }


    def handleBlockChange(packetIn: SPacketBlockChange) {
      //  clientWorldController.invalidateRegionAndSetBlock(packetIn.block)

    }

    def handleMultiBlockChange(packetIn: SPacketMultiBlockChange) {
        for (blockData <- packetIn.changedBlocks) {
           // clientWorldController.invalidateRegionAndSetBlock(blockData)
        }
    }

    override def handleSetSlot(packetIn: SPacketSetSlot): Unit = {
        if (packetIn.slot == -1) {
          //  gameController.player.inventory.itemStack = packetIn.item
        } else {
          //  gameController.player.openContainer.putStackInSlot(packetIn.slot, packetIn.item)
        }

    }

    override def handleWindowItems(packetIn: SPacketWindowItems): Unit = {
      //  val openContainer = gameController.player.openContainer

        for (i <- packetIn.itemStacks.indices) {
          //  openContainer.putStackInSlot(i, packetIn.itemStacks(i))
        }
    }

    override def handlePlayerListItem(packetIn: SPacketPlayerListItem): Unit = {

    }

    override def handleChangeGameState(packetIn: SPacketChangeGameState): Unit = {
        packetIn.state match {
           // case 3 => gameController.playerController.setGameType(GameType(packetIn.value))
            case _ =>
        }
    }

    override def disconnect(msg: String): Unit = {

    }
}


