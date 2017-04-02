package org.ggp.base.player.gamer.statemachine.MCTS.Simulation;

import org.ggp.base.player.gamer.statemachine.MCTS.Configurations.MCTSGamer;
import org.ggp.base.player.gamer.statemachine.MCTS.MCTSNode;
import org.ggp.base.util.statemachine.MachineState;
import org.ggp.base.util.statemachine.StateMachine;
import org.ggp.base.util.statemachine.exceptions.GoalDefinitionException;
import org.ggp.base.util.statemachine.exceptions.MoveDefinitionException;
import org.ggp.base.util.statemachine.exceptions.TransitionDefinitionException;

import java.util.List;
import java.util.Random;

/**
 * Function randomly selects a move from the selected tree element till the depth has been reached or a terminal state.
 * The returned score is the goal in the final Node.
 *
 * Created by Remco de Vos on 02/04/2017.
 */
public class RandomDepthSimulation implements SimulationFunction {

    private final Random random;
    private final int maximumDepth;
    private static final int DEFAULT_DEPTH = 10;

    public RandomDepthSimulation() {
        this(new Random());
    }

    public RandomDepthSimulation(Random random) {
        this(random, DEFAULT_DEPTH);
    }

    public RandomDepthSimulation(Random random, int maximumDepth) {
        this.random = random;
        this.maximumDepth = maximumDepth;
    }

    @Override
    public int simulate(MCTSNode selectedElement, MCTSGamer gamer) {
        StateMachine stateMachine = gamer.getStateMachine();
        MachineState state = selectedElement.getMachineState();
        int depth = maximumDepth;
        List<MachineState> states;
        // While we haven't reached a terminal state or the maximum depth make random moves
        while(depth > 0 && !stateMachine.isTerminal(state)) {
            try {
                states = stateMachine.getNextStates(state);
                state = states.get(random.nextInt(states.size()));
            } catch (MoveDefinitionException | TransitionDefinitionException e) {
                e.printStackTrace();
            }
            depth--;
        }
        try {
            return stateMachine.getGoal(state,gamer.getRole());
        } catch (GoalDefinitionException e) {
            return 0;
        }

    }
}
