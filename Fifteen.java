// Fifteen.java

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Fifteen {

    public static void main(String[] args) {
        // Kick off the Hollywood-style app
        WordFrequencyApp app = new WordFrequencyApp();
        StopWordFilter stopFilter = new StopWordFilter(app);
        DataStorage storage = new DataStorage(app, stopFilter);
        WordFrequencyCounter counter = new WordFrequencyCounter(app, storage);
        app.run(args[0]);
    }
}

// === Framework ===

class WordFrequencyApp {
    ArrayList<LoadHandler> loadEvents = new ArrayList<>();
    ArrayList<WorkHandler> doWorkEvents = new ArrayList<>();
    ArrayList<EndHandler> endEvents = new ArrayList<>();

    void registerForLoad(LoadHandler h) {
        loadEvents.add(h);
    }

    void registerForWork(WorkHandler h) {
        doWorkEvents.add(h);
    }

    void registerForEnd(EndHandler h) {
        endEvents.add(h);
    }

    void run(String path) {
        for (int i = 0; i < loadEvents.size(); i++) {
            loadEvents.get(i).onLoad(path);
        }
        for (int i = 0; i < doWorkEvents.size(); i++) {
            doWorkEvents.get(i).onWork();
        }
        for (int i = 0; i < endEvents.size(); i++) {
            endEvents.get(i).onEnd();
        }
    }
}

// === Interfaces ===

interface LoadHandler {
    void onLoad(String file);
}

interface WorkHandler {
    void onWork();
}

interface EndHandler {
    void onEnd();
}

interface WordHandler {
    void handle(String word);
}

// === Stop Word Filter ===

class StopWordFilter implements LoadHandler {
    ArrayList<String> stopWords;

    StopWordFilter(WordFrequencyApp app) {
        app.registerForLoad(this);
    }

    public void onLoad(String ignore) {
        stopWords = new ArrayList<>();
        try {
            String text = Files.readString(Paths.get("../stop_words.txt"));
            String[] parts = text.split(",");
            for (String word : parts) {
                stopWords.add(word);
            }
        } catch (IOException e) {
            System.out.println("Could not load stop_words.txt");
        }

        for (char ch = 'a'; ch <= 'z'; ch++) {
            stopWords.add(String.valueOf(ch));
        }
    }

    boolean isStopWord(String word) {
        return stopWords.contains(word);
    }
}

// === Data Storage ===

class DataStorage implements LoadHandler, WorkHandler {
    String data = "";
    StopWordFilter filter;
    ArrayList<WordHandler> wordHandlers = new ArrayList<>();

    DataStorage(WordFrequencyApp app, StopWordFilter swf) {
        filter = swf;
        app.registerForLoad(this);
        app.registerForWork(this);
    }

    public void onLoad(String filePath) {
        try {
            String content = Files.readString(Paths.get(filePath));
            content = content.replaceAll("[^a-zA-Z0-9]+", " ");
            data = content.toLowerCase();
        } catch (IOException e) {
            System.out.println("Couldn't read file: " + filePath);
        }
    }

    public void onWork() {
        String[] words = data.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            String w = words[i];
            if (!filter.isStopWord(w)) {
                for (int j = 0; j < wordHandlers.size(); j++) {
                    wordHandlers.get(j).handle(w);
                }
            }
        }
    }

    void registerWordHandler(WordHandler h) {
        wordHandlers.add(h);
    }
}

// === Word Frequency Counter ===

class WordFrequencyCounter implements WordHandler, EndHandler {
    HashMap<String, Integer> freqs = new HashMap<>();

    WordFrequencyCounter(WordFrequencyApp app, DataStorage storage) {
        storage.registerWordHandler(this);
        app.registerForEnd(this);
    }

    public void handle(String word) {
        if (freqs.containsKey(word)) {
            freqs.put(word, freqs.get(word) + 1);
        } else {
            freqs.put(word, 1);
        }
    }

    public void onEnd() {
        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(freqs.entrySet());
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j).getValue() > list.get(i).getValue()) {
                    Map.Entry<String, Integer> temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                }
            }
        }

        for (int i = 0; i < 25 && i < list.size(); i++) {
            Map.Entry<String, Integer> e = list.get(i);
            System.out.println(e.getKey() + " - " + e.getValue());
        }
    }
}