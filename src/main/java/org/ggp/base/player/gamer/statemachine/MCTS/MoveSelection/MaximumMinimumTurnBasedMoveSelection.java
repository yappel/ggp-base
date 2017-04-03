package org.ggp.base.player.gamer.statemachine.MCTS.MoveSelection;

import org.ggp.base.player.gamer.statemachine.MCTS.MCTSNode;
import org.ggp.base.util.statemachine.Move;

import java.util.List;
import java.util.Map;

/**
 * Chooses the move with the highest minimal outcome. Applies MinMax on the monte carlo tree.
 * This is the implementation for turn based games(NOOP games). This means that every joint moves only consist out of
 * moves for one player and the NOOP move for the other.
 * <p>
 * Created by Ben on 31/03/17.
 */
public class MaximumMinimumTurnBasedMoveSelection implements MoveSelectionFunction {

    @Override
    public Move selectMove(MCTSNode root) {
        Map<Move, List<MCTSNode>> children = root.getChildrenMap();
        Move bestMove = root.getChildren().get(0).getMove();
        if (children.keySet().size() != 1) {
            double maxRatio = Double.MIN_VALUE;

            for (Move move : children.keySet()) {
                MCTSNode child = children.get(move).get(0);
                double minRatio = Double.MAX_VALUE;
                for (MCTSNode node : child.getChildren()) {
                    double ratio = node.getScore() / node.getVisits();
                    if (ratio < minRatio) {
                        minRatio = ratio;
                    }
                }
                System.out.println("Calculating: " + child.getJointMoves() + " - Ratio: " + minRatio);
                if (minRatio > maxRatio) {
                    maxRatio = minRatio;
                    bestMove = move;
                }
            }

        }

        return bestMove;

    }
}
