package logic.shop.item.throwable;

import logic.shop.item.Item;
import logic.shop.item.limits.Coins;
import logic.shop.item.limits.Level;
import logic.shop.item.limits.Limit;
import logic.shop.item.limits.OnePersonCount;

public class Axe extends Item {
    public Axe() {
        super(new Limit[]{
                new OnePersonCount((long) (24 * 3600), 1),
                new Coins(20),
                new Level(2)
        });
    }

    @Override
    public String imageName() {
        return "axe.png";
    }
}
