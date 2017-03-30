package org.ggp.base.player.gamer.statemachine.MCTS;

import org.ggp.base.util.statemachine.MachineState;
import org.ggp.base.util.statemachine.Move;
import org.ggp.base.util.statemachine.Role;

import java.util.List;

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
    private final MCTSNode parent;
    private final MCTSGamer gamer;
    // TODO: could this be final?
    private List<MCTSNode> children;

    private int score = 0;
    private int visits = 0;

    public MCTSNode(MachineState state, Role role, Move move, MCTSNode parent, MCTSGamer gamer) {
        this.state = state;
        this.role = role;
        this.move = move;
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

    public MCTSNode getParent() {
        return this.parent;
    }

    public MCTSGamer getGamer() {
        return this.gamer;
    }

    public List<MCTSNode> getChildren() {
        return this.children;
    }

    public void setChildren(List<MCTSNode> children) {
        this.children = children;
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
