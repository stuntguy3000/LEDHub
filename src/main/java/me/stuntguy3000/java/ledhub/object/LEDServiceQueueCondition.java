package me.stuntguy3000.java.ledhub.object;

/**
 * @author stuntguy3000
 */
public enum LEDServiceQueueCondition {
    /**
     * Always add to the service queue
     */
    ALWAYS_QUEUE,
    /**
     * Force add to the front of the queue, kicking out the currently running transition
     */
    JUMP_QUEUE,
    /**
     * Never add to the service queue, meaning if an action is in the queue then disregard this.
     */
    RUN_IF_FREE
}
