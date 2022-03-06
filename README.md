# minecraft-clone

## Description
A simple clone of Minecraft made with JavaFX. This is not meant to be a direct clone of Mojang's work but rather a project to learn how 3D graphics work and more advanced OOP concepts. The main feature of this project will be a mesh-generator (this is where the logic lies) that generates a simple optimized voxel mesh that will be sent to the rendering pipeline. It will also involve a camera-object that can be controlled in 3D space.

#### Classes
<img src="https://user-images.githubusercontent.com/37273026/156928351-0b802189-8e3c-4f12-89b6-03785dc9726a.png" width="600">

#### File handling
The game will involve a save-to-file feature where chunk- and block-data gets saved to XML file. Chunk's consist of matrices, so this is how the data will be read.

<br>

## Features
Currently looking to implement these features:
- [ ] [Camera-controls](https://github.com/mattiastofte/minecraft-clone/issues/1)
- [ ] [Voxel-mesh generator](https://github.com/mattiastofte/minecraft-clone/issues/2)
- [ ] [Data-structure](https://github.com/mattiastofte/minecraft-clone/issues/3)
- [ ] [Block modification](https://github.com/mattiastofte/minecraft-clone/issues/4)

#### Mesh generator
It is not very efficient to generate a voxel grid mesh by iterating over each voxel and generating many 6-sided cubes per solid voxel. The time complexity of this method is linear in the number of voxels. One obvious improvement is to just cull out the interior faces, leaving only quads on the surface. 

We can try to estimate how much smaller using a dimensional analysis argument:  assuming that our geometry is more-or-less spherical, let n be its radius.  Then, in the stupid method we would expect to generate about O(n^3) quads, while in the culled  version we’d get only O(n^2).  This gives an improvement of a factor of about O(n)

<img src="https://user-images.githubusercontent.com/37273026/156931462-27901f1f-9a29-4eab-b7d3-2005bc5e34b2.png" width="220"> <img src="https://user-images.githubusercontent.com/37273026/156931472-5b4e451c-c5c9-4a29-b70a-1a12c7d6d312.png" width="207">

<br>

## Prerequisites
To run this application requires the following:
- [Java JRE](https://www.java.com/en/download/)
- [JavaFX 18](https://gluonhq.com/products/javafx/)

<br>

## Resources
I've used these resources to plan and design my code:
- [Meshing in a Minecraft Game](https://0fps.net/2012/06/30/meshing-in-a-minecraft-game/)

*Made with ♡ by Mattias Tofte.*
