package org.parabot.scriptwriter.aFisher.core;

import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;
import org.parabot.scriptwriter.aFisher.strategies.Banking;
import org.parabot.scriptwriter.aFisher.strategies.Fish;

import java.util.ArrayList;

@ScriptManifest(author = "Atex",
        category = Category.FISHING,
        description = "FISHING MONEY",
        name = "aFisher", servers = { "Revival" },
        version = 0.1)
public class Core extends Script {
    ArrayList<Strategy> strategies = new ArrayList<>();
    @Override
    public boolean onExecute() {
        strategies.add(new Fish());
        strategies.add(new Banking());

        provide(strategies);
        return true;
    }
}
