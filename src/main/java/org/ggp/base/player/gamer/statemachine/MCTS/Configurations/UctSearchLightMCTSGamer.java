package org.ggp.base.player.gamer.statemachine.MCTS.Configurations;

public class UctSearchLightMCTSGamer extends UctMCTSGamer {

	public UctSearchLightMCTSGamer() {
		super(true);
	}

	@Override
	public String getName() {
		return "UCT_SearchLight_MCTS";

	}
}
