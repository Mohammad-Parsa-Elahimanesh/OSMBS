package logic.shop.item.limits;

import logic.User;
import logic.shop.Commodity;
import logic.shop.item.Item;

import java.util.List;

public class Coins extends Limit{
    public final int neededCoins;
    public static int getSumNeededCoins(Commodity commodity) {
        int sumNeededCoins = 0;
        for(Item item: commodity.items)
            sumNeededCoins += item.neededCoins();
        return sumNeededCoins*(11-commodity.items.length)/10;
    }
    public Coins(int neededCoins) {
        this.neededCoins = neededCoins;
    }

    @Override
    public BuyStatus canBuy(User buyer, List<Commodity> sells, Commodity wanted) {
        return getSumNeededCoins(wanted) <= buyer.onlineCoins?BuyStatus.SUCCESS:BuyStatus.NOT_ENOUGH_COINS;
    }

    @Override
    public String details(Item item) {
        return "you need "+neededCoins+" coins to buy "+item.name()+" alone, you get 10*(pack size - 1)% off in buying packs";
    }
}
