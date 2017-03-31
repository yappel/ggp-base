package org.ggp.base.player.gamer.statemachine.MCTS.MoveSelection;

import org.ggp.base.player.gamer.statemachine.MCTS.MCTSNode;
import org.ggp.base.util.statemachine.Move;

/**
 * Interface for implementing the selection of the move to be performed given a monte carlo tree.
 *
 * Created by Ben on 31/03/2017.
 */
public interface MoveSelectionFunction {

    /**
     * Select a move to be performed from the provided root node.
     * @param node The root node to start from.
     * @return The optimal move.
     */
    Move selectMove(MCTSNode node);

}