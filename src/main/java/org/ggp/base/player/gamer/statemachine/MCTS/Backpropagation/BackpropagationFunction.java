package org.ggp.base.player.gamer.statemachine.MCTS.Backpropagation;

import org.ggp.base.player.gamer.statemachine.MCTS.TreeElement;

/**
 * Interface for implementing backpropagation step in Monte Carlo Tree Search.
 *
 * Created by Remco de Vos on 30/03/2017.
 */
public interface BackpropagationFunction {

    /**
     * Update the information in the nodes from the selected element to the root based on the score from the playout.
     * @param element The element to start updating from
     * @param score The score from the playout
     */
    void updateScore(TreeElement element, int score);

}
