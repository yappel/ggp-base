package org.ggp.base.player.gamer.statemachine.MCTS;

import org.ggp.base.util.statemachine.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Remco de Vos on 30/03/2017.
 */
public class MCTSNode {

    private final Move move;
    private final MCTSNode parent;
    private final MCTSGamer gamer;
    // TODO: could this be final?
    private ArrayList<MCTSNode> children;

    private int score = 0;
    private int visits = 0;

    public MCTSNode(Move move, MCTSNode parent, MCTSGamer gamer) {
        this.move = move;
        this.parent = parent;
        this.gamer = gamer;
    }

    public Move getMove() {
        return this.move;
    }

    public MCTSNode getParent() {
        return this.parent;
    }

    public MCTSGamer getGamer() {
        return this.gamer;
    }

    public List<MCTSNode> getChildren() {
        return this.children;
    }

    public int getScore() {
        return this.score;
    }

    public int getVisits() {
        return this.visits;
    }

    public boolean isLeafNode() {
        return this.children == null;
    }

}
