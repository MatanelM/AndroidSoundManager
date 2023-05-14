package com.example.soundmanager;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;

public class SoundManager {

    // this class purpose is to manage the sounds running on the application
    // if an activity is closed it's the activity job to tell the manager
    // to close the sound as well, or to find a solution for keeping the
    // track over the sound that it has been activated in another way.

    // the sound manager can activate a sound when it has the id of the sound.
    // the sound manager can activate the sound in loop or not in a loop
    // the sound manager keeps track of it's sounds
    private static int counter = 0;
    private static SoundManager _instance = null;

    // a map has id:sound key value pair, to track the sounds on the manager
    private HashMap<Integer, MediaPlayer> map = new HashMap<>();
    private int mainSoundId = 1;

    private boolean isMuted = false; // Flag to track mute state


    private SoundManager(){

    }

    public static synchronized void init(){
        if ( _instance == null ) {
            _instance = new SoundManager();
        }
    }

    public boolean isMuted() {return isMuted;}

    public static synchronized  SoundManager getInstance(){
        return _instance;
    }

    public int makeSoundInLoop(Context context, int id){
        SoundManager.counter += 1;

        BackgroundSoundLoop sound = new BackgroundSoundLoop(context, id);
        sound.execute();
        MediaPlayer mp = sound.makeSoundInLoop();
        setVolume(mp);
        this.map.put(counter, mp);
        return counter;
    }
    public int makeSoundNotInLoop(Context context, int id){
        SoundManager.counter += 1;

        BackgroundSoundNoLoop sound = new BackgroundSoundNoLoop(context, id);
        sound.execute();
        MediaPlayer mp = sound.makeSoundNoLoop();
        setVolume(mp);
        this.map.put(counter, mp);
        return counter;
    }

    public void stopSound(int num){
        if (  this.map.get(num) != null &&  this.map.get(num).isPlaying())
            this.map.get(num).stop();
    }

    public void stopAllSounds(){
        for (Map.Entry<Integer, MediaPlayer> entry: this.map.entrySet()) {
            this.stopSound(entry.getKey());
        }
    }

    public void stopMainSound() {
        this.stopSound(this.mainSoundId);
    }

    public void startMainSound(Context context, int id){
        this.mainSoundId = makeSoundInLoop(context, id);
    }

    public void pauseSound(int num){
        if (this.map.get(num) != null && this.map.get(num).isPlaying()) {
            this.map.get(num).pause();
        }
    }

    public void resumeSound(int num){
        if (this.map.get(num) != null && !this.map.get(num).isPlaying()) {
            this.map.get(num).start();
        }
    }

    public void forward(int num, float seconds){

        if ( this.map.get(num) == null) return;

        MediaPlayer sound = this.map.get(num);
        // Forward 5 seconds
        int currentPosition = sound.getCurrentPosition();
        int duration = sound.getDuration();
        int forwardPosition = currentPosition + (int)seconds*1000; // 5 seconds in milliseconds

        if (forwardPosition > duration) {
            forwardPosition = duration; // Ensure position does not exceed duration
        }

        sound.seekTo(forwardPosition);
    }

    public void rewind(int num, float seconds){

        if ( this.map.get(num) == null) return;

        MediaPlayer sound = this.map.get(num);

        int  currentPosition = sound.getCurrentPosition();
        int rewindPosition = currentPosition - (int)seconds*1000; // 5 seconds in milliseconds

        if (rewindPosition < 0) {
            rewindPosition = 0; // Ensure position does not go below zero
        }

        sound.seekTo(rewindPosition);
    }

    public void toggleMute() {
        isMuted = !isMuted;
        for (Map.Entry<Integer, MediaPlayer> entry: this.map.entrySet()) {
            MediaPlayer sound = entry.getValue();
            if ( sound == null || !sound.isPlaying() ) continue;
            setVolume(sound);
        }
    }
    private void setVolume(MediaPlayer sound){
        float volume = isMuted ? 0.0f : 1.0f;
        sound.setVolume(volume, volume);
    }
}

