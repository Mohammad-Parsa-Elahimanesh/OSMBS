package logic;

import java.util.Objects;

public class Record {
    public int score = 0;
    public double wholeTime = 0;
    public int killedEnemies = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Record record1)) return false;
        return score == record1.score && (int)record1.wholeTime == (int)wholeTime && killedEnemies == record1.killedEnemies;
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, wholeTime, killedEnemies);
    }
}
