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
        Move bestMove = null;
        double maxRatio = Double.MIN_VALUE;

        for(Move move : children.keySet()) {
            List<MCTSNode> nodes = children.get(move);
            double minRatio = Double.MAX_VALUE;
            List<Move> secondMove = null;
            for(MCTSNode node : nodes) {
                double ratio = node.getScore()/node.getVisits();
                System.out.println("Calculating: " + node.getJointMoves() + " - Ratio: " + ratio);
                if (ratio < minRatio) {
                    minRatio = ratio;
                    secondMove = node.getJointMoves();
                }
            }
            if (minRatio > maxRatio) {
                maxRatio = minRatio;
                bestMove = move;
            }
            System.out.println("Evaluating: " + move.toString() + " " + secondMove + " - Ratio: " + minRatio);
        }
        return bestMove;
    }
}
