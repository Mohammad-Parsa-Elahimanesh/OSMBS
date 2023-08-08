package logic.shop.item.limits;

import logic.User;
import logic.shop.Commodity;
import logic.shop.item.Item;

import java.util.List;

public class AllCount extends Limit{
    final Long seconds;
    final int count;
    public AllCount(Long seconds, int count) {
        this.seconds = seconds;
        this.count = count;
    }
    @Override
    public BuyStatus canBuy(User buyer, List<Commodity> sells, Commodity wanted) {
        return filterLastSeconds(sells, seconds).size() < count?BuyStatus.SUCCESS:BuyStatus.ALL_PEOPLE_LIMIT;
    }
    public String details(Item item) {
        return "we can sale only "+count+" of "+item.name()+"s per "+seconds+" continuous seconds";
    }
}
