package logic.shop;

import logic.shop.item.Item;
import logic.shop.item.potion.HealPotion;
import logic.shop.item.potion.Invisibility;
import logic.shop.item.potion.SpeedPotion;
import logic.shop.item.throwable.Axe;
import logic.shop.item.throwable.DamageBomb;
import logic.shop.item.throwable.SpecialSaber;
import logic.shop.item.throwable.SpeedBomb;

public enum Commodities {
    HEALTH,
    MAGIC,
    RUN,
    HERO,
    AXE,
    DESTROY,
    SABER_FIGHTER,
    SPEED_ROOM,
    BOMBS,
    JUST_SPEED;

    public Commodity getCommodity() {
        switch (this) {
            case HEALTH -> {
                return new Commodity(new Item[]{new HealPotion()});
            }
            case MAGIC -> {
                return new Commodity(new Item[]{new Invisibility()});
            }
            case RUN -> {
                return new Commodity(new Item[]{new SpeedPotion()});
            }
            case HERO -> {
                return new Commodity(new Item[]{new Invisibility(), new SpeedPotion()});
            }
            case AXE -> {
                return new Commodity(new Item[]{new Axe()});
            }
            case DESTROY -> {
                return new Commodity(new Item[]{new DamageBomb()});
            }
            case SABER_FIGHTER -> {
                return new Commodity(new Item[]{new SpecialSaber()});
            }
            case SPEED_ROOM -> {
                return new Commodity(new Item[]{new SpeedBomb()});
            }
            case BOMBS -> {
                return new Commodity(new Item[]{new SpeedBomb(), new DamageBomb()});
            }
            case JUST_SPEED -> {
                return new Commodity(new Item[]{new SpeedPotion(), new SpeedBomb()});
            }
            default -> {
                throw new IllegalStateException("Unknown");
            }
        }
    }

}
