package logic.shop.item.limits;

import logic.User;
import logic.shop.Commodity;
import logic.shop.item.Item;

import java.util.List;

public class Level extends Limit{
    final int neededLevel;
    public Level(int neededLevel) {
        this.neededLevel = neededLevel;
    }

    @Override
    public BuyStatus canBuy(User buyer, List<Commodity> sells, Commodity wanted) {
        return buyer.getLevel() >= neededLevel ?BuyStatus.SUCCESS:BuyStatus.NOT_ENOUGH_LEVEL;
    }

    @Override
    public String details(Item item) {
        return "you must be at least in level " + neededLevel+" to buy " + item.name()+".";
    }
}
