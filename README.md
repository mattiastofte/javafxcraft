# minecraft-clone

### Description
A simple clone of Minecraft made with JavaFX. This is not meant to be a direct clone of Mojang's work but rather a project to learn how 3D graphics work and more advanced OOP concepts. The main feature of this project will be a mesh-generator (this is where the logic lies) that generates a simple optimized voxel mesh that will be sent to the rendering pipeline. It will also involve a camera-object that can be controlled in 3D space.

### Classes
<img src="https://user-images.githubusercontent.com/37273026/156928351-0b802189-8e3c-4f12-89b6-03785dc9726a.png" width="600">

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
