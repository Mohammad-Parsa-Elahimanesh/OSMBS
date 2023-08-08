package logic.shop.item.throwable;

import logic.shop.item.limits.Coins;
import logic.shop.item.limits.Level;
import logic.shop.item.limits.Limit;

public class SpeedBomb extends Throwable{
    public SpeedBomb() {
        super(new Limit[]{
                new Coins(15),
                new Level(2),
        });
    }
    @Override
    public String imageName() {
        return "speedBomb.png";
    }
}
