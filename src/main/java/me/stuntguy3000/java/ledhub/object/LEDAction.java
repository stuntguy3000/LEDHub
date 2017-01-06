package me.stuntguy3000.java.ledhub.object;

import lombok.Data;

/**
 * @author stuntguy3000
 */
@Data
public class LEDAction implements Cloneable {
    private LEDServiceActionType type;
    private LEDColour startColour;
    private LEDColour endColour;
    private LEDServiceQueueCondition ledServiceQueueCondition;
    private long actionLife = -1;
    private int playCount = 1;
    private long endDelay = -1;

    public LEDAction(LEDServiceActionType type, LEDColour startColour, LEDColour endColour) {
        this.type = type;
        this.startColour = startColour;
        this.endColour = endColour;
        this.ledServiceQueueCondition = LEDServiceQueueCondition.ALWAYS_QUEUE;
        this.actionLife = -1;
        this.playCount = 1;
        this.endDelay = -1;
    }

    public LEDAction(LEDServiceActionType type, LEDColour startColour, LEDColour endColour, LEDServiceQueueCondition ledServiceQueueCondition) {
        this.type = type;
        this.startColour = startColour;
        this.endColour = endColour;
        this.ledServiceQueueCondition = ledServiceQueueCondition;
        this.actionLife = -1;
        this.playCount = 1;
        this.endDelay = -1;
    }

    public LEDAction(LEDServiceActionType type, LEDColour startColour, LEDColour endColour, LEDServiceQueueCondition ledServiceQueueCondition, long actionLife) {
        this.type = type;
        this.startColour = startColour;
        this.endColour = endColour;
        this.ledServiceQueueCondition = ledServiceQueueCondition;
        this.actionLife = actionLife;
        this.playCount = 1;
        this.endDelay = -1;
    }

    public LEDAction(LEDServiceActionType type, LEDColour startColour, LEDColour endColour, LEDServiceQueueCondition ledServiceQueueCondition, long actionLife, int playCount) {
        this.type = type;
        this.startColour = startColour;
        this.endColour = endColour;
        this.ledServiceQueueCondition = ledServiceQueueCondition;
        this.actionLife = actionLife;
        this.playCount = playCount;
        this.endDelay = -1;
    }

    public LEDAction(LEDServiceActionType type, LEDColour startColour, LEDColour endColour, LEDServiceQueueCondition ledServiceQueueCondition, long actionLife, int playCount, int endDelay) {
        this.type = type;
        this.startColour = startColour;
        this.endColour = endColour;
        this.ledServiceQueueCondition = ledServiceQueueCondition;
        this.actionLife = actionLife;
        this.playCount = playCount;
        this.endDelay = endDelay;
    }

    @Override
    public LEDAction clone() {
        return new LEDAction(type, startColour, endColour, ledServiceQueueCondition, actionLife, playCount);
    }
}
