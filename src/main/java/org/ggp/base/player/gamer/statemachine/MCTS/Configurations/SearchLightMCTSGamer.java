package org.ggp.base.player.gamer.statemachine.MCTS.Configurations;

public class SearchLightMCTSGamer extends DefaultMCTSGamer {

	public SearchLightMCTSGamer() {
		super(true);
	}

    @Override
    public String getName() {
        return "SearchLight_MCTS";
    }

}
