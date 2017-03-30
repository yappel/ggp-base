package org.ggp.base.player.gamer.statemachine.MCTS.Selection;

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
    public TreeElement select(List<TreeElement> children) {
        // TODO: fix the problem that children could have 0 elements
        return children.get(random.nextInt(children.size()));
    }
}
