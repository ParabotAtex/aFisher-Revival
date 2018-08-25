package org.parabot.scriptwriter.aFisher.strategies;

import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.*;
import org.rev317.min.api.wrappers.SceneObject;
import org.rev317.min.api.wrappers.Tile;

public class Banking implements Strategy {
    private int bankId = 28861;
    private int bankInterfaceId = 5292;
    private int fishingId = 1535;

    @Override
    public boolean activate() {
        return Inventory.isFull();
    }

    @Override
    public void execute() {

        if(Interfaces.getOpenInterfaceId() == bankInterfaceId) {
            Time.sleep(1000);
            Bank.depositAllExcept(3160);
            Time.sleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return !Inventory.isFull();
                }
            }, 3000);
        } else {
            SceneObject[] banks = SceneObjects.getNearest(bankId);
            SceneObject bank = null;
            for(SceneObject b : banks) {
                if(b.getLocation().equals(getBankTile())) {
                    bank = b;
                }
            }
            if(bank == null) {
                bank = SceneObjects.getClosest(bankId);
            }
            bank.interact(SceneObjects.Option.FIRST);
            Time.sleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return Interfaces.getOpenInterfaceId() == bankInterfaceId;
                }
            },3000);
        }
        Time.sleep(500);
    }


    private Tile getBankTile() {
        return new Tile(Npcs.getClosest(fishingId).getLocation().getX(), Npcs.getClosest(fishingId).getLocation().getY() + 3);
    }
}
