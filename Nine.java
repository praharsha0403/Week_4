import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.*;

public class Nine {

    public static void main(String[] args) throws Exception {
        readFile(args[0], new Cont1());
    }

    static class Cont1 implements ICallback<String> {
        public void call(String text) {
            filterChars(text, new Cont2());
        }
    }

    static class Cont2 implements ICallback<String> {
        public void call(String s) {
            normalize(s, new Cont3());
        }
    }

    static class Cont3 implements ICallback<String> {
        public void call(String lower) {
            scan(lower, new Cont4());
        }
    }

    static class Cont4 implements ICallback<List<String>> {
        public void call(List<String> words) {
            removeStopWords(words, new Cont5());
        }
    }

    static class Cont5 implements ICallback<List<String>> {
        public void call(List<String> clean) {
            frequencies(clean, new Cont6());
        }
    }

    static class Cont6 implements ICallback<Map<String, Integer>> {
        public void call(Map<String, Integer> freqMap) {
            sort(freqMap, new Cont7());
        }
    }

    static class Cont7 implements ICallback<List<Map.Entry<String, Integer>>> {
        public void call(List<Map.Entry<String, Integer>> sorted) {
            printTop(sorted, new Done());
        }
    }

    static class Done implements ICallback<Void> {
        public void call(Void x) {
            // Nothing to do
        }
    }

    interface ICallback<T> {
        void call(T x);
    }

    public static void readFile(String path, ICallback<String> next) {
        try {
            Scanner sc = new Scanner(new File(path));
            String total = "";
            while (sc.hasNextLine()) {
                total += sc.nextLine() + "\n";
            }
            sc.close();
            next.call(total);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void filterChars(String data, ICallback<String> next) {
        String filtered = data.replaceAll("[^a-zA-Z0-9]+", " ");
        next.call(filtered);
    }

    public static void normalize(String input, ICallback<String> next) {
        next.call(input.toLowerCase());
    }

    public static void scan(String input, ICallback<List<String>> next) {
        String[] parts = input.split("\\s+");
        List<String> words = new ArrayList<>();
        for (String w : parts) {
            if (!w.equals("")) {
                words.add(w);
            }
        }
        next.call(words);
    }

    public static void removeStopWords(List<String> wordList, ICallback<List<String>> next) {
        Set<String> stopWords = new HashSet<>();
        try {
            Scanner s = new Scanner(new File("../stop_words.txt"));
            String[] stops = s.nextLine().split(",");
            for (String word : stops) {
                stopWords.add(word);
            }
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (char c = 'a'; c <= 'z'; c++) {
            stopWords.add(Character.toString(c));
        }

        List<String> result = new ArrayList<>();
        for (String word : wordList) {
            if (!stopWords.contains(word)) {
                result.add(word);
            }
        }

        next.call(result);
    }

    public static void frequencies(List<String> words, ICallback<Map<String, Integer>> next) {
        Map<String, Integer> freqs = new HashMap<>();
        for (String w : words) {
            if (freqs.containsKey(w)) {
                freqs.put(w, freqs.get(w) + 1);
            } else {
                freqs.put(w, 1);
            }
        }
        next.call(freqs);
    }

    public static void sort(Map<String, Integer> map, ICallback<List<Map.Entry<String, Integer>>> next) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());

        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i+1; j < list.size(); j++) {
                if (list.get(j).getValue() > list.get(i).getValue()) {
                    Map.Entry<String, Integer> temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                }
            }
        }

        next.call(list);
    }

    public static void printTop(List<Map.Entry<String, Integer>> wordFreqs, ICallback<Void> next) {
        int count = 0;
        for (Map.Entry<String, Integer> entry : wordFreqs) {
            if (count == 25) break;
            System.out.println(entry.getKey() + " - " + entry.getValue());
            count++;
        }
        next.call(null);
    }
}
