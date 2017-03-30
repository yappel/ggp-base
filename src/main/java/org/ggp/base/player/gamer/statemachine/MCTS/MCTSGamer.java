package org.ggp.base.player.gamer.statemachine.MCTS;

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
import org.ggp.base.util.statemachine.Move;
import org.ggp.base.util.statemachine.StateMachine;
import org.ggp.base.util.statemachine.exceptions.GoalDefinitionException;
import org.ggp.base.util.statemachine.exceptions.MoveDefinitionException;
import org.ggp.base.util.statemachine.exceptions.TransitionDefinitionException;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class MCTSGamer extends StateMachineGamer {

	private final SelectionFunction selectionFunction;
	private final ExpansionFunction expansionFunction;
	private final SimulationFunction simulationFunction;
	private final BackpropagationFunction backpropagationFunction;
	private final Random random;

	public MCTSGamer() {
		super();
		this.random = new Random();
		this.selectionFunction = new UniformSelectionFunction(this.random);
		this.expansionFunction = new BasicRandomExpansionFunction(this.random);
		this.simulationFunction = new RandomWinLossSimulation(this.random);
		this.backpropagationFunction = new DefaultBackpropagationFunction();
	}


	@Override
	public StateMachine getInitialStateMachine() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stateMachineMetaGame(long timeout)
			throws TransitionDefinitionException, MoveDefinitionException, GoalDefinitionException {
		// TODO Auto-generated method stub

	}

	@Override
	public Move stateMachineSelectMove(long timeout)
			throws TransitionDefinitionException, MoveDefinitionException, GoalDefinitionException {
		StateMachine theMachine = getStateMachine();
        long start = System.currentTimeMillis();
        long finishBy = timeout - 1000;
        TreeNode rootNode = new TreeNode(null,this,null);

        MCTSNode root = new MCTSNode(this.getCurrentState(), this.getRole(), null, null, null, this);

        while(System.currentTimeMillis() < finishBy) {
			MCTSNode selected = this.selectionFunction.select(root);
			selected = this.expansionFunction.expand(selected, this);
			int score = this.simulationFunction.simulate(selected, this);
			this.backpropagationFunction.updateScore(selected, score);
        }

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
		return bestMove;
	}

	@Override
	public void stateMachineStop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stateMachineAbort() {
		// TODO Auto-generated method stub

	}

	@Override
	public void preview(Game g, long timeout) throws GamePreviewException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "MonteCarloTreeSearchGamer";
	}

}
