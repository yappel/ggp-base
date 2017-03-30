package org.ggp.base.player.gamer.statemachine.MCTS;

import org.ggp.base.util.statemachine.MachineState;
import org.ggp.base.util.statemachine.Move;
import org.ggp.base.util.statemachine.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Remco de Vos on 30/03/2017.
 */
public class MCTSNode {

    /**
     * The state of the game in this node.
     */
    private final MachineState state;

    /**
     * The player in the current node.
     */
    private final Role role;

    /**
     * The move made to get in this node.
     */
    private final Move move;

    /**
     * The joint moves made by all player to get in this node.
     */
    private final List<Move> jointMoves;

    private final MCTSNode parent;
    private final MCTSGamer gamer;
    // TODO: remove old
    private List<MCTSNode> children2;

    private Map<Move, List<MCTSNode>> children;

    private int score = 0;
    private int visits = 0;

    // TODO: see which of the variables are actually used / needed
    public MCTSNode(MachineState state, Role role, Move move, List<Move> jointMoves, MCTSNode parent, MCTSGamer gamer) {
        this.state = state;
        this.role = role;
        this.move = move;
        this.jointMoves = jointMoves;
        this.parent = parent;
        this.gamer = gamer;
    }

    public MachineState getMachineState() {
        return this.state;
    }

    public Role getRole() {
        return this.role;
    }

    public Move getMove() {
        return this.move;
    }

    public List<Move> getJointMoves() {
        return this.jointMoves;
    }

    public MCTSNode getParent() {
        return this.parent;
    }

    public MCTSGamer getGamer() {
        return this.gamer;
    }

    public List<MCTSNode> getChildren() {
        // TODO: optimize or change to return the HashMap
        List<MCTSNode> res = new ArrayList<MCTSNode>();
        for(List<MCTSNode> nodes : this.children.values()) {
            res.addAll(nodes);
        }
        return res;
    }

    public void setChildren(Map<Move, List<MCTSNode>> children) {
        this.children = children;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getVisits() {
        return this.visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public boolean isLeafNode() {
        return this.children == null;
    }

}
