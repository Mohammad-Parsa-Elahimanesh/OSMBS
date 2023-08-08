package logic.shop.item.potion;

import logic.shop.item.limits.*;

public class HealPotion extends Potion{
    public HealPotion() {
        super(new Limit[]{
                new AllCount((long) (24 * 3600), 10),
                new Coins(20),
                new Level(1)
        });
    }
    @Override
    public String imageName() {
        return "healPotion.png";
    }
}
