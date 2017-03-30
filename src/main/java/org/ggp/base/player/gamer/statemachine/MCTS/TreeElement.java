package org.ggp.base.player.gamer.statemachine.MCTS;

import org.ggp.base.util.statemachine.Move;

public abstract class TreeElement {
	private Move move;
	private TreeNode parent;
	private MCTSGamer gamer;

	public TreeElement(Move move, MCTSGamer gamer, TreeNode parent) {
		this.setMove(move);
		this.setParent(parent);
		this.gamer = gamer;
	}

	public MCTSGamer getGamer() {
		return this.gamer;
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
