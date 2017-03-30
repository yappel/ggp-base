package org.ggp.base.player.gamer.statemachine.MCTS.Expansion;

import org.ggp.base.player.gamer.statemachine.MCTS.MCTSGamer;
import org.ggp.base.player.gamer.statemachine.MCTS.MCTSNode;

import org.ggp.base.util.statemachine.MachineState;
import org.ggp.base.util.statemachine.Move;
import org.ggp.base.util.statemachine.Role;
import org.ggp.base.util.statemachine.StateMachine;
import org.ggp.base.util.statemachine.exceptions.MoveDefinitionException;
import org.ggp.base.util.statemachine.exceptions.TransitionDefinitionException;

import java.util.*;

/**
 * Expand the leaf node by adding all children to the node and select one of these children randomly to return.
 *
 * Created by Remco de Vos on 30/03/2017.
 */
public class BasicRandomExpansionFunction implements ExpansionFunction {

    private final Random random;

    public BasicRandomExpansionFunction() {
        this(new Random());
    }

    public BasicRandomExpansionFunction(Random random) {
        this.random = random;
    }

    @Override
    public MCTSNode expand(MCTSNode leaf, MCTSGamer gamer) throws MoveDefinitionException, TransitionDefinitionException {
        // Check if the current state is terminal
        if(gamer.getStateMachine().isTerminal(leaf.getMachineState())) {
            return leaf;
        } else {
            Map<Move, List<MCTSNode>> children = new HashMap<Move, List<MCTSNode>>();
            final StateMachine stateMachine = gamer.getStateMachine();
            final int roleIndex = stateMachine.getRoleIndices().get(gamer.getRole());
            // Get all the legal joint moves from the MachineState in the leaf
            for (List<Move> moves : stateMachine.getLegalJointMoves(leaf.getMachineState())) {
                // Get the move performed by the algorithm and the resulting state of the joined moves
                Move move = moves.get(roleIndex);
                MachineState state = stateMachine.getNextState(leaf.getMachineState(), moves);
                // If there is no key for the move init a list
                if (!children.containsKey(move)) {
                    children.put(move, new ArrayList<MCTSNode>());
                }
                // TODO: remove role as it has no purpose?
                // Add the new tree node to the Map of children
                children.get(move).add(new MCTSNode(state, leaf.getRole(), move, moves, leaf, gamer));
            }
            // Add the children to the leaf
            leaf.setChildren(children);
            // TODO: is the way of selecting a random child correct?
            // Choose a random element to expand
            List<Move> keys = new ArrayList<Move>(children.keySet());
            List<MCTSNode> nodes = children.get(keys.get(random.nextInt(keys.size())));
            return nodes.get(random.nextInt(nodes.size()));
        }
    }
}
