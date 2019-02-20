package inf112.skeleton.app.card;
import java.util.Collections;
import  java.util.Stack;

public class ProgramCardDeck implements IDeck{

    private Stack<ProgramCard> moveCard;

    public ProgramCardDeck(){

        moveCard = new Stack<>();

        for(int i = 0; i < 18; i ++) { //rotate right
            moveCard.add(new ProgramCard(80+20*i, 0, 1 ));
        }

        for(int i = 18; i < 36; i ++) { //rotate left
            moveCard.add(new ProgramCard(70+20*i, 0, -1 ));
        }

        for(int i = 36; i < 42; i ++) {   //rotate 180
            moveCard.add(new ProgramCard(10+10*(i-36), 0, 2 ));
        }

        for(int i = 42; i < 60; i ++) { //move forward 1
            moveCard.add(new ProgramCard(490+20*(i-42), 1, 0 ));
        }

        for(int i = 60; i < 72; i ++) { //move forward 2
            moveCard.add(new ProgramCard(670+10*(i-60), 2, 0 ));
        }

        for(int i = 72; i < 78; i ++) { //move forward 13
            moveCard.add(new ProgramCard(790+10*(i-72), 3, 0 ));
        }

        for(int i = 78; i < 84; i ++) { //move backward
            moveCard.add(new ProgramCard(430+10*(i-78), -1, 0 ));
        }

        Collections.shuffle(moveCard);

    }


    @Override
    public void shuffle(){
        Collections.shuffle(moveCard);
    }

    @Override
    public Stack<ProgramCard> draw(int n){
        Stack<ProgramCard> drawn = new Stack<>();
        for (int i =0; i <n; i++){
           drawn.add(moveCard.pop());

        }

        return drawn;
    }

    @Override
    public boolean isEmpty(){
        return moveCard.isEmpty();
    }



}
