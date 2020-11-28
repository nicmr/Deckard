package dev.nicolasmohr.deckard;

import java.util.*;

public class Battleground {
    public String name;
    public ArrayList<Timing> timings;
    public String thumbnail;

    public Battleground() {
        this.name = "";
        this.timings = new ArrayList<Timing>();
        this.thumbnail = "";
    }

    public Battleground(String name, String thumbnail, ArrayList<Timing> timings) {
        this.name = name;
        this.timings = timings;
        this.thumbnail = thumbnail;
    }

    /**
     * Generates a HashMap from the supplied List<Battlegrounds>, indexed by the .name field for faster lookup
     */
    public static HashMap<String, Battleground> indexByName (List<Battleground> battlegrounds) {
        var battlegroundmap = new HashMap<String, Battleground>();
        for (Battleground b: battlegrounds) {
            battlegroundmap.put(b.name.toLowerCase(), b);
        }
        return battlegroundmap;
    }
}