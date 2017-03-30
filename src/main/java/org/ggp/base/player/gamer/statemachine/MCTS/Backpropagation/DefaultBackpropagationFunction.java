package org.ggp.base.player.gamer.statemachine.MCTS.Backpropagation;

import org.ggp.base.player.gamer.statemachine.MCTS.MCTSNode;
import org.ggp.base.util.statemachine.MachineState;

/**
 * Created by Ben on 30/03/17.
 */
public class DefaultBackpropagationFunction implements BackpropagationFunction{
    @Override
    public void updateScore(MCTSNode element, int score) {
        MCTSNode node = element;
        while (element != null) {
            element.setScore(element.getScore() + score);
            element.setVisits(element.getVisits() + 1);
            node = node.getParent();
        }
    }
}
