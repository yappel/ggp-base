package org.ggp.base.player.gamer.statemachine.MCTS.Simulation;

import org.ggp.base.player.gamer.statemachine.MCTS.MCTSGamer;
import org.ggp.base.player.gamer.statemachine.MCTS.TreeElement;

import java.util.Random;

/**
 * Function randomly selects moves from the selected tree element till a win/loss state has been reached.
 * The returned score is 100 when won, 0 when lost.
 *
 * Created by Remco de Vos on 30/03/2017.
 */
public class RandomWinLossSimulation implements SimulationFunction {

    private final Random random;

    public RandomWinLossSimulation() {
        this(new Random());
    }

    public RandomWinLossSimulation(Random random) {
        this.random = random;
    }

    @Override
    public int simulate(TreeElement selectedElement, MCTSGamer gamer) {
        // TODO: actually implement
        return 0;
    }
}