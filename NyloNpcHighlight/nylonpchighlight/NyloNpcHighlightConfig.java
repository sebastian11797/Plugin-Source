package net.runelite.client.plugins.nylonpchighlight;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("NyloNpcHighlight")
public interface NyloNpcHighlightConfig extends Config {
    @ConfigItem(
            keyName = "HighlightMelee",
            name = "Highlight Nylocas Ischyros",
            description = "Highlights all of the melee nylocas",
            hidden = true
    )
    default boolean HighlightMelee() { return false; }
    @ConfigItem(
            keyName = "HighlightMelee",
            name = "Highlight Nylocas Ischyros",
            description = "Highlights all of the melee nylocas",
            hidden = true
    )
    void setHighlightMelee(boolean flag);
    @ConfigItem(
            keyName = "HighlightRange",
            name = "Highlight Nylocas Toxobolos",
            description = "Highlights all of the range nylocas",
            hidden = true
    )
    default boolean HighlightRange() { return false; }
    @ConfigItem(
            keyName = "HighlightRange",
            name = "Highlight Nylocas Toxobolos",
            description = "Highlights all of the range nylocas",
            hidden = true
    )
    void setHighlightRange(boolean flag);
    @ConfigItem(
            keyName = "HighlightMage",
            name = "Highlight Nylocas Hagios",
            description = "Highlights all of the mage nylocas",
            hidden = true
    )
    default boolean HighlightMage() { return false; }
    @ConfigItem(
            keyName = "HighlightMage",
            name = "Highlight Nylocas Hagios",
            description = "Highlights all of the mage nylocas",
            hidden = true
    )
    void setHighlightMage(boolean flag);
}
