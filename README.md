## Draggable-Modifier-Compose
<p>
  <a href="#"><img alt="Android Language Badge" src="https://badgen.net/badge/OS/Android?icon=https://raw.githubusercontent.com/androiddevnotes/learn-jetpack-compose-android/master/assets/android.svg&color=3ddc84"/></a>
  <a href="#"><img alt="Kotlin Language Badge" src="https://badgen.net/badge/language/Kotlin?icon=https://raw.githubusercontent.com/androiddevnotes/learn-jetpack-compose-android/master/assets/kotlin.svg&color=f18e33"/></a>
  <a href="https://github.com/amit-bhandari"><img alt="amit-bhandari GitHub badge" src="https://badgen.net/badge/GitHub/amit-bhandari?icon=github&color=24292e"/></a>
</p>


Modifier which can be used to apply drag gesture to any compose ui component. 

<p align="left">
  <img src="https://github.com/user-attachments/assets/b673a7e1-e45a-469b-bebf-ad21ffb13ae7" width="200" style="margin-right: 20px;"/>
  <img src="https://github.com/user-attachments/assets/31f51365-1d67-4fa5-9c5f-42046fd38942" width="200" style="margin-right: 20px;"/>
  <img src="https://github.com/user-attachments/assets/512e0a55-f102-45a4-8621-05d1ed6b53ff" width="200" style="margin-right: 20px;"/>
  <img src="https://github.com/user-attachments/assets/76e11f8c-9517-4b62-ba0a-7ef85911d94f" width="200" />
</p>

## API
Modifier can be applied to any compose UI component simply by attaching it 
```
Card(
  modifier = Modifier
    .getDraggableModifier(
      direction = Direction.LEFT,
      mode = DragMode.SNAP,
      percentShow = 0.2f,
      snapThreshold = 0.5f,
      maxReveal = 0.8f,
      percentRevealListener = { /* visibility percentage updates here */ }
  )
)
```

## Customizations available 
- **direction**
  - Direction in which drag motion needs to be applied
  - `enum class Direction { DOWN, UP, RIGHT, LEFT }`
- **mode**
  - `DragMode.SNAP`: Leaving drag gesture would result in layout snapping in reveal or collapsed state
  - `DragMode.HOLD`: Layout will stay in same position as it is
- **percentShow**
  - Part of component which would be visible initially.
  - Example: For `Direction.UP` and `percentShow` of `0.1f`, 90% of component would be shifted down initially which user can drag up.
- **maxReveal**
  - Maximum percentage of component to reveal on drag gesture.
  - Defaults to 1.0
- **snapThreshold**
  - Value at which decision of whether to move component to fully reveal state or collapsed state is made.
- **percentRevealListener**
  - Callback which can be used to adjust other layout with respect to dragging of original component.
  - Example: In videos above, batman is scaled with respect to drag motion.

## Thank You!
- Give a ⭐️ if you like my work, good will costs you nothing!


## License

```
The MIT License (MIT)

Copyright (c) 2016 Danylyk Dmytro

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
 
