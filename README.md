# minecraft-clone

## Description
A simple clone of Minecraft made with Java and JavaFX. This is a university project to learn 3D graphics and more advanced object-oriented programming concepts. The main features of this project will be a voxel/chunk system, a mesh generator, world modification, controllable player/camera and a simple physics implementation. The mesh is made from triangles and optimized with a simple culling algorithm before it gets sent to the rendering pipeline.

#### Classes
<img src="https://user-images.githubusercontent.com/37273026/156928351-0b802189-8e3c-4f12-89b6-03785dc9726a.png" width="600">

#### File handling
The game will involve a save-to-file feature where the chunk data gets serialized and saved to a .ser file (file extension made by Oracle). The chunk data consist of a three dimensional matrix, and this is how the data will be read/written.

<br>

## Features
Some of the features that I'm looking to implement:
- [x] [Camera-controls](https://github.com/mattiastofte/minecraft-clone/issues/1)
- [x] [Voxel-mesh generator](https://github.com/mattiastofte/minecraft-clone/issues/2)
- [x] [Data-structure](https://github.com/mattiastofte/minecraft-clone/issues/3)
- [ ] [Block modification](https://github.com/mattiastofte/minecraft-clone/issues/4)
<br>

![Voxel_Mesh_Culling](https://user-images.githubusercontent.com/37273026/157760535-35f8b997-cc3b-40a5-ad4c-bf750b181b10.gif)

#### Mesh generator
Iterating over all the voxels and generating cubes with all six faces is not efficient. The time complexity of this method is almost linear in the number of voxels, which makes this method unacceptable for a game that consists of thousands of voxels. The solution is to cull out the interior faces, leaving only quads (faces) on the surface. 

By using the dimensional analysis argument, where we assume a more-or-less spherical geometry, we would expect to generate about O(n^3) quads. On the other hand, the culled version would only have about O(n^2) quads, which is a noticable improvement. 

<img src="https://user-images.githubusercontent.com/37273026/156931462-27901f1f-9a29-4eab-b7d3-2005bc5e34b2.png" width="220"> <img src="https://user-images.githubusercontent.com/37273026/156931472-5b4e451c-c5c9-4a29-b70a-1a12c7d6d312.png" width="207">

#### Depth buffer

This game has a simple depth buffer, also known as a z-buffer. This is a type of data buffer used in computer graphics to represent depth information of objects in 3D space from a particular perspective. Depth buffers are an aid to rendering a scene to ensure that the correct polygons properly occlude other polygons.

<img src="https://www.cyanilux.com/tutorials/depth/Preview.png" width="220">

<br>

## Prerequisites
To run this application requires the following:
- [Java JRE](https://www.java.com/en/download/)
- [JavaFX 18](https://gluonhq.com/products/javafx/)

<br>

## Resources
I've used these resources to plan and design my code:
- [Meshing in a Minecraft Game](https://0fps.net/2012/06/30/meshing-in-a-minecraft-game/)
- https://www.javatpoint.com/javafx-rotate-transition

<br>

*Made with ♡ by Mattias Tofte.*
