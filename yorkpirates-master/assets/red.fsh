#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif
varying LOWP vec4 vColor;
varying vec2 vTexCoord;
uniform sampler2D u_texture;
uniform float grayscale;
void main() {
    vec4 texColor = texture2D(u_texture, vTexCoord);

    // v Make Grayscale v
    //float gray = dot(texColor.rgb, vec3(0.96, 0, 0));
    //texColor.rgb = mix(vec3(gray), texColor.rgb, grayscale);

    // v Make Color Tint v
    vec3 red = vec3(1., 0., 0.);
    texColor.rgb = mix(red, texColor.rgb, 0.7);

    gl_FragColor = texColor * vColor;
}