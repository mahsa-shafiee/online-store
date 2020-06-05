package util;

import java.util.List;

public enum OperationType {

    REGISTER("register"), LOGIN("login"), ORDER("ordering product")
    , VIEW_PRODUCT("viewing product"), VIEW_CART("viewing cart")
    , ADD_TO_CART("adding to cart"), DELETE_FROM_CART("deleting from cart")
    , VIEW_ORDERS("viewing orders"), LOGOUT("logout"), EXIT("exit");


    private String operation;
    private int itemId;
    private List<Integer> itemsIds;


    OperationType(String operation) {
        this.operation = operation;
    }


    public void setItemId(int itemId) {
        this.itemId = itemId;
    }


    public void setItemsIds(List<Integer> itemsIds) {
        this.itemsIds = itemsIds;
    }


    public String getOperation() {
        return itemId == 0 ? (itemsIds == null ? (operation) : (operation + "(cart products ids=" + itemsIds + ")")) : (operation + "(product id=" + itemId + ")");
    }
}
