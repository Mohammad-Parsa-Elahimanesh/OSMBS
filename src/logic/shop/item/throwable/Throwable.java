package logic.shop.item.throwable;

import logic.shop.item.Item;
import logic.shop.item.limits.Level;
import logic.shop.item.limits.Limit;

public abstract class Throwable extends Item {
    Throwable(Limit[] limits) {
        super(limits);
    }
}
