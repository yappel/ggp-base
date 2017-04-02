package org.ggp.base.player.gamer.statemachine.MCTS.Configurations;

import org.ggp.base.player.gamer.statemachine.MCTS.Backpropagation.BackpropagationFunction;
import org.ggp.base.player.gamer.statemachine.MCTS.Backpropagation.DefaultBackpropagationFunction;
import org.ggp.base.player.gamer.statemachine.MCTS.Expansion.BasicRandomExpansionFunction;
import org.ggp.base.player.gamer.statemachine.MCTS.Expansion.ExpansionFunction;
import org.ggp.base.player.gamer.statemachine.MCTS.MoveSelection.MaximumAverageMoveSelection;
import org.ggp.base.player.gamer.statemachine.MCTS.MoveSelection.MoveSelectionFunction;
import org.ggp.base.player.gamer.statemachine.MCTS.Selection.SelectionFunction;
import org.ggp.base.player.gamer.statemachine.MCTS.Selection.UniformSelectionFunction;
import org.ggp.base.player.gamer.statemachine.MCTS.Simulation.RandomWinLossSimulation;
import org.ggp.base.player.gamer.statemachine.MCTS.Simulation.SimulationFunction;

import java.util.Random;

/**
 * Creates a default configuration of the monte carlo tree search algorithm.
 *
 * Created by Ben on 31/03/17.
 */
public class DefaultMCTSGamer extends MCTSGamer{

    private Random random;

    private Random getRandom() {
        if (this.random == null) {
            random = new Random();
        }
        return random;
    }

    @Override
    public SelectionFunction getSelectionFunction() {
        return new UniformSelectionFunction(getRandom());
    }

    @Override
    public ExpansionFunction getExpansionFunction() {
        return new BasicRandomExpansionFunction(getRandom());
    }

    @Override
    public SimulationFunction getSimulationFunction() {
        return new RandomWinLossSimulation(getRandom());
    }

    @Override
    public BackpropagationFunction getBackpropagationFunction() {
        return new DefaultBackpropagationFunction();
    }

    @Override
    public MoveSelectionFunction getMoveSelectionFunction() {
        return new MaximumAverageMoveSelection();
    }

    @Override
    public String getName() {
        return "Default_MCTS";
    }
}
