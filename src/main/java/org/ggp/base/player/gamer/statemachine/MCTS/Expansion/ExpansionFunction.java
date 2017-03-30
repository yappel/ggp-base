package org.ggp.base.player.gamer.statemachine.MCTS.Expansion;

import org.ggp.base.player.gamer.statemachine.MCTS.TreeElement;

/**
 * Interface for implementing the expansion step in Monte Carlo Tree Search.
 *
 * Created by Remco de Vos on 30/03/2017.
 */
public interface ExpansionFunction {

    /**
     * Expand the leaf element by adding one or more child elements to it, and return one of them.
     * @param leaf The leaf element to expand
     * @return The selected child node
     */
    TreeElement expand(TreeElement leaf);

}
