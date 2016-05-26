package me.stuntguy3000.java.ledhub.object;

/**
 * @author stuntguy3000
 */
public enum LEDServiceQueueCondition {
    /**
     * Always add to the service queue
     */
    ALWAYS,
    /**
     * Never add to the service queue, meaning if an action is in the queue then disregard this.
     */
    NEVER
}
