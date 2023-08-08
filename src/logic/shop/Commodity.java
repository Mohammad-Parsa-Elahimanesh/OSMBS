package logic.shop;

import logic.User;
import logic.shop.item.Item;
import logic.shop.item.limits.BuyStatus;
import logic.shop.item.limits.Coins;
import logic.shop.item.limits.Gems;
import logic.shop.item.limits.Limit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Commodity {
    public static final List<Commodity> sells = new ArrayList<>();
    public final Item[] items;

    private User buyer;
    private Long soldTime;
    Commodity(Item[] items) {
        this.items = items;
    }
    public BuyStatus sell(User buyer) {
        BuyStatus status = BuyStatus.SUCCESS;
        for(Item item : items)
            for(Limit limit: item.limits)
            {
                status = limit.canBuy(buyer, sells, this);
                if(status != BuyStatus.SUCCESS)
                    return status;
            }
        this.buyer = buyer;
        soldTime = System.currentTimeMillis();
        buyer.onlineCoins -= Coins.getSumNeededCoins(this);
        buyer.gems -= Gems.getSumNeededGems(this);
        buyer.items.addAll(Arrays.asList(items));
        sells.add(this);
        return status;
    }
    public User getBuyer() {
        return buyer;
    }
    public Long getSoldTime() {
        return soldTime;
    }
    public String imageName() {return items[0].imageName();}
    public String details() {
        StringBuilder rs = new StringBuilder("it contains ");
        for(int i = 0; i < items.length; i++) {
            rs.append(items[i].name());
            if(i+1 != items.length)
                rs.append(", ");
            else
                rs.append(".    ");
        }
        rs.append("it costs ").append(Coins.getSumNeededCoins(this)).append(" coins and ")
                .append(Gems.getSumNeededGems(this)).append(" gems.    ");
        for(Item item : items)
            for (Limit limit: item.limits)
                rs.append(limit.details(item)).append("   ");
        return rs.toString();
    }
}
