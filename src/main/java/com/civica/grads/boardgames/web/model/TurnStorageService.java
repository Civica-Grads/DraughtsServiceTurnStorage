package com.civica.grads.boardgames.web.model;

import com.civica.grads.boardgames.enums.Colour;
import com.civica.grads.boardgames.enums.CounterType;
import com.civica.grads.boardgames.exceptions.GameException;
import com.civica.grads.boardgames.model.MoveRecord;
import com.civica.grads.boardgames.model.Position;
import com.civica.grads.boardgames.model.TurnRecord;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public class TurnStorageService {

    private final ArrayList<TurnRecord> turnRecords = new ArrayList<TurnRecord>();
    
    
    public TurnStorageService() throws GameException
    {
        init();
    }
    
    
    
    
    
    /**
     * @return the turnRecords
     */
    public final ArrayList<TurnRecord> getTurnRecords() {
        return this.turnRecords;
    }





    /**
     * @return
     * @see java.util.ArrayList#isEmpty()
     */
    public boolean isEmpty() {
        return this.turnRecords.isEmpty();
    }





    /**
     * @param index
     * @return
     * @see java.util.ArrayList#get(int)
     */
    public TurnRecord get(int index) {
        return this.turnRecords.get(index);
    }





    /**
     * @param e
     * @return
     * @see java.util.ArrayList#add(java.lang.Object)
     */
    public boolean add(TurnRecord e) {
        return this.turnRecords.add(e);
    }





    /**
     * 
     * @see java.util.ArrayList#clear()
     */
    public void clear() {
        this.turnRecords.clear();
    }





    void init() throws GameException {
        
         /**
         * Turn record 1
         */
        
        
        addExampleTurnWithTwoMoves();
       
        /** 
         * Turn record 2
         */
        
        addExampleTurnWithOneMove();
 
        
    }


    private void addExampleTurnWithTwoMoves() {
        // Sample moves
        TurnRecord turnRecord = new TurnRecord();
        MoveRecord moveRecord1 = new MoveRecord(new Position(0, 0), new Position(3, 3), Colour.BLACK, CounterType.KING, true);
        MoveRecord moveRecord2 = new MoveRecord(new Position(3, 3), new Position(7, 7), Colour.BLACK, CounterType.KING, true);

        // add sample moves to turn record
        turnRecord.addMoveRecord(moveRecord1);
        turnRecord.addMoveRecord(moveRecord2);
        add(turnRecord);
    }

    private void addExampleTurnWithOneMove() {
        TurnRecord turnRecord = new TurnRecord();

        // Sample move
        MoveRecord moveRecord = new MoveRecord(new Position(2, 5), new Position(3, 6), Colour.WHITE, CounterType.NORMAL, false);

        turnRecord.addMoveRecord(moveRecord);
        add(turnRecord);
    }

}
