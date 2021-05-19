package ru.megains.orangemc.render.gui.element

import java.awt.Color

import ru.megains.mge.Mouse
import ru.megains.mge.render.MContainer
import ru.megains.mge.render.model.Model
import ru.megains.mge.render.text.Text
import ru.megains.orangemc.render.gui.base.Gui


class MButton(val buttonText: String, weight: Int, height: Int,func:()=>Unit) extends MContainer {


    val textMesh: Text = new Text(buttonText){
        posX =10
        posY = 16
    }
    val buttonUp: Model = Gui.createRect(weight, height, Color.WHITE)
    val buttonUpOver: Model = Gui.createRect(weight, height, new Color(200,200,200))
    val buttonDisable: Model = Gui.createRect(weight, height, Color.BLACK)
    var enable = true
    var over = false
    var click = false

    buttonUpOver.active = false
    buttonDisable.active = false

    addChildren(buttonUp,buttonUpOver,buttonDisable,textMesh)
//    def draw(mouseX: Int, mouseY: Int): Unit = {
//
//        val background: Model = if (!enable) buttonDisable else if (isMouseOver(mouseX, mouseY)) buttonDown else buttonUp
//
//
//
//       // drawObject(positionX, positionY, 1, background)
//
//      //  drawObject(positionX + 10 , positionY + 18, 1, textMesh)
//
//    }
//
////    def clear(): Unit = {
////       // textMesh.cleanUp()
////        //buttonDown.cleanUp()
////       // buttonUp.cleanUp()
////    }

    override def update(): Unit = {



        if (!enable) {
            buttonDisable.active = true
        } else{
            buttonDisable.active = false

//                if(click) {
//                    posX +=2
//                    posY+=2
//
//                } else {
//                    posX -=2
//                    posY-=2
//
//                }




        }

    }

    override def mouseMove(x: Int, y: Int): Unit = {
        if(over != isMouseOver(Mouse.getX,Mouse.getY)){
            over = isMouseOver(Mouse.getX,Mouse.getY)
            if(over) {
                buttonUp.active = false
                buttonUpOver.active = true

            } else {
                buttonUp.active = true
                buttonUpOver.active = false

            }
        }
    }

    override def mouseClick(x: Int, y: Int): Unit = {
        if( isMouseOver(x: Int, y: Int)){
            func()
        }

    }

    def isMouseOver(mouseX: Int, mouseY: Int): Boolean = {
        enable && mouseX >= posX && mouseX <= posX + weight && mouseY >= posY && mouseY <= posY + height
    }


}
