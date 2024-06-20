package game.result;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public interface GameResultManager {

    List<GameResult> add(GameResult result) throws IOException;

    List<GameResult> getAll() throws IOException;

    default List<GameResult> getBest(int limit) throws IOException {
        return getAll()
                .stream()
                .sorted(Comparator.comparingInt(GameResult::getSteps))
                .limit(limit)
                .toList();
    }

}