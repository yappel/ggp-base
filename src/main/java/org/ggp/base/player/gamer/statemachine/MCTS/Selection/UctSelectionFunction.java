package org.ggp.base.player.gamer.statemachine.MCTS.Selection;

import org.ggp.base.player.gamer.statemachine.MCTS.MCTSNode;
import java.util.Random;

/**
 * Selects subtree to be expanded based on UCT (multi armed bandit problem)
 *
 * Created by Ben on 31/03/17.
 */
public class UctSelectionFunction implements SelectionFunction {
    private final Random random;
    private final double explorationParameter = 2;

    public UctSelectionFunction() {
        this(new Random());
    }

    public UctSelectionFunction(Random random) {
        this.random = random;
    }

    @Override
    public MCTSNode select(MCTSNode node) {
        MCTSNode selected = node;
        while(!selected.isLeafNode()) {
            selected = selectChild(selected);
        }
        return selected;
    }



    private MCTSNode selectChild(MCTSNode root) {
        double maxUct = Double.MIN_VALUE;
        MCTSNode maxChild = null;
        for (MCTSNode child: root.getChildren()) {
            if (child.getVisits() == 0) {
                return child;
            }
            double uct = (child.getScore()/child.getVisits())
                    + Math.sqrt(Math.log(root.getVisits())/child.getVisits());
            if (uct > maxUct) {
                maxUct = uct;
                maxChild = child;
            }
        }
        return maxChild;
    }
}
