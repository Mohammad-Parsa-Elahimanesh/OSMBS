package logic.shop.item.throwable;

import logic.shop.item.limits.*;

public class SpecialSaber extends Throwable{
    public SpecialSaber() {
        super(new Limit[]{
                new OnePersonCount((long) (24 * 3600), 2),
                new Gems(2),
                new Level(2)
        });
    }
    @Override
    public String imageName() {
        return "specialSaber.png";
    }
}
