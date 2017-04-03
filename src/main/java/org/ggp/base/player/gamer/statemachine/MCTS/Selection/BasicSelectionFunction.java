package org.ggp.base.player.gamer.statemachine.MCTS.Selection;

import org.ggp.base.player.gamer.statemachine.MCTS.MCTSNode;

/**
 * This function returns the next node in the list of children of the root node. Does make the assumption that the
 * function is called several times. Keeps track of the index and the total size of the children in the root node. As
 * it only returns from the list of children, the following expansion step will not expand the tree.    
 *
 * Created by Remco de Vos on 02/04/2017.
 */
public class BasicSelectionFunction implements SelectionFunction {

    private int count = 0;
    private int childrenSize = 0;

    @Override
    public MCTSNode select(MCTSNode node) {
        MCTSNode selected = node;
        if(!selected.isLeafNode()) {
            this.childrenSize = selected.getChildren().size();
            this.count = this.count % this.childrenSize;
            selected = selected.getChildren().get(this.count);
            this.count++;
        }
        return selected;
    }
}
