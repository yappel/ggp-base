package org.ggp.base.player.gamer.statemachine.MCTS.Configurations;

import org.ggp.base.player.gamer.statemachine.MCTS.MoveSelection.MaximumMinimumTurnBasedMoveSelection;
import org.ggp.base.player.gamer.statemachine.MCTS.MoveSelection.MoveSelectionFunction;
import org.ggp.base.player.gamer.statemachine.MCTS.Simulation.RandomDepthSimulation;
import org.ggp.base.player.gamer.statemachine.MCTS.Simulation.SimulationFunction;

/**
 * MCTS Gamer configuration which does not explore the game till a terminal state but to a certain depth and basis its
 * decision on score of the reached state instead of a win/loss scenario. Uses the BasicSelectionFunction.
 *
 * Created by Remco de Vos on 02/04/2017.
 */
public class PointMCTSGamer extends DefaultMCTSGamer {

    public PointMCTSGamer() {
        super(false);
    }

    @Override
    public SimulationFunction getSimulationFunction() {
        return new RandomDepthSimulation();
    }

    @Override
    public MoveSelectionFunction getMoveSelectionFunction() {
        return new MaximumMinimumTurnBasedMoveSelection();
    }

    @Override
    public String getName() {
        return "Point_MCTS";
    }
}
