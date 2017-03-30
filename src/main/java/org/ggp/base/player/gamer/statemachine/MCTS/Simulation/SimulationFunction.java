package org.ggp.base.player.gamer.statemachine.MCTS.Simulation;

import org.ggp.base.player.gamer.statemachine.MCTS.MCTSGamer;
import org.ggp.base.player.gamer.statemachine.MCTS.MCTSNode;

/**
 * Interface for implementing the simulation step in the Monte Carlo Tree Search.
 *
 * Created by Remco de Vos on 30/03/2017.
 */
public interface SimulationFunction {

    /**
     * Simulate a playout from the selected Tree Element.
     * @param selectedElement The Tree Element from where to start the simulation
     * @param gamer The MCTSGamer in the game
     * @return The score obtained during the playout
     */
    int simulate(MCTSNode selectedElement, MCTSGamer gamer);

}
