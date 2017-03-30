package org.ggp.base.player.gamer.statemachine.MCTS.Selection;

import org.ggp.base.player.gamer.statemachine.MCTS.MCTSNode;
import org.ggp.base.player.gamer.statemachine.MCTS.TreeElement;

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
    public MCTSNode select(List<MCTSNode> children) {
        // TODO: fix the problem that children could have 0 elements
        // TODO: actually implement that it will continue till a leaf has been reached
        return children.get(random.nextInt(children.size()));
    }
}
