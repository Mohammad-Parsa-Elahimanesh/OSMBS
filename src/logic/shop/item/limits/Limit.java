package logic.shop.item.limits;

import logic.User;
import logic.shop.Commodity;
import logic.shop.item.Item;

import java.util.ArrayList;
import java.util.List;

public abstract class Limit {
    public abstract BuyStatus canBuy(User buyer, List<Commodity> sells, Commodity wanted);
    public abstract String details(Item item);
    List<Commodity> filterLastSeconds(List<Commodity> sells, Long seconds) {
        List<Commodity> filtered = new ArrayList<>();
        for(Commodity commodity: sells)
            if(now()-commodity.getSoldTime() < seconds*1000)
                filtered.add(commodity);
        return filtered;
    }
    List<Commodity> filterUser(List<Commodity> sells, User buyer) {
        List<Commodity> filtered = new ArrayList<>();
        for(Commodity commodity: sells)
            if(buyer.equals(commodity.getBuyer()))
                filtered.add(commodity);
        return filtered;
    }

    Long now() {return System.currentTimeMillis();}
}
