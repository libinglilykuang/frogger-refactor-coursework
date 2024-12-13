package p4_group_8_repo.model.data;

public class LevelConfig {
    private final int startX;
    private final int y;
    private final int gap;

    public LevelConfig(int startX, int y, int gap) {
        this.startX = startX;
        this.y = y;
        this.gap = gap;
    }

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
