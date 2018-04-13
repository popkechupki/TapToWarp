package net.comorevi.nukkit.taptowarp;

import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

import java.io.File;
import java.util.LinkedHashMap;

public class TapToWarp extends PluginBase implements Listener {

	public int KEY_BLOCK;
	public Vector3 warpPoint;

	@Override
	public void onEnable() {
		if (!this.getDataFolder().exists()) {
			this.getDataFolder().exists();
		}
		Config config = new Config(
				new File(this.getDataFolder(), "config.yml"),
				Config.YAML,
				new LinkedHashMap<String, Object>() {
					{
						put("keyBlockID", Block.SEA_LANTERN);
						put("locationX", getServer().getDefaultLevel().getSafeSpawn().x + 0.5);
						put("locationY", getServer().getDefaultLevel().getSafeSpawn().y);
						put("locationZ", getServer().getDefaultLevel().getSafeSpawn().z + 0.5);
					}
				});
		config.save();

		this.KEY_BLOCK = config.getInt("keyBlockID");
		this.warpPoint = new Vector3(config.getInt("locationX"), config.getInt("locationY"), config.getInt("locationZ"));

		this.getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getBlock().getId() == this.KEY_BLOCK) {
			event.getPlayer().teleport(this.warpPoint);
		}
	}

}
