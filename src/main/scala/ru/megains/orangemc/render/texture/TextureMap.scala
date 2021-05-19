package ru.megains.orangemc.render.texture

import ru.megains.mge.render.texture.{TTexture, TextureAtlas, TextureData}
import ru.megains.orangemc.register.GameRegisterRender
import ru.megains.orangemc.render.TTextureRegister
import org.lwjgl.opengl.GL11._
import scala.collection.immutable.HashMap
import org.lwjgl.opengl.GL30._

class TextureMap(name:String) extends TTexture(new TextureData(null)) with TTextureRegister{

    var list: List[TextureAtlas] =  _
    var map:Array[Array[Boolean]] = _
    var textureBlockMap: HashMap[String, TextureAtlas] = new HashMap[String, TextureAtlas]
    val missingTexture = new TextureAtlas("textures/missing.png")


    registerTexture()
    loadTextureAtlas()



    def registerTexture(): Unit = {

        textureBlockMap += "missing" -> missingTexture
        GameRegisterRender.blockData.idRender.values.foreach(_.registerTexture(this))
       // GameRegister.itemData.idRender.values.foreach(_.registerTexture(this))
    }

    def loadTextureAtlas(): Unit ={

        list = textureBlockMap.values.toList.sortBy(_.width).reverse
        createTexture()
        updateTexture()
        glBindTexture(GL_TEXTURE_2D, getGlTextureId)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST_MIPMAP_LINEAR)
        glGenerateMipmap(GL_TEXTURE_2D)

    }

    override def registerTexture(textureName : String): TextureAtlas={
        val tTexture = new TextureAtlas("textures/"+textureName+".png")
        if(!tTexture.isMissingTexture){
            textureBlockMap += textureName -> tTexture
            tTexture
        }else{
            println("Missing texture = "+textureName)
            missingTexture
        }
    }

    def updateTexture(): Unit ={
        glBindTexture(GL_TEXTURE_2D, getGlTextureId)
        list.foreach(_.updateTexture(width,height))
    }

    def createTexture(): Unit ={

        width = 1
        height = 1

        map = Array.ofDim[Boolean](width,height)
        list.foreach((tex:TextureAtlas)=>{
            val size:Int = tex.height
            searchBox(size,tex)

        })
        glBindTexture(GL_TEXTURE_2D,getGlTextureId)
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1)

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width , height , 0, GL_RGBA, GL_UNSIGNED_BYTE, 0)
        println("Create texture block map "+width+"-"+height+" pixels")
    }

    def searchBox(size:Int,tex:TextureAtlas): Boolean ={

        for(x <- 0 to (map.length - size);y <- 0 to (map(x).length - size)){
            var boxEmpty: Boolean = true

            for(i<- x until x + size; j<-y until y + size){
                if(map(i)(j)) boxEmpty=false
            }

            if(boxEmpty){
                for(i<-x until x + size; j<-y until y + size){map(i)(j)=true}
                tex.startX =x
                tex.startY =y
                return true
            }
        }
        resizeMap()
        searchBox(size,tex)
    }

    def resizeMap(): Unit ={
        width+=1
        height+=1
        val temp = Array.ofDim[Boolean](width,height)
        for(x<-map.indices;y<- map(x).indices){
            temp(x)(y) = map(x)(y)
        }
        map = temp
    }

}
