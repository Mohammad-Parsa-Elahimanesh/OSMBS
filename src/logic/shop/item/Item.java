package logic.shop.item;

import logic.shop.item.limits.Coins;
import logic.shop.item.limits.Gems;
import logic.shop.item.limits.Limit;

public abstract class Item {
    public final Limit[] limits;
    public Item(Limit[] limits) {
        this.limits = limits;
    }
    public abstract String imageName();
    public String name() {return imageName().split(".")[0];}
    public int neededCoins() {
        for(Limit limit : limits)
            if(limit instanceof Coins coins)
                return coins.neededCoins;
        return 0;
    }
    public int neededGems() {
        for(Limit limit : limits)
            if(limit instanceof Gems gems)
                return gems.neededGems;
        return 0;
    }
}
