package uk.ac.nott.cs.comp2013.froggergame.model.data;

public class LevelConfig {
    private final int startX;
    private final int y;
    private final int gap;
    private final int totalEnds;

    public LevelConfig(int totalEnds,int startX, int y, int gap) {
        this.totalEnds = totalEnds;
        this.startX = startX;
        this.y = y;
        this.gap = gap;
    }

    public int getTotalEnds() { return totalEnds; }

    public int getStartX() {
        return startX;
    }

    public int getY() {
        return y;
    }

    public int getGap() {
        return gap;
    }
}
