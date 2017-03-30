package org.ggp.base.player.gamer.statemachine.MCTS;

import org.ggp.base.player.gamer.exception.GamePreviewException;
import org.ggp.base.player.gamer.statemachine.StateMachineGamer;
import org.ggp.base.util.game.Game;
import org.ggp.base.util.statemachine.Move;
import org.ggp.base.util.statemachine.StateMachine;
import org.ggp.base.util.statemachine.exceptions.GoalDefinitionException;
import org.ggp.base.util.statemachine.exceptions.MoveDefinitionException;
import org.ggp.base.util.statemachine.exceptions.TransitionDefinitionException;

public class MCTSGamer extends StateMachineGamer {

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

        while(System.currentTimeMillis() < finishBy) {
        	TreeLeaf selectedLeaf = rootNode.Select();
        	TreeNode selectedNode = selectedLeaf.Extend();
        	boolean simResultedInWin = selectedNode.Simulate();
        	selectedNode.AddResult(simResultedInWin);
        }

        //It's OK to have a favorite child here
        TreeElement mostPromisingChild = null;
        for (TreeElement child : rootNode.getChilds()) {
        	if (mostPromisingChild == null || mostPromisingChild.GetScore() < child.GetScore()) {
        		mostPromisingChild = child;
        	}
        }

        return mostPromisingChild.getMove();
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
