package logic.shop.item.limits;

import logic.User;
import logic.shop.Commodity;
import logic.shop.item.Item;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class AvailableTime extends Limit{
    final Long startTime;
    final Long endTime;

    public AvailableTime(Long startTime, Long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public BuyStatus canBuy(User buyer, List<Commodity> sells, Commodity wanted) {
        return (startTime*1000 <= now() && now() <= endTime*1000)?BuyStatus.SUCCESS:BuyStatus.SPECIFIC_TIME_LIMIT;
    }

    @Override
    public String details(Item item) {
        return item.name()+" is just available from "+new Date(startTime*1000)+" to "+new Date(endTime*1000)+".";
    }
}
