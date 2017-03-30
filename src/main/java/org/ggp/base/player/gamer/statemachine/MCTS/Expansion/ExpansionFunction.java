package org.ggp.base.player.gamer.statemachine.MCTS.Expansion;

import org.ggp.base.player.gamer.statemachine.MCTS.MCTSGamer;
import org.ggp.base.player.gamer.statemachine.MCTS.MCTSNode;
import org.ggp.base.util.statemachine.exceptions.MoveDefinitionException;
import org.ggp.base.util.statemachine.exceptions.TransitionDefinitionException;

/**
 * Interface for implementing the expansion step in Monte Carlo Tree Search.
 *
 * Created by Remco de Vos on 30/03/2017.
 */
public interface ExpansionFunction {

    /**
     * Expand the leaf element by adding one or more child elements to it, and return one of them.
     * @param leaf The leaf element to expand
     * @param gamer The active MCTS gamer
     * @return The selected child node
     */
    MCTSNode expand(MCTSNode leaf, MCTSGamer gamer) throws MoveDefinitionException, TransitionDefinitionException;

}
