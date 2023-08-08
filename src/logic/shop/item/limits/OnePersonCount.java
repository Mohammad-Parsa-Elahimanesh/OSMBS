package logic.shop.item.limits;

import logic.User;
import logic.shop.Commodity;
import logic.shop.item.Item;

import java.util.List;

public class OnePersonCount extends Limit{
    final Long seconds;
    final int count;
    public OnePersonCount(Long seconds, int count) {
        this.seconds = seconds;
        this.count = count;
    }
    @Override
    public BuyStatus canBuy(User buyer, List<Commodity> sells, Commodity wanted) {
        return filterUser(filterLastSeconds(sells, seconds), buyer).size() < count?BuyStatus.SUCCESS:BuyStatus.ONE_PERSON_LIMIT;
    }

    @Override
    public String details(Item item) {
        return "we can sale only "+count+" of "+item.name()+"s per "+seconds+" continuous seconds to one person";
    }
}
