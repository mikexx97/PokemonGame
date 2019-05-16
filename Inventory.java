package nl.delphinity.pokemon.model.item;

import java.util.HashMap;

public class Inventory {

    private final HashMap<ItemType, Integer> items;

    public Inventory() {
        items = new HashMap<>();
    }

    public HashMap<ItemType, Integer> getItems() {
        return items;
    }

    public void addItem(int amount, ItemType type) {
        int currentAmount = items.getOrDefault(type, 0);
        if(currentAmount > 0) {
            items.put(type, currentAmount + amount);
        } else {
            items.put(type, amount);
        }
    }

    public void removeItem(ItemType type) {
        int currentAmount = items.getOrDefault(type, 0);
        if(currentAmount > 0) {
            items.put(type, currentAmount - 1);
        } else {
            System.out.println("You don't have this item in your inventory!");
        }
    }
}
