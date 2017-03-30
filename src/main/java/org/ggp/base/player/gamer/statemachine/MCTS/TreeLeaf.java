package org.ggp.base.player.gamer.statemachine.MCTS;

import org.ggp.base.util.statemachine.Move;
import org.ggp.base.util.statemachine.exceptions.MoveDefinitionException;

public class TreeLeaf extends TreeElement {

	public TreeLeaf(Move move, MCTSGamer gamer, TreeNode parent) {
		super(move,gamer,parent);
	}


	public TreeNode Extend() throws MoveDefinitionException {
		this.getParent().removeChild(this);
		TreeNode newNode = new TreeNode(this);
		return newNode;
	}

	@Override
	public double GetScore() {
		//This will most likely never get called
		return 1f/this.getParent().getChilds().size();
	}
}
