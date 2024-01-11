package me.inotsleep.genetics.genes;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class GeneUtils {
    public static String combine(String first, String second) {
        Map<String, String> firstGenes = new HashMap<>();
        Map<String, String> secondGenes = new HashMap<>();

        Arrays.stream(first.split("")).forEach(gene -> {
            String savedGene = firstGenes.get(gene.toUpperCase());
            if (savedGene == null) {
                firstGenes.put(gene.toUpperCase(), gene);
                return;
            }
            firstGenes.put(savedGene.toUpperCase(), savedGene+gene);
        });

        Arrays.stream(second.split("")).forEach(gene -> {
            String savedGene = secondGenes.get(gene.toUpperCase());
            if (savedGene == null) {
                secondGenes.put(gene.toUpperCase(), gene);
                return;
            }
            secondGenes.put(savedGene.toUpperCase(), savedGene+gene);
        });
        AtomicReference<String> combined = new AtomicReference<>("");
        new ArrayList<>(firstGenes.keySet()).forEach(gene -> {
            String secondGene = secondGenes.get(gene.toUpperCase());
            if (secondGene == null) {
                combined.set(combined.get()+gene);
                firstGenes.remove((""+gene.charAt(0)).toUpperCase());
                return;
            }

            String resultGene = "" + getRandom(gene) + getRandom(secondGene);
            combined.set(combined.get()+resultGene);
            firstGenes.remove((""+gene.charAt(0)).toUpperCase());
            secondGenes.remove((""+gene.charAt(0)).toUpperCase());
        });

        return Arrays.stream(
                (
                        combined.get()
                        +String.join("", firstGenes.keySet())
                        +String.join("", secondGenes.keySet())
                ).split(""))
                .sorted(
                        (a, b) -> compareChars(a.charAt(0),b.charAt(0))
                )
                .collect(Collectors.joining("")
        );
    }

    private static int compareChars(char a, char b) {
        char upperA = Character.toUpperCase(a);
        char upperB = Character.toUpperCase(b);
        if (upperA == upperB) {
            return Character.compare(a, b);
        }
        return Character.compare(upperA, upperB);
    }

    private static char getRandom(String str) {
        return str.charAt((int) (Math.random() * (str.length())));
    }
}
