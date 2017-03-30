package org.ggp.base.player.gamer.statemachine.MCTS.Selection;

import org.ggp.base.player.gamer.statemachine.MCTS.MCTSNode;

import java.util.List;

/**
 * Interface for implementing the selection step in Monte Carlo Tree Search. Starting at the root R it selects
 * successive children till a leaf node L has been reached and returns L.
 *
 * Created by Remco de Vos on 30/03/2017.
 */
public interface SelectionFunction {

    /**
     * Select a TreeElement from the list of children.
     * @param children The list of children to choose from
     * @return The selected child
     */
    MCTSNode select(List<MCTSNode> children);

}