package org.ggp.base.player.gamer.statemachine.MCTS.Simulation;

import org.ggp.base.player.gamer.statemachine.MCTS.MCTSGamer;
import org.ggp.base.player.gamer.statemachine.MCTS.MCTSNode;
import org.ggp.base.player.gamer.statemachine.MCTS.TreeElement;
import org.ggp.base.util.statemachine.MachineState;
import org.ggp.base.util.statemachine.StateMachine;
import org.ggp.base.util.statemachine.exceptions.GoalDefinitionException;
import org.ggp.base.util.statemachine.exceptions.MoveDefinitionException;
import org.ggp.base.util.statemachine.exceptions.TransitionDefinitionException;

import java.util.List;
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
    public int simulate(MCTSNode selectedElement, MCTSGamer gamer) {
        StateMachine stateMachine = gamer.getStateMachine();
        MachineState state = selectedElement.getMachineState();


        List<MachineState> states;
        while(!stateMachine.isTerminal(state)) {
            try {
                states = stateMachine.getNextStates(state);
                state = states.get(random.nextInt(states.size()));
            } catch (MoveDefinitionException | TransitionDefinitionException e) {
                e.printStackTrace();
            }
        }
        try {
            return stateMachine.getGoal(state,gamer.getRole());
        } catch (GoalDefinitionException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
