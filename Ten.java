import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class Ten {

    public static void main(String[] args) {
        TheOne<String> result = new TheOne<>(args[0]);
        result.bind(new FileReaderFunc())
              .bind(new FilterFunc())
              .bind(new LowerCaseFunc())
              .bind(new SplitFunc())
              .bind(new StopWordRemover())
              .bind(new FrequencyCounter())
              .bind(new Sorter())
              .bind(new TopPrinter())
              .printme();
    }

    static class TheOne<T> {
        private T value;

        TheOne(T val) {
            this.value = val;
        }

        public <R> TheOne<R> bind(Function<T, R> func) {
            R newVal = func.apply(value);
            return new TheOne<>(newVal);
        }

        public void printme() {
            System.out.println(value);
        }
    }

    interface Function<T, R> {
        R apply(T x);
    }


    static class FileReaderFunc implements Function<String, String> {
        public String apply(String path) {
            try {
                return Files.readString(Paths.get(path));
            } catch (IOException e) {
                return "";
            }
        }
    }

    static class FilterFunc implements Function<String, String> {
        public String apply(String text) {
            return text.replaceAll("[^a-zA-Z0-9]+", " ");
        }
    }

    static class LowerCaseFunc implements Function<String, String> {
        public String apply(String txt) {
            return txt.toLowerCase();
        }
    }

    static class SplitFunc implements Function<String, List<String>> {
        public List<String> apply(String line) {
            return Arrays.asList(line.split("\\s+"));
        }
    }

    static class StopWordRemover implements Function<List<String>, List<String>> {
        public List<String> apply(List<String> words) {
            Set<String> stops = new HashSet<>();
            try {
                String raw = Files.readString(Paths.get("../stop_words.txt"));
                String[] parts = raw.split(",");
                for (String w : parts) stops.add(w);
            } catch (Exception e) {
                // fallback
            }
            for (char c = 'a'; c <= 'z'; c++) {
                stops.add(String.valueOf(c));
            }
            List<String> clean = new ArrayList<>();
            for (String w : words) {
                if (!stops.contains(w)) clean.add(w);
            }
            return clean;
        }
    }

    static class FrequencyCounter implements Function<List<String>, Map<String, Integer>> {
        public Map<String, Integer> apply(List<String> wds) {
            Map<String, Integer> map = new HashMap<>();
            for (String w : wds) {
                if (map.containsKey(w)) {
                    map.put(w, map.get(w) + 1);
                } else {
                    map.put(w, 1);
                }
            }
            return map;
        }
    }

    static class Sorter implements Function<Map<String, Integer>, List<Map.Entry<String, Integer>>> {
        public List<Map.Entry<String, Integer>> apply(Map<String, Integer> m) {
            List<Map.Entry<String, Integer>> out = new ArrayList<>(m.entrySet());
            
            for (int i = 0; i < out.size() - 1; i++) {
                for (int j = i + 1; j < out.size(); j++) {
                    if (out.get(j).getValue() > out.get(i).getValue()) {
                        var tmp = out.get(i);
                        out.set(i, out.get(j));
                        out.set(j, tmp);
                    }
                }
            }
            return out;
        }
    }

    static class TopPrinter implements Function<List<Map.Entry<String, Integer>>, String> {
        public String apply(List<Map.Entry<String, Integer>> list) {
            StringBuilder sb = new StringBuilder();
            int cap = Math.min(25, list.size());
            for (int i = 0; i < cap; i++) {
                var e = list.get(i);
                sb.append(e.getKey()).append(" - ").append(e.getValue()).append("\n");
            }
            return sb.toString();
        }
    }
}
