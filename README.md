# minecraft-clone

### Description
A simple clone of Minecraft made with JavaFX. This is not meant to be a direct clone of Mojang's work but rather a project to learn how 3D graphics work and more advanced OOP concepts. The main feature of this project will be a mesh-generator (this is where the logic lies) that generates a simple optimized voxel mesh that will be sent to the rendering pipeline. It will also involve a camera-object that can be controlled in 3D space.

### Classes
![Class diagram](https://user-images.githubusercontent.com/37273026/156928043-8f234949-32b5-48e6-b691-20772b4a5724.svg)
![Culling Example](https://0fps.files.wordpress.com/2012/06/culled8x8x8.png?w=300&h=288)

### File handling
The game will involve a save-to-file feature where chunk- and block-data gets saved to XML file. Chunk's consist of matrices, so this is how the data will be read.

## Prerequisites
To run this application requires the following:
- [Java JRE](https://www.java.com/en/download/)
- [JavaFX 18](https://gluonhq.com/products/javafx/)

## Features
Currently looking to implement these features:
- [ ] [Camera-controls](https://github.com/mattiastofte/minecraft-clone/issues/1)
- [ ] [Voxel-mesh generator](https://github.com/mattiastofte/minecraft-clone/issues/2)
- [ ] [Data-structure](https://github.com/mattiastofte/minecraft-clone/issues/3)
- [ ] [Block modification](https://github.com/mattiastofte/minecraft-clone/issues/4)

## Resources
I've used these resources to plan and design my code:
- [Meshing in a Minecraft Game](https://0fps.net/2012/06/30/meshing-in-a-minecraft-game/)

*Made with ♡ by Mattias Tofte.*
