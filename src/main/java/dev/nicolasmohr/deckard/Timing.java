package dev.nicolasmohr.deckard;

public class Timing {
    public String objective;
    public int spawn;
    public int respawn;

    public Timing() {
        this.objective = "";
        this.spawn = 0;
        this.respawn = 0;
    }

    public Timing(String objective, int spawn, int respawn) {
        this.objective = objective;
        this.spawn = spawn;
        this.respawn = respawn;
    }
}