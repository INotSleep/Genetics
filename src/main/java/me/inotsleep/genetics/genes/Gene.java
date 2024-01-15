package me.inotsleep.genetics.genes;

import me.inotsleep.genetics.genes.action.Action;

import java.util.List;
import java.util.Map;

public class Gene {
    public static Map<String, Gene> genes;

    public String geneSymbol;
    public List<Action> actions;
}
