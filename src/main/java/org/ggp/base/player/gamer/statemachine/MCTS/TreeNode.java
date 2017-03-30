package org.ggp.base.player.gamer.statemachine.MCTS;

import java.util.ArrayList;
import java.util.Collections;

import org.ggp.base.util.statemachine.Move;

public class TreeNode extends TreeElement {

	private ArrayList<TreeElement> childs;

	private int wins = 0;
	private int totalPlays = 0;

	public TreeNode(Move move) {
		super(move);
		this.childs = new ArrayList<TreeElement>();
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

	@Override
	public TreeNode Select() {
		Collections.shuffle(this.childs);
		for (TreeElement child : childs) {
			if (child instanceof TreeLeaf) {
				return child.Select();
			}
		}
		return this.childs.get(0).Select();
	}

	public boolean ExtendAndSimulate() {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList<TreeElement> getChilds() {
		return childs;
	}
}
