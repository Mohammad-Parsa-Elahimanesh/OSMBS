package logic.shop.item.throwable;

import logic.shop.item.Item;
import logic.shop.item.limits.*;

public class DamageBomb extends Throwable {

    public DamageBomb() {
        super(new Limit[]{
                new AllCount((long) (24 * 3600), 3),
                new Gems(1),
                new Level(2)
        });
    }

    @Override
    public String imageName() {
        return "damageBomb.png";
    }
}
