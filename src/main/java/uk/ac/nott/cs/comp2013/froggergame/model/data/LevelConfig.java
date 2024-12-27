package uk.ac.nott.cs.comp2013.froggergame.model.data;

public record LevelConfig(int totalEnds, int startX, int y, int gap, double speedFactor) {
    public static LevelConfig createDefault(double speedFactor) {
        return new LevelConfig(5, 4, 96, 133, speedFactor);
    }
}
