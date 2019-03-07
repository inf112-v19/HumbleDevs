package inf112.skeleton.app.card;
import java.util.Collections;
import  java.util.Stack;

public class ProgramCardDeck implements IDeck{

    private Stack<ProgramCard> StackCard;

    public ProgramCardDeck(){



        StackCard = new Stack<>();

        for(int i = 0; i < 18; i ++) { //rotate right
            StackCard.add(new ProgramCard(80+20*i, 0, Action.RIGHT ));
        }

        for(int j = 18; j < 36; j ++) { //rotate left
            StackCard.add(new ProgramCard(70+20*j, 0, Action.LEFT ));
        }

        for(int k = 36; k < 42; k ++) {   //rotate 180
            StackCard.add(new ProgramCard(10+10*k, 0, Action.UTURN ));
        }

        for(int l = 42; l < 60; l ++) { //move forward 1
            StackCard.add(new ProgramCard(490+20*l-42, 1,Action.MOVEFORWARD ));
        }

        for(int m = 60; m < 72; m ++) { //move forward 2
            StackCard.add(new ProgramCard(670+10*m, 2,Action.MOVEFORWARD ));
        }

        for(int n = 72; n < 78; n ++) { //move forward 3
            StackCard.add(new ProgramCard(790+10*n, 3, Action.MOVEFORWARD));
        }

        for(int o = 78; o < 84; o ++) { //move backward
            StackCard.add(new ProgramCard(430+10*o-78, -1, Action.MOVEBACKWARD));
        }

        Collections.shuffle(StackCard);

    }


    @Override
    public void shuffle(){
        Collections.shuffle(StackCard);
    }


    @Override
    public ProgramCard getCard(){
        ProgramCard card = StackCard.get(0);
        StackCard.remove(0);
        return card;
    }


    @Override
    public void addCard(ProgramCard card){
        StackCard.add(card);
    }

    @Override
    public int size(){
        return StackCard.size();

    }

    @Override
    public boolean isEmpty(){
        return StackCard.isEmpty();
    }



}
