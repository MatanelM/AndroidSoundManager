
![WhatsApp Image 2023-05-15 at 00 42 03](https://github.com/MatanelM/AndroidSoundManager/assets/33670032/6a2eb45c-4b96-44d2-8b86-1f97d6968e7d)
[![](https://jitpack.io/v/MatanelM/AndroidSoundManager.svg)](https://jitpack.io/#MatanelM/AndroidSoundManager)

# SoundManager

Making an android application that can make sound is a trivial goal for an android developer.
Working with sounds and android can be simple when you have one type of sound and the application is always running, but an application that handles a variety of sounds is already requiring a different project structure.

### SoundManager: Simplifying Sound Implementation
SoundManager provides a module that simplifies this problem. Its letting you reach your app's sound implementation faster, while it provides an intuitive API. SoundManager is handling playback control, synchronization, volume adjustments, and resource management, abstracting the complexity and simplifying sound implementation by keeping the boilerplate code under the hood.

### Features

* <b>Playback Control and Synchronization:</b> Smoothly control playback, synchronize multiple sounds, and handle pausing, resuming, and stopping sounds individually or collectively.
* <b>Easy Sound Integration: </b>Easily add and play sounds in your application using the intuitive SoundManager API.
* <b>Abstraction of Complexity: </b>SoundManager abstracts the complexities of handling multiple sounds, allowing you to focus on the high-level logic of sound management.
* <b>Resource Management: </b>SoundManager simplifies sound resource handling, automatically managing loading and unloading of sound files.

### Getting Started

* <b> Download and add the SoundManager library to your Android project's dependencies: </b> Add it in your root setting.gradle at the end of repositories:
```
dependencyResolutionManagement {
      repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
      repositories {
            mavenCentral()
            maven { url 'https://jitpack.io' }
      }
}
```

<b> Step 2. Add the dependency </b> Add it in your app build.gradle at the end of dependencies:
```
dependencies {
        implementation 'com.github.MatanelM:AndroidSoundManager:2.2.3'
}

```

* <b> Initialize the SoundManager instance in your application's entry point: </b> Initialization can be performed in the onCreate() method of the Application class or the main activity. Pick which method you like and add the following:
```
SoundManager.init();
```

* <b> Add sound files to the SoundManager's sound pool: </b> Whenever a sound is being played, it is added to the pool and get's an identifier. This identifier can be used to make follow-up control function on the original sound. Keep the identifier in the class variable in order to use the sound file while it is playing.
```
button1.setOnClickListener(l -> {
      mainSoundId = SoundManager.getInstance().makeSoundInLoop(this, R.raw.main);
});

button2.setOnClickListener(l -> {
    SoundManager.getInstance().rewind(mainSoundId, 4);
});
        
```

* <b> Use the SoundManager API to easily control playback, adjust volume, and manage the sounds in your application.</b>
```
button3.setOnClickListener(l -> {
    SoundManager.getInstance().toggleMute();
});

button4.setOnClickListener(l -> {
    SoundManager.getInstance().stopAllSounds();
});
```
## Example

https://github.com/MatanelM/AndroidSoundManager/assets/33670032/657144da-0333-417a-aee9-a3f72be1590b

### Contributing
Contributions are welcome!



