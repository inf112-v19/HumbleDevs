package inf112.skeleton.app.card;
import java.util.Collections;
import  java.util.Stack;

public class ProgramCardDeck implements IDeck{

    private Stack<ProgramCard> StackCard;

    public ProgramCardDeck(){



        StackCard = new Stack<>();

        for(int i = 0; i < 18; i ++) { //rotate right
            StackCard.add(new ProgramCard(80+20*i, 0, 1 ));
        }

        for(int i = 18; i < 36; i ++) { //rotate left
            StackCard.add(new ProgramCard(70+20*i, 0, -1 ));
        }

        for(int i = 36; i < 42; i ++) {   //rotate 180
            StackCard.add(new ProgramCard(10+10*i, 0, 2 ));
        }

        for(int i = 42; i < 60; i ++) { //move forward 1
            StackCard.add(new ProgramCard(490+20*i-42, 1, 0 ));
        }

        for(int i = 60; i < 72; i ++) { //move forward 2
            StackCard.add(new ProgramCard(670+10*i, 2, 0 ));
        }

        for(int i = 72; i < 78; i ++) { //move forward 3
            StackCard.add(new ProgramCard(790+10*i, 3, 0 ));
        }

        for(int i = 78; i < 84; i ++) { //move backward
            StackCard.add(new ProgramCard(430+10*i-78, -1, 0 ));
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
