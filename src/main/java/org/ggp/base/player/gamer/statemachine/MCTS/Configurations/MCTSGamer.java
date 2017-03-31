package org.ggp.base.player.gamer.statemachine.MCTS.Configurations;

import org.ggp.base.player.gamer.event.GamerSelectedMoveEvent;
import org.ggp.base.player.gamer.exception.GamePreviewException;
import org.ggp.base.player.gamer.statemachine.MCTS.Backpropagation.BackpropagationFunction;
import org.ggp.base.player.gamer.statemachine.MCTS.Expansion.ExpansionFunction;
import org.ggp.base.player.gamer.statemachine.MCTS.MCTSNode;
import org.ggp.base.player.gamer.statemachine.MCTS.MoveSelection.MoveSelectionFunction;
import org.ggp.base.player.gamer.statemachine.MCTS.Selection.SelectionFunction;
import org.ggp.base.player.gamer.statemachine.MCTS.Simulation.SimulationFunction;
import org.ggp.base.player.gamer.statemachine.StateMachineGamer;
import org.ggp.base.util.game.Game;
import org.ggp.base.util.logging.GamerLogger;
import org.ggp.base.util.statemachine.Move;
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

	public MCTSGamer() {
		super();
		this.selectionFunction = getSelectionFunction();
		this.expansionFunction = getExpansionFunction();
		this.simulationFunction = getSimulationFunction();
		this.backpropagationFunction = getBackpropagationFunction();
        this.moveSelectionFunction = getMoveSelectionFunction();
        GamerLogger.setFileToDisplay("StateMachine");
        GamerLogger.setFileToDisplay("ExecutiveSummary");
        GamerLogger.setFileToDisplay("Proxy");

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
        if (root == null) {
            root = new MCTSNode(this.getCurrentState(), this.getRole(), null, null, null, this);
        } else{
            for (MCTSNode node: root.getChildren()) {
                if(node.getMachineState().equals(getCurrentState())) {
                    root = node;
                    root.setParent(null);
                }
            }
        }

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

        stateMachineMetaGame(timeout);

        System.out.println("Simulations: " + root.getVisits());
        System.out.println("Nodes: " + root.countNodes());

        Move move = moveSelectionFunction.selectMove(root);

        long stop = System.currentTimeMillis();
        notifyObservers(new GamerSelectedMoveEvent(getStateMachine().getLegalMoves(getCurrentState(), getRole()), move, stop - start));
        return move;
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
