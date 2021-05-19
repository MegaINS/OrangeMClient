#version 400

uniform sampler2D textureMap;
uniform bvec2 dataTC;



in Vertex{
    vec4 color;
    vec2 texCoord;
} Ver;

out vec4 fragColor;

void main() {


    if (dataTC[1]){
        fragColor = Ver.color;
    } else {
        fragColor = vec4(1);
    }


    if (dataTC[0]){
        fragColor *= texture(textureMap, Ver.texCoord);
    }

}
