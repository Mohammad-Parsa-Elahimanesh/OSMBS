package logic.shop.item.potion;

import logic.shop.item.limits.*;

public class Invisibility extends Potion{
    public Invisibility() {
        super(new Limit[]{
                new AllCount((long) (24 * 3600), 10),
                new Gems(1),
                new Coins(5),
                new Level(1)
        });
    }
    @Override
    public String imageName() {
        return "invisibility.png";
    }
}
