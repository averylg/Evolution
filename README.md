Evolution
=========
A project created by awesomelemonade. First versions created in 2012 (Not uploaded to github). First commit in this version dates back to 2015. This project is a playground for experimenting with various ideas I have learned throughout my experience with programming.

Notable Highlights
------------------
* Reflection: Dynamic loading of classes via Java's reflection and annotations
* Event system using Java reflection (@Subscribe annotation)
* Signed Distance Field Font Rendering: Implemented a technique shown in [this paper](https://steamcdn-a.akamaihd.net/apps/valve/2007/SIGGRAPH2007_AlphaTestedMagnification.pdf)
* OpenGL 3+ Rendering (Vertex Arrays, Vertex Buffers, Shaders, Shader Programs, GLSL, Uniform Variables, etc)
* Object model loading (ObjLoader) using .obj files for 3D rendering
* TimeSync using lwjgl's implementation of sync() to sync frame rates - [discussion here](http://forum.lwjgl.org/index.php?topic=5653.0)
* Multithreading (Splitting of update() and render(), Calculating Perlin Noise)
* Cubemap rendering technique for skyboxes
* Terrain generation using [perlin noise]( https://web.archive.org/web/20160325134143/http://freespace.virgin.net/hugo.elias/models/m_perlin.htm)
* Raytracing with Moller Trumbore Intersection algorithm
* Linear Algebra library (Matrix, Vector)
* 3D Physics Library ([Collision Detection/Response System](http://www.peroxide.dk/papers/collision/collision.pdf))
* Window & Input Handling with GLFW + LWJGL 3
* Animations & Interpolations using Bezier Curves
* Cantor & [Szudzik Pairing](http://szudzik.com/ElegantPairing.pdf) function implementations
* MurMur hash implementation

Plans
-----
* Marching Cube & Marching Tetrahedra for destructible terrain - [0fps](https://0fps.net/2012/07/12/smooth-voxel-terrain-part-2/), [paulbourke](http://paulbourke.net/geometry/polygonise/), [Stanford-mdfisher](https://graphics.stanford.edu/~mdfisher/MarchingCubes.html) - [Potential research exploration](http://www.geometry.caltech.edu/pubs/ACTD07.pdf)
* Terrain generation with overhangs and caves - [stackoverflow](https://stackoverflow.com/questions/39695764/generating-voxel-overhangs-with-3d-noise), http://accidentalnoise.sourceforge.net/minecraftworlds.html
* Particle System using OpenGL's instanced rendering with glDrawElementsInstanced and glVertexAttribDivisor

Ideas
-----
* Multitexturing for terrain
* Normal mapping for textures
* Reimplement Phong Lighting
* "Flood-Fill" lighting for marching cube models
* Remove magic values from creation of vertexArray and vertexBuffer in favor of linking them with ShaderProgram

Other
-----
LWJGL Version: 3.2.3 release (Downloaded 27 Nov 19)
