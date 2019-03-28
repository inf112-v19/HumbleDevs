package inf112.skeleton.app.card;

/**
 * Returns the OptionCards options description
 *
 * @author Linh Nguyen
 */

public enum OptionCards {
    AblativeCoat, AbortSwitch, Brakes, CircuitBreaker, ConditionalProgram,
    CrabLegs, DoubleBarreledLaser, DualProcessor, ExtraMemory, FireControl,
    Flywheel, FourthGear, GyroscopicStabilizer, HighPoweredLaser, MechanicalArm,
    MiniHowitzer, PowerDownShield, PressorBeam, RadioControl, RammingGear,
    RearFiringLaser, Recompile, ReverseGear, Scrambler, SuperiorArchive, TractorBeam, Empty;


    public String toString() {
        if(this.equals(AblativeCoat)){
            return "Ablative Coat absorbs the next 3 Damage your robots receives " +
                    "but those Damage tokens onto this card instead of onto you Program Sheets. " +
                    "Discard this card and the tokens when you put the third one on.";
        }
        if(this.equals(AbortSwitch)){
            return "Once each turn, you may replace one of the ProgramCards you reveal with the top card from deck. " +
                    "If do, you must replace all the Program cards for the remaining registers the same that turn";
        }
        if(this.equals(Brakes)){
            return "Whenever you execute a Move 1, you may move your robot 0 spaces instead of 1. " +
                    "Priority is that of the Move 1";
        }
        if(this.equals(CircuitBreaker)){
            return " If you have 3 or more Damage tokens on your Program Sheet at the end of your turn, " +
                    "your robot will begin the next turn powered down";
        }
        if(this.equals(ConditionalProgram)){
            return "After you program registers each turn, " + "" +
                    "you may put one of the Program cards left in your hand face down onto this Option instead of discarding it. " +
                    "Later that turn, you can substitute that card for one you had programmed in any register, discarding the original card. " +
                    "Announce the change before anyone reveals Program cards for that register. " +
                    "If you put a card on this option and don't use it, discard it at the end of the turn";
        }
        if(this.equals(CrabLegs)){
            return "When programming your registers, " +
                    "you may put a Move 1 card in the same register as a Rotate Left or Rotate Right card. " +
                    "If you do, during that register your robot will move 1 space to left or right, respectively, without rotating. " +
                    "Priority is that of the Move 1";
        }
        if(this.equals(DoubleBarreledLaser)){
            return "Whenever your robot fires its main laser, " +
                    "it fires two shots instead of one. " +
                    "You may use thus Option with Fire Control and/or High-Power Laser.";
        }
        if(this.equals(DualProcessor)){
            return "When programming your registers, " +
                    "you may put both a Move card (Move 1, Move 2, Move 3, or Back Up) " +
                    "and a Rotate card (Rotate Left, Rotate Right, or U-Turn) in the same register. " +
                    "lf you do, during that phase your robot will move 1 space less than the Move card says to move and then execute the Rotate card. " +
                    "If the Rotate card is a U-Turn, move 2 spaces less than the Move card says if possible.";
        }
        if(this.equals(ExtraMemory)){
            return "You receive one extra Program card each turn. " +
                    "(You still discard all unused Program cards when you're done programming your registers.)";
        }
        if (this.equals(FireControl)) {
            return "Whenever your robot hits another robot with its main laser, " +
                    "instead of doing damage you may choose one of the target robot's registers and lock it or choose one of that player's Options and it. " +
                    "(The player can't discard an Option to avoid this effect.)";
        }
        if(this.equals(Flywheel)) {
            return "After all players are done programming their registers each turn, " +
                    "you may put one of your remaining Program cards face down onto this card. " +
                    "You can add that Program card to those dealt to you on any subsequent turn. " +
                    "You can have only one card on Flywheel at a time.";
        }
        if (this.equals(FourthGear)) {
            return "Whenever you execute a Move 3, you may move your robot 4 spaces instead of 3. " +
                    "Priority is that of the Move 3.";
        }
        if(this.equals(GyroscopicStabilizer)) {
            return "Before players reveal the cards in their first registers each turn, " +
                    "state whether this Option is active.When it is, " +
                    "your robot isn't rotated by gears or rotating conveyor belts for that entire turn";
        }
        if(this.equals(HighPoweredLaser)) {
            return "Your robot's main laser can shoot through one wall or robot to get to a target robot. " +
                    "If you shoot through a robot that robot also receives full damage. " +
                    "You may use this Option with Fire Control and/or Double-Barreled Laser.";
        }
        if(this.equals(MechanicalArm)) {
            return "Your robot can touch a flag or repair site from 1 space away (diagonally or orthogonally), " +
                    "as long as there isn't a wall between it and the flag or repair site.";
        }
        if(this.equals(MiniHowitzer)){
            return "Whenever you could fire your main laser at a robot, " +
                    "you may tire the Mini Howitzer instead. " +
                    "This pushes the target robot 1 space away from your robot, " +
                    "and the target robot receives 1 Damage token. " +
                    "(Robots can't be pushed through walls.) " +
                    "You may use this Option five times. " +
                    "Put a Damage token on this card each time you use it and discard this card and the tokens when you put the fifth one on.";
        }
        if(this.equals(PowerDownShield)) {
            return "As long as your robot is powered down, " +
                    "each register phase you can prevent up to 1 Damage to it from each of the four directions.";
        }
        if(this.equals(PressorBeam)) {
            return "Whenever you could fire your main laser at a robot you may instead fire the Pressor Beam. " +
                    "This moves the target robot 1 space away from your robot.";
        }
        if(this.equals(RadioControl)){
            return "Whenever you could fire our main laser at a robot, " +
                    "you may instead fire the Radio Control beam. " +
                    "This causes the target robot to execute your robot's program for the rest of the turn. " +
                    "In cases of card priority, the target robot moves immediately after your robot.";
        }
        if(this.equals(RammingGear)) {
            return "Whenever your robot pushes or bumps into another robot, that robot receives 1 Damage token.";
        }
        if(this.equals(RearFiringLaser)){
            return "Your robot has a rear-firing laser in addition to its main laser. " +
                    "This laser follows all the same rules as the main laser.";
        }
        if(this.equals(Recompile)){
            return "Once each turn, you may discard the hand of Program cards dealt to you and draw a new hand from the deck. " +
                    "Your robot then receives 1 Damage token.";
        }
        if(this.equals(ReverseGear)) {
            return "Whenever you execute a Back Up, you may move your robot back 2 spaces instead of Priority is that of the Back Up.";
        }
        if(this.equals(Scrambler)) {
            return "Whenever you could fire your main laser at a robot, " +
                    "you may instead fire the Scrambler. " +
                    "This replaces the target robot's next programmed card with the top Program card from the deck. " +
                    "You can't use this Option on the fifth register.";
        }
        if(this.equals(SuperiorArchive)){
            return "When reentering play after being destroyed your robot doesn't receive the normal 2 Damage tokens.";
        }
        if(this.equals(TractorBeam)) {
            return "Whenever you could fire your main laser at a robot that isn't in an adjacent space, " +
                    "you may instead fire the Tractor Beam. " +
                    "This pulls the target robot space toward your robot.";
        }
        return "No card options assigned.";
    }
}