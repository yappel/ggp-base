package org.ggp.base.player.gamer.statemachine.MCTS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ggp.base.util.statemachine.MachineState;
import org.ggp.base.util.statemachine.Move;
import org.ggp.base.util.statemachine.exceptions.GoalDefinitionException;
import org.ggp.base.util.statemachine.exceptions.MoveDefinitionException;
import org.ggp.base.util.statemachine.exceptions.TransitionDefinitionException;

public class TreeNode extends TreeElement {

	private ArrayList<TreeElement> childs;

	private int wins = 0;
	private int totalPlays = 0;

	public TreeNode(Move move, MCTSGamer gamer) throws MoveDefinitionException {
		this(move,gamer,null);
	}

	public TreeNode(Move move, MCTSGamer gamer, TreeNode parent) throws MoveDefinitionException {
		super(move, gamer, parent);
		initChilds();
	}

	/**
	 * Replaces a leaf with a node
	 * @param leaf
	 * @throws MoveDefinitionException
	 */
	public TreeNode(TreeLeaf leaf) throws MoveDefinitionException {
		this(leaf.getMove(),leaf.getGamer(),leaf.getParent());
	}

	private void initChilds() throws MoveDefinitionException {
		this.childs = new ArrayList<TreeElement>();
		MCTSGamer gamer = this.getGamer();
		List<Move> moves = gamer.getStateMachine().getLegalMoves(gamer.getCurrentState(), gamer.getRole());
		for (Move move : moves) {
			this.childs.add(new TreeLeaf(move,gamer,this));
		}
	}

	public void addChild(TreeElement child) {
		this.childs.add(child);
	}

	public void removeChild(TreeElement child) {
		this.childs.remove(child);
	}

	public void AddResult(boolean win) {
		this.totalPlays++;
		if(win) this.wins++;
		if (this.getParent() != null) this.getParent().AddResult(win);
	}

	@Override
	public double GetScore() {
		return (double)this.wins/(double)this.totalPlays;
	}

	public TreeLeaf Select() {
		Collections.shuffle(this.childs);
		for (TreeElement child : childs) {
			if (child instanceof TreeLeaf) {
				return (TreeLeaf) child;
			}
		}
		TreeNode chosenChild = (TreeNode) this.childs.get(0);
		return chosenChild.Select();
	}

	public boolean Simulate() throws TransitionDefinitionException, MoveDefinitionException, GoalDefinitionException {
		MCTSGamer gamer = this.getGamer();
		MachineState finalState = gamer.getStateMachine().performDepthCharge(gamer.getCurrentState(), new int[1]);
		boolean result = gamer.getStateMachine().getGoal(finalState, gamer.getRole()) == 100;
		System.out.println("Did we win? " + result);
		return result;
	}

	public ArrayList<TreeElement> getChilds() {
		return childs;
	}
}
