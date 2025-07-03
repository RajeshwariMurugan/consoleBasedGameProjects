package src;

import java.util.*;
import java.io.File;
import javax.sound.sampled.*;

public class MusicPlayer {
    static List<String> playlist = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static Clip clip;
    static int currentTrack = -1;
    static boolean isPlaying = false;

    public static void main(String[] args) {
        // Note: For this to work, you need actual audio files in the specified directory
        File musicDir = new File("music");
        if (musicDir.exists() && musicDir.isDirectory()) {
            File[] files = musicDir.listFiles((dir, name) -> name.endsWith(".wav"));
            if (files != null) {
                for (File file : files) {
                    playlist.add(file.getPath());
                }
            }
        }
        
        if (playlist.isEmpty()) {
            System.out.println("No music files found in 'music' directory.");
            System.out.println("Please add some .wav files to the 'music' folder.");
            return;
        }
        
        while (true) {
            System.out.println("\nMusic Player");
            System.out.println("1. Play");
            System.out.println("2. Pause");
            System.out.println("3. Stop");
            System.out.println("4. Next Track");
            System.out.println("5. Previous Track");
            System.out.println("6. View Playlist");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    play();
                    break;
                case 2:
                    pause();
                    break;
                case 3:
                    stop();
                    break;
                case 4:
                    nextTrack();
                    break;
                case 5:
                    previousTrack();
                    break;
                case 6:
                    viewPlaylist();
                    break;
                case 7:
                    if (clip != null && clip.isOpen()) {
                        clip.close();
                    }
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void play() {
        if (isPlaying) {
            System.out.println("Already playing!");
            return;
        }
        
        if (currentTrack == -1) {
            currentTrack = 0;
        }
        
        try {
            if (clip != null && clip.isOpen()) {
                clip.close();
            }
            
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                new File(playlist.get(currentTrack)).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            isPlaying = true;
            System.out.println("Now playing: " + getTrackName(currentTrack));
        } catch (Exception e) {
            System.out.println("Error playing track: " + e.getMessage());
        }
    }

    static void pause() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            isPlaying = false;
            System.out.println("Playback paused.");
        } else {
            System.out.println("Nothing is playing to pause.");
        }
    }

    static void stop() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
            isPlaying = false;
            System.out.println("Playback stopped.");
        } else {
            System.out.println("Nothing is playing to stop.");
        }
    }

    static void nextTrack() {
        if (currentTrack == -1) {
            currentTrack = 0;
        } else {
            currentTrack = (currentTrack + 1) % playlist.size();
        }
        stop();
        play();
    }

    static void previousTrack() {
        if (currentTrack == -1) {
            currentTrack = 0;
        } else {
            currentTrack = (currentTrack - 1 + playlist.size()) % playlist.size();
        }
        stop();
        play();
    }

    static void viewPlaylist() {
        System.out.println("\nPlaylist:");
        for (int i = 0; i < playlist.size(); i++) {
            String indicator = (i == currentTrack) ? "> " : "  ";
            System.out.println(indicator + (i+1) + ". " + getTrackName(i));
        }
    }

    static String getTrackName(int index) {
        String path = playlist.get(index);
        return path.substring(path.lastIndexOf(File.separator) + 1, path.lastIndexOf('.'));
    }
}