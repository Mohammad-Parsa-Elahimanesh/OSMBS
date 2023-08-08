package logic.shop.item.limits;

import logic.User;
import logic.shop.Commodity;
import logic.shop.item.Item;

import java.util.List;

public class Gems extends Limit{
    public final int neededGems;
    public static int getSumNeededGems(Commodity commodity) {
        int sumNeededGems = 0;
        for(Item item: commodity.items)
            sumNeededGems += item.neededGems();
        return sumNeededGems;
    }
    public Gems(int neededGems) {
        this.neededGems = neededGems;
    }

    @Override
    public BuyStatus canBuy(User buyer, List<Commodity> sells, Commodity wanted) {
        return getSumNeededGems(wanted) <= buyer.gems?BuyStatus.SUCCESS:BuyStatus.NOT_ENOUGH_GEMS;
    }

    @Override
    public String details(Item item) {
        return "you need "+neededGems+" to buy "+item.name()+".";
    }
}
