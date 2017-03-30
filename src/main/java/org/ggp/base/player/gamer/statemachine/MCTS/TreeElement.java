package org.ggp.base.player.gamer.statemachine.MCTS;

import org.ggp.base.util.statemachine.Move;

public abstract class TreeElement {
	private Move move;
	private TreeNode parent;

	public TreeElement(Move move) {
		this.setMove(move);
		this.setParent(null);
	}

	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public abstract double GetScore();
}
