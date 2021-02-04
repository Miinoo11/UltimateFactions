package de.miinoo.factions.configuration.configurations;

import de.miinoo.factions.core.configuration.Configuration;

import java.io.File;
import java.util.List;

public class ScoreboardConfiguration extends Configuration {

    public ScoreboardConfiguration(File file) {
        super(file);
    }

    public String getHeader() { return configuration.getString("Scoreboard.Header");}

    public List<String> getLines() { return configuration.getStringList("Scoreboard.Lines"); }

}
