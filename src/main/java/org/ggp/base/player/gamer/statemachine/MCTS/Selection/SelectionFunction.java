package org.ggp.base.player.gamer.statemachine.MCTS.Selection;

import org.ggp.base.player.gamer.statemachine.MCTS.MCTSNode;

/**
 * Interface for implementing the selection step in Monte Carlo Tree Search. Starting at the root R it selects
 * successive children till a leaf node L has been reached and returns L.
 *
 * Created by Remco de Vos on 30/03/2017.
 */
public interface SelectionFunction {

    /**
     * Select a leaf node starting from the provided root node.
     * @param node The root node to start from
     * @return The selected child
     */
    MCTSNode select(MCTSNode node);

}