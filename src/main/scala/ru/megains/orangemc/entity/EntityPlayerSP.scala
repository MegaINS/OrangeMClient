package ru.megains.orangemc.entity

import ru.megains.orangem.entity.EntityPlayer
import ru.megains.orangem.world.World
import ru.megains.orangemc.OrangeMClient
import ru.megains.orangemc.network.handler.NetHandlerPlayClient

class EntityPlayerSP(orangeM: OrangeMClient, world: World, val connection: NetHandlerPlayClient) extends EntityPlayer(" ") {


    private var lastReportedPosX: Double = .0
    private var lastReportedPosY: Double = .0
    private var lastReportedPosZ: Double = .0
    private var lastReportedYaw: Double = .0
    private var lastReportedPitch: Double = .0
    private var positionUpdateTicks: Int = 0
    private var prevOnGround: Boolean = false

    def openInventory(): Unit = {
        openContainer = inventoryContainer
      //  oc.guiManager.setGuiScreen(new GuiPlayerInventory(this))
    }


//    override def openGui(world: World, pos: BlockPos): Unit = {
////        val tileEntity = world.getTileEntity(pos)
////        tileEntity match {
////            case inv:ATileEntityInventory =>
////                val gui = inv.getGui(this)
////                openContainer = gui.inventorySlots
////                Tartess.tartess.guiManager.setGuiScreen(gui)
////            case _=>
////        }
//
//    }

    override def update() {

        super.update()
        onUpdateWalkingPlayer()

    }


    def onUpdateWalkingPlayer() {
//
//        if (isCurrentViewEntity) {
//            val axisalignedbb: AABB = body
//            val d0: Double = posX - lastReportedPosX
//            val d1: Double = axisalignedbb.minY - lastReportedPosY
//            val d2: Double = posZ - lastReportedPosZ
//            val d3: Double = rotationYaw - lastReportedYaw
//            val d4: Double = rotationPitch - lastReportedPitch
//            positionUpdateTicks += 1
//            val flag2: Boolean = d0 * d0 + d1 * d1 + d2 * d2 > 9.0E-4D || positionUpdateTicks >= 20
//            val flag3: Boolean = d3 != 0.0D || d4 != 0.0D
//
//
//            if (flag2 && flag3) connection.sendPacket(new CPacketPlayer.PositionRotation(posX, axisalignedbb.minY, posZ, rotationYaw, rotationPitch, onGround))
//            else if (flag2) connection.sendPacket(new CPacketPlayer.Position(posX, axisalignedbb.minY, posZ, onGround))
//            else if (flag3) connection.sendPacket(new CPacketPlayer.Rotation(rotationYaw, rotationPitch, onGround))
//            else if (prevOnGround != onGround) connection.sendPacket(new CPacketPlayer(onGround))
//            if (flag2) {
//                lastReportedPosX = posX
//                lastReportedPosY = axisalignedbb.minY
//                lastReportedPosZ = posZ
//                positionUpdateTicks = 0
//            }
//            if (flag3) {
//                lastReportedYaw = rotationYaw
//                lastReportedPitch = rotationPitch
//            }
//            prevOnGround = onGround
//
//        }
    }

  //  protected def isCurrentViewEntity: Boolean = oc.renderViewEntity == this
}
