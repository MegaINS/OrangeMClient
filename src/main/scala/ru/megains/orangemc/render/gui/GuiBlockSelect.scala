package ru.megains.orangemc.render.gui

import ru.megains.mge.render.text.Text
import ru.megains.orangem.utils.{RayTraceResult, RayTraceType}
import ru.megains.orangemc.render.gui.base.GuiUI

class GuiBlockSelect() extends GuiUI {

    var ray: RayTraceResult = RayTraceResult.VOID


    var name:Text = _
    var block_name:Text = _
    var block_side:Text = _
    var block_x:Text = _
    var block_y:Text = _
    var block_z:Text = _

    override def init(){
        posY = 180
        posZ = 100
        name = new Text("Block select:")
        block_name = new Text(""){
            posY = 20
        }
        block_x = new Text(""){
            posY = 40
        }
        block_y = new Text(""){
            posY = 60
        }
        block_z = new Text(""){
            posY = 80
        }
        block_side = new Text(""){
            posY = 100
        }

        addChildren(name,block_name,block_side,block_x,block_y,block_z)
    }

    override def update(): Unit ={


        if (gameScene.rayTrace != ray) {
            ray =gameScene.rayTrace

            ray.traceType match {
                case RayTraceType.VOID =>
                    block_name.text = ""
                    block_x.text = ""
                    block_y.text = ""
                    block_z.text = ""
                    block_side.text = ""
                case RayTraceType.BLOCK =>
                    block_name.text = "name: "+ray.blockState.block.name
                    block_x.text = "x: "+ray.blockState.x.toString
                    block_y.text = "y: "+ray.blockState.y.toString
                    block_z.text = "z: "+ray.blockState.z.toString
                    block_side.text = "side: "+ray.sideHit.name
                case RayTraceType.ENTITY =>

            }


        }
    }
}
