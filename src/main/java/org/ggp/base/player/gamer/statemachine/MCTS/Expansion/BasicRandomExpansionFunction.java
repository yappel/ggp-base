package org.ggp.base.player.gamer.statemachine.MCTS.Expansion;

import org.ggp.base.player.gamer.statemachine.MCTS.MCTSGamer;
import org.ggp.base.player.gamer.statemachine.MCTS.MCTSNode;
import org.ggp.base.util.statemachine.Move;
import org.ggp.base.util.statemachine.exceptions.MoveDefinitionException;

import java.util.List;
import java.util.Random;

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
    public MCTSNode expand(MCTSNode leaf, MCTSGamer gamer) throws MoveDefinitionException {
        // Check if the current state is terminal
        if(gamer.getStateMachine().isTerminal(leaf.getMachineState())) {
            return leaf;
        } else {
            // Get all the legal moves for the current node given the state of the node and the role
            List<Move> moves = gamer.getStateMachine().getLegalMoves(leaf.getMachineState(), leaf.getRole());
            
        }







        return null;
    }
}
