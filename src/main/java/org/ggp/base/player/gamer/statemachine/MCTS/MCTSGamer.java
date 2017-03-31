package org.ggp.base.player.gamer.statemachine.MCTS;

import org.ggp.base.player.gamer.event.GamerSelectedMoveEvent;
import org.ggp.base.player.gamer.exception.GamePreviewException;
import org.ggp.base.player.gamer.statemachine.MCTS.Backpropagation.BackpropagationFunction;
import org.ggp.base.player.gamer.statemachine.MCTS.Backpropagation.DefaultBackpropagationFunction;
import org.ggp.base.player.gamer.statemachine.MCTS.Expansion.BasicRandomExpansionFunction;
import org.ggp.base.player.gamer.statemachine.MCTS.Expansion.ExpansionFunction;
import org.ggp.base.player.gamer.statemachine.MCTS.Selection.SelectionFunction;
import org.ggp.base.player.gamer.statemachine.MCTS.Selection.UniformSelectionFunction;
import org.ggp.base.player.gamer.statemachine.MCTS.Simulation.RandomWinLossSimulation;
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

import java.util.List;
import java.util.Map;
import java.util.Random;

public class MCTSGamer extends StateMachineGamer {

	private final SelectionFunction selectionFunction;
	private final ExpansionFunction expansionFunction;
	private final SimulationFunction simulationFunction;
	private final BackpropagationFunction backpropagationFunction;
	private final Random random;
	private MCTSNode root;

	public MCTSGamer() {
		super();
		this.random = new Random();
		this.selectionFunction = new UniformSelectionFunction(this.random);
		this.expansionFunction = new BasicRandomExpansionFunction(this.random);
		this.simulationFunction = new RandomWinLossSimulation(this.random);
		this.backpropagationFunction = new DefaultBackpropagationFunction();
        GamerLogger.setFileToDisplay("StateMachine");
        GamerLogger.setFileToDisplay("ExecutiveSummary");
        GamerLogger.setFileToDisplay("Proxy");

	}


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
        System.out.println("Nodes: " + countNodes(root));

        Map<Move, List<MCTSNode>> children = root.getChildrenMap();
        Move bestMove = null;
        double scoreRatio = 0;

        for(Move move : children.keySet()) {
        	List<MCTSNode> nodes = children.get(move);
        	int totalVisits = 0;
        	int totalScore = 0;
        	for(MCTSNode node : nodes) {
        		totalVisits += node.getVisits();
        		totalScore += node.getScore();
			}
			double ratio = (double) totalScore / (double) totalVisits;
        	if (ratio > scoreRatio) {
        		scoreRatio = ratio;
        		bestMove = move;
			}
		}


        long stop = System.currentTimeMillis();
        notifyObservers(new GamerSelectedMoveEvent(getStateMachine().getLegalMoves(getCurrentState(), getRole()), bestMove, stop - start));
        return bestMove;
	}

	@Override
	public void stateMachineStop() {
		// TODO Auto-generated method stub
        root = null;
	}

	@Override
	public void stateMachineAbort() {
		// TODO Auto-generated method stub
        root = null;
	}

	@Override
	public void preview(Game g, long timeout) throws GamePreviewException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "MonteCarloTreeSearchGamer";
	}

    public int countNodes(MCTSNode node) {
        if (node.getVisits() == 0){
            return 0;
        }
        if (node.getChildrenMap() == null){
            return 1;
        }
        int nodes = 0;
        for (MCTSNode n : node.getChildren()) {
            nodes += countNodes(n);
        }
        return nodes + 1;
    }

}
