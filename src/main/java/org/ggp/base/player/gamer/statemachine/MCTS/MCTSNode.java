package org.ggp.base.player.gamer.statemachine.MCTS;

import org.ggp.base.player.gamer.statemachine.MCTS.Configurations.MCTSGamer;
import org.ggp.base.util.statemachine.MachineState;
import org.ggp.base.util.statemachine.Move;
import org.ggp.base.util.statemachine.Role;
import org.ggp.base.util.statemachine.StateMachine;
import org.ggp.base.util.statemachine.exceptions.MoveDefinitionException;
import org.ggp.base.util.statemachine.exceptions.TransitionDefinitionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    private final MCTSGamer gamer;
    private MCTSNode parent;
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

    public void setParent(MCTSNode parent) {
        this.parent = parent;
    }

    public MCTSGamer getGamer() {
        return this.gamer;
    }

    public List<MCTSNode> getChildren() {
        // TODO: optimize or change to return the HashMap
        List<MCTSNode> res = new ArrayList<>();
        for(List<MCTSNode> nodes : this.children.values()) {
            res.addAll(nodes);
        }
        return res;
    }

    public void setChildren(Map<Move, List<MCTSNode>> children) {
        this.children = children;
    }

    // TODO: change this to getChildren?
    public Map<Move, List<MCTSNode>> getChildrenMap() {
        return this.children;
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

    public int countNodes() {
        if (this.visits == 0){
            return 0;
        }
        if (this.children == null){
            return 1;
        }
        int nodes = 0;
        for (MCTSNode child : getChildren()) {
            nodes += child.countNodes();
        }
        return nodes + 1;
    }

    public void createChildren() throws MoveDefinitionException, TransitionDefinitionException {
        children = new ConcurrentHashMap<>();
        final StateMachine stateMachine = getGamer().getStateMachine();
        final int roleIndex = stateMachine.getRoleIndices().get(getGamer().getRole());
        // Get all the legal joint moves from the MachineState in the leaf
        for (List<Move> moves : stateMachine.getLegalJointMoves(getMachineState())) {
            // Get the move performed by the algorithm and the resulting state of the joined moves
            Move move = moves.get(roleIndex);
            MachineState state = stateMachine.getNextState(getMachineState(), moves);
            // If there is no key for the move init a list
            if (!children.containsKey(move)) {
                children.put(move, new ArrayList<>());
            }
            // TODO: remove role as it has no purpose?
            // Add the new tree node to the Map of children
            children.get(move).add(new MCTSNode(state, getRole(), move, moves, this, getGamer()));
        }
    }

}
