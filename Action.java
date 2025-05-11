/**
 * Action class to track cart operations for undo functionality
 */
public class Action {
    public enum ActionType {
        ADD,
        REMOVE
    }
    
    private ActionType actionType;
    private String productId;
    private int quantity;
    
    // Constructor
    public Action(ActionType actionType, String productId, int quantity) {
        this.actionType = actionType;
        this.productId = productId;
        this.quantity = quantity;
    }
    
    // Getters
    public ActionType getActionType() {
        return actionType;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    @Override
    public String toString() {
        return "Action{" +
                "actionType=" + actionType +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}