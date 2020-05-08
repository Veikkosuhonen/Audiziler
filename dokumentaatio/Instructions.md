# Instructions

## Launching the App

[Download](https://github.com/Veikkosuhonen/ot-harjoitustyo/releases/tag/v0.1-alpha) the latest (pre-)release and the config.properties-file. I recommend moving them to a new folder.

To run the executable, you can run it from the command line by navigating to the parent directory and entering

`java -jar Audiziler.jar`. 

Replace `Audiziler.jar` with the exact name of the executable.

Or you can double-click the executable and it should be opened by the JRE. 

_I recommend running any pre-release version from the command line as some error messages may be printed._

**Important**: make sure to have a valid config.properties file located in the same directory as the executable. 
It can be found in the attachments of every release. The app will exit if it cannot find the config file.

## How to use

![ui](https://github.com/Veikkosuhonen/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Audiziler_UI.png)

All the controls are located below the side openers. 
Moving the cursor will reveal the openers and hovering over them will open the corresponding menu.

### Add audio files
from the **left menu**. Supported file types are AIFF, WAV and MP3. Then click a file from the menu to select it for playing.
The app will remember any added files next time you launch it (if they haven't been moved).

### Play
the file from the **bottom menu** which contains the playback controls. You can alternatively click the app window to toggle playback.

### Go to fullscreen 
by pressing 'f' and exit fullscreen by pressing 'esc'

### Open the **right menu**
to control the visualization.

## Controlling the visualization

The visualizer is subject to constant changes and these instructions will quickly become outdated.

As of now, there are three different types of visualization: BARS, FLAME and PHASES (wip). 
The type can be switched from the combo-box at the top of the menu. 
The sliders control different parameters affecting how the visualizations are drawn. 
Currently, all the visualization types have the same group of settings, resulting in somewhat unintuitive naming.
In general,

- the **magnitude color offset** controls how much the change in magnitude of a frequency band changes the color of that bar,

- the **frequency color offset** controls how much the frequency of a band changes the color of that bar,

- the **color offset** offsets the color of all bars by a constant,

- the **height** makes bars longer and particles fly higher,

- the **acceleration** slows down the movement of bars and makes particles accelerate,

- the **bloom** controls how bright objects are made to glow (lower means more glow),

- the **analyzer rate** controls the rate at which spectrum data updates are sampled (pretty useless as of now),

- the **threshold** determines how sensitive the visualizer is to sound.

#### You should experiment to figure out what all these actually do.

## Save files
The app creates files in its parent directory. It is a good idea to have it in its own directory. 

The app saves the settings to a text file defined by the config file. Default would be `settings.txt`. 
This file can be edited, for example the min and max values of settings could be changed. Lines starting with `#` are ignored.

The audio file filepaths are saved to a text file defined by the config file.
