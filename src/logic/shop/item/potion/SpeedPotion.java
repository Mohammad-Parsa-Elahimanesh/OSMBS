package logic.shop.item.potion;

import logic.shop.item.limits.AllCount;
import logic.shop.item.limits.Coins;
import logic.shop.item.limits.Level;
import logic.shop.item.limits.Limit;

public class SpeedPotion extends Potion{
    public SpeedPotion() {
        super(new Limit[]{
                new AllCount((long) (24 * 3600), 10),
                new Coins(12),
                new Level(1)
        });
    }
    @Override
    public String imageName() {
        return "speedPotion.png";
    }
}
