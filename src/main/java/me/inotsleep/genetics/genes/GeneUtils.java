package me.inotsleep.genetics.genes;

import org.bukkit.entity.LivingEntity;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GeneUtils {
    public static String combine(String first, String second) {
        Map<String, String> firstGenes = new HashMap<>();
        Map<String, String> secondGenes = new HashMap<>();

        extractGenes(first, firstGenes);
        extractGenes(second, secondGenes);
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

    private static void extractGenes(String textGenes, Map<String, String> genes) {
        Arrays.stream(textGenes.split("")).forEach(gene -> {
            String savedGene = genes.get(gene.toUpperCase());
            if (savedGene == null) {
                genes.put(gene.toUpperCase(), gene);
                return;
            }
            genes.put(savedGene.toUpperCase(), savedGene+gene);
        });
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

    public static void applyActions(String genes, LivingEntity entity) {// We will need this in other place in future
        Set<String> sepGenes = new HashSet<>();

        Pattern pattern = Pattern.compile(".{2}");
        Matcher matcher = pattern.matcher(genes);
        while (matcher.find()) {
            sepGenes.add(matcher.group());
        }

        for (String sGene : sepGenes.toArray(new String[0])) {
            Gene gene = Gene.publicGenes.get(sGene.substring(0, 0).toUpperCase());
            gene.applyActions(entity, sGene);
        }
    }
}
