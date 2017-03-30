package org.ggp.base.player.gamer.statemachine.MCTS;

import org.ggp.base.util.statemachine.Move;

public class TreeLeaf extends TreeElement {

	public TreeLeaf(Move move) {
		super(move);
	}

	@Override
	public TreeNode Select() {
		this.getParent().removeChild(this);
		TreeNode newNode = new TreeNode(this.getMove());
		this.getParent().addChild(newNode);
		return newNode;
	}

	@Override
	public double GetScore() {
		//This will most likely never get called
		return 1f/this.getParent().getChilds().size();
	}
}
