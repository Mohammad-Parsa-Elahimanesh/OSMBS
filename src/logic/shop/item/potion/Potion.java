package logic.shop.item.potion;

import logic.shop.item.Item;
import logic.shop.item.limits.Limit;


public abstract class Potion extends Item {
    public Potion(Limit[] limits) {
        super(limits);
    }

}
