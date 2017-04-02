package org.ggp.base.player.gamer.statemachine.MCTS.Configurations;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.ggp.base.player.gamer.event.GamerSelectedMoveEvent;
import org.ggp.base.player.gamer.exception.GamePreviewException;
import org.ggp.base.player.gamer.statemachine.StateMachineGamer;
import org.ggp.base.player.gamer.statemachine.MCTS.MCTSNode;
import org.ggp.base.player.gamer.statemachine.MCTS.Backpropagation.BackpropagationFunction;
import org.ggp.base.player.gamer.statemachine.MCTS.Expansion.ExpansionFunction;
import org.ggp.base.player.gamer.statemachine.MCTS.MoveSelection.MoveSelectionFunction;
import org.ggp.base.player.gamer.statemachine.MCTS.Selection.SelectionFunction;
import org.ggp.base.player.gamer.statemachine.MCTS.Simulation.SimulationFunction;
import org.ggp.base.util.game.Game;
import org.ggp.base.util.logging.GamerLogger;
import org.ggp.base.util.statemachine.MachineState;
import org.ggp.base.util.statemachine.Move;
import org.ggp.base.util.statemachine.Role;
import org.ggp.base.util.statemachine.StateMachine;
import org.ggp.base.util.statemachine.cache.CachedStateMachine;
import org.ggp.base.util.statemachine.exceptions.GoalDefinitionException;
import org.ggp.base.util.statemachine.exceptions.MoveDefinitionException;
import org.ggp.base.util.statemachine.exceptions.TransitionDefinitionException;
import org.ggp.base.util.statemachine.implementation.prover.ProverStateMachine;

public abstract class MCTSGamer extends StateMachineGamer {

	private final SelectionFunction selectionFunction;
	private final ExpansionFunction expansionFunction;
	private final SimulationFunction simulationFunction;
	private final BackpropagationFunction backpropagationFunction;
	private final MoveSelectionFunction moveSelectionFunction;
    private MCTSNode root;
    private boolean useSearchLight;

    protected final Random random;

	public MCTSGamer(boolean useSearchLight) {
		super();
		this.selectionFunction = getSelectionFunction();
		this.expansionFunction = getExpansionFunction();
		this.simulationFunction = getSimulationFunction();
		this.backpropagationFunction = getBackpropagationFunction();
        this.moveSelectionFunction = getMoveSelectionFunction();
        GamerLogger.setFileToDisplay("StateMachine");
        GamerLogger.setFileToDisplay("ExecutiveSummary");
        GamerLogger.setFileToDisplay("Proxy");
        GamerLogger.setFileToDisplay("SearchLight");
        this.useSearchLight = useSearchLight;
        this.random = new Random();
    }

    public abstract SelectionFunction getSelectionFunction();
    public abstract ExpansionFunction getExpansionFunction();
    public abstract SimulationFunction getSimulationFunction();
    public abstract BackpropagationFunction getBackpropagationFunction();
    public abstract MoveSelectionFunction getMoveSelectionFunction();

	@Override
	public StateMachine getInitialStateMachine() {
		return new CachedStateMachine(new ProverStateMachine());
	}

	@Override
	public void stateMachineMetaGame(long timeout)
			throws TransitionDefinitionException, MoveDefinitionException, GoalDefinitionException {
        long finishBy = timeout - 1000;

        setTree();

        while(System.currentTimeMillis() < finishBy) {
            MCTSNode selected = this.selectionFunction.select(root);
            selected = this.expansionFunction.expand(selected, this);
            int score = this.simulationFunction.simulate(selected, this);
            this.backpropagationFunction.updateScore(selected, score);
        }

	}

	@Override
	public Move stateMachineSelectMove(long timeout)
			throws TransitionDefinitionException, MoveDefinitionException, GoalDefinitionException {

        long start = System.currentTimeMillis();

        setTree();

        Move move = null;
        if(this.useSearchLight) {
        	move = searchLightTurnBased(root, getStateMachine());
        }


        Map<Move, List<MCTSNode>> childrenMap = root.getChildrenMap();

        if (childrenMap != null) {
            System.out.println(childrenMap.keySet());
        }

        if (childrenMap != null && childrenMap.keySet().size() == 1) {
            move = root.getChildren().get(0).getMove();
        } else if (move == null) {
            stateMachineMetaGame(timeout);

            move = moveSelectionFunction.selectMove(root);
        }

        long stop = System.currentTimeMillis();
        notifyObservers(new GamerSelectedMoveEvent(getStateMachine().getLegalMoves(getCurrentState(), getRole()), move, stop - start));
        return move;
	}

	private Move searchLightTurnBased(MCTSNode root, StateMachine stateMachine) throws TransitionDefinitionException, MoveDefinitionException {
        System.out.println("SearchLight");
        Map<Move, List<MCTSNode>> children = root.getChildrenMap();
        if (children == null) {
            root.createChildren();
            children = root.getChildrenMap();
        }
        for (Move move : children.keySet()) {
            MCTSNode child = children.get(move).get(0);
// Checking if current move leads to guaranteed win.
            if (checkTerminalGoal(child.getMachineState(), stateMachine, root.getGamer().getRole(), 100)) {
                return child.getMove();
            }

// Checking whether next move contains contains guaranteed lose and removes it from the options.
            for (MachineState node : stateMachine.getNextStates(child.getMachineState())) {
                if (checkTerminalGoal(node, stateMachine, root.getGamer().getRole(), 0)) {
                    if (children.keySet().size() > 1) {
                        children.remove(move);
                        System.out.println("Removed:" + move);
                    }
                }
            }

        }

        return null;
    }


    private boolean checkTerminalGoal(MachineState node, StateMachine stateMachine, Role role, int goalValue) {
        if (stateMachine.isTerminal(node)) {
            try {
                if (stateMachine.getGoal(node, role) == goalValue) {
                    return true;
                }
            } catch (GoalDefinitionException e) {
                GamerLogger.logError("SearchLight", e.getMessage());
            }
        }
        return false;
    }

    private void setTree() {
        if (root == null) {
            root = new MCTSNode(this.getCurrentState(), this.getRole(), null, null, null, this);
        } else if (!root.getMachineState().equals(getCurrentState())) {
            for (MCTSNode node : root.getChildren()) {
                if (node.getMachineState().equals(getCurrentState())) {
                    root = node;
                    root.setParent(null);
                }
            }
        }
    }

	@Override
	public void stateMachineStop() {
        root = null;
	}

	@Override
	public void stateMachineAbort() {
        root = null;
	}

	@Override
	public void preview(Game g, long timeout) throws GamePreviewException {
		// TODO Auto-generated method stub
	}
}
