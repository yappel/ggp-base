package org.ggp.base.player.gamer.statemachine.MCTS.Expansion;

import org.ggp.base.player.gamer.statemachine.MCTS.Configurations.MCTSGamer;
import org.ggp.base.player.gamer.statemachine.MCTS.MCTSNode;
import org.ggp.base.util.statemachine.Move;
import org.ggp.base.util.statemachine.exceptions.MoveDefinitionException;
import org.ggp.base.util.statemachine.exceptions.TransitionDefinitionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public MCTSNode expand(MCTSNode leaf, MCTSGamer gamer) throws MoveDefinitionException, TransitionDefinitionException {
        // Check if the current state is terminal
        if(gamer.getStateMachine().isTerminal(leaf.getMachineState())) {
            return leaf;
        }
        // When a leaf has not yet been visited choose the leaf
        if (leaf.getVisits() <= 0) {
            return leaf;
        } else {
            leaf.createChildren();
            Map<Move, List<MCTSNode>> children = leaf.getChildrenMap();
            // TODO: is the way of selecting a random child correct?
            // Choose a random element to expand
            List<Move> keys = new ArrayList<>(children.keySet());
            List<MCTSNode> nodes = children.get(keys.get(random.nextInt(keys.size())));
            return nodes.get(random.nextInt(nodes.size()));
        }
    }
}
