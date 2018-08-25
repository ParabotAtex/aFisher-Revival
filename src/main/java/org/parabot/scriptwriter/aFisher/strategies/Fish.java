package org.parabot.scriptwriter.aFisher.strategies;

import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.GroundItems;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Npcs;
import org.rev317.min.api.wrappers.GroundItem;
import org.rev317.min.api.wrappers.Npc;
import org.rev317.min.api.wrappers.Tile;

import static org.rev317.min.api.methods.Players.getMyPlayer;
import static org.rev317.min.api.methods.Walking.walkTo;

public class Fish implements Strategy {
    private Tile fishingTile = new Tile(3209,3018);
    private int fishingId = 1535;
    private int[] pickup = {13648,13649,13650,13651,6199,4703};
    @Override
    public boolean activate() {
        return !Inventory.isFull();
    }

    @Override
    public void execute() {
        try {
            fishingTile = getFishingTile();
//        for (int aPickup : pickup) {
//            if (GroundItems.getClosest(aPickup) != null) {
//                GroundItems.getClosest(aPickup).take();
//            }
//        }

            for (GroundItem l : GroundItems.getGroundItems()) {
                if (l.getLocation().equals(fishingTile)) {
                    l.take();
                    Time.sleep(350);
                }
            }

            if (!getMyPlayer().getLocation().equals(fishingTile)) {
                walkTo(fishingTile);
                Time.sleep(new SleepCondition() {
                    @Override
                    public boolean isValid() {
                        return getMyPlayer().getLocation().equals(fishingTile);
                    }
                }, 3000);
            } else if (getMyPlayer().getLocation().equals(fishingTile)) {
                Npc fishing = Npcs.getClosest(fishingId);
                fishing.interact(Npcs.Option.CAGE);
                Time.sleep(150, 200);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private Tile getFishingTile() {
        return new Tile(Npcs.getClosest(fishingId).getLocation().getX(), Npcs.getClosest(fishingId).getLocation().getY() +1);
    }
}