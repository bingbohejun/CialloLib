#version 150

uniform sampler2D Sampler0;
uniform vec4 ColorModulator;

uniform vec2 CialloLibRectanglePos;
uniform vec2 CialloLibTileDimensions;
uniform vec4 CialloLibTileUvs;

in vec2 vertexPosition;
out vec4 fragColor;

float lerp(float time, float a, float b) {
    return (1 - time) * a + time * b;
}

vec2 getTextureCoords(vec2 pos) {
    float deltaX = mod(pos.x - CialloLibRectanglePos.x, CialloLibTileDimensions.x) / CialloLibTileDimensions.x;
    float deltaY = mod(pos.y - CialloLibRectanglePos.y, CialloLibTileDimensions.y) / CialloLibTileDimensions.y;
    return vec2(
            lerp(deltaX, CialloLibTileUvs.x, CialloLibTileUvs.z),
            lerp(deltaY, CialloLibTileUvs.y, CialloLibTileUvs.w)
    );
}

void main() {
    vec4 color = texture(Sampler0, getTextureCoords(vertexPosition));
    if (color.a < 0.1) {
        discard;
    }
    fragColor = color * ColorModulator;
}
