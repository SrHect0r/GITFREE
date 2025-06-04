package util;

import logic.Player;

import java.util.Comparator;

public class QuestionComparator implements Comparator<Player> {
    @Override
    public int compare(Player p1, Player p2) {
        return Integer.compare(p2.getScore(), p1.getScore());
    }
}
