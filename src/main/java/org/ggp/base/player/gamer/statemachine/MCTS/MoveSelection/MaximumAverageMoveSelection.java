package org.ggp.base.player.gamer.statemachine.MCTS.MoveSelection;

import org.ggp.base.player.gamer.statemachine.MCTS.MCTSNode;
import org.ggp.base.util.statemachine.Move;

import java.util.List;
import java.util.Map;

/**
 * Chooses the move with the highest average outcome.
 *
 * Created by Ben on 31/03/17.
 */
public class MaximumAverageMoveSelection implements MoveSelectionFunction {

    @Override
    public Move selectMove(MCTSNode root) {
        Map<Move, List<MCTSNode>> children = root.getChildrenMap();
        Move bestMove = root.getChildren().get(0).getMove();
        double scoreRatio = Double.MIN_VALUE;

        for(Move move : children.keySet()) {
            List<MCTSNode> nodes = children.get(move);
            int totalVisits = 0;
            int totalScore = 0;
            for(MCTSNode node : nodes) {
                totalVisits += node.getVisits();
                totalScore += node.getScore();
            }
            double ratio = (double) totalScore / (double) totalVisits;
            System.out.println("Calculating: " + move + " - Ratio: " + ratio);
            if (ratio > scoreRatio) {
                scoreRatio = ratio;
                bestMove = move;
            }
        }
        return bestMove;
    }
}
