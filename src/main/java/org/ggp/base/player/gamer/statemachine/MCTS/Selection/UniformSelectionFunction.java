package org.ggp.base.player.gamer.statemachine.MCTS.Selection;

import org.ggp.base.player.gamer.statemachine.MCTS.MCTSNode;

import java.util.List;
import java.util.Random;

/**
 * Uniformly select a random child from the provided list of children.
 *
 * Created by Remco de Vos on 30/03/2017.
 */
public final class UniformSelectionFunction implements SelectionFunction {

    private final Random random;

    public UniformSelectionFunction() {
        this(new Random());
    }

    public UniformSelectionFunction(Random random) {
        this.random = random;
    }

    @Override
    public MCTSNode select(MCTSNode node) {
        MCTSNode selected = node;
        while(!selected.isLeafNode()) {
            List<MCTSNode> selectedChildren = selected.getChildren();
            selected = selectedChildren.get(random.nextInt(selectedChildren.size()));
        }
        return selected;
    }
}
