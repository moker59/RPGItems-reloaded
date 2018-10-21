/*
 *  This file is part of RPG Items.
 *
 *  RPG Items is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  RPG Items is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with RPG Items.  If not, see <http://www.gnu.org/licenses/>.
 */
package think.rpgitems.power.impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import think.rpgitems.I18n;
import think.rpgitems.RPGItems;
import think.rpgitems.power.PowerMeta;
import think.rpgitems.power.PowerResult;
import think.rpgitems.power.PowerRightClick;
import think.rpgitems.power.Property;

/**
 * Power food.
 * <p>
 * Restore {@link #foodpoints food points} when eaten.
 * </p>
 */
@SuppressWarnings("WeakerAccess")
@PowerMeta(immutableTrigger = true)
public class PowerFood extends BasePower implements PowerRightClick {
    /**
     * Food Points
     */
    @Property(order = 0, required = true)
    public int foodpoints;

    @Override
    public PowerResult<Void> rightClick(final Player player, ItemStack stack, PlayerInteractEvent event) {
        ItemStack item = player.getInventory().getItemInMainHand();
        int count = item.getAmount() - 1;
        if (count == 0) {
            int newFoodPoint = player.getFoodLevel() + foodpoints;
            if (newFoodPoint > 20) newFoodPoint = 20;
            player.setFoodLevel(newFoodPoint);
            Bukkit.getScheduler().scheduleSyncDelayedTask(RPGItems.plugin, () -> player.getInventory().setItemInMainHand(new ItemStack(Material.AIR)), 1L);
        } else {
            player.setFoodLevel(player.getFoodLevel() + foodpoints);
            item.setAmount(count);
        }
        return PowerResult.ok();
    }

    @Override
    public String getName() {
        return "food";
    }

    @Override
    public String displayText() {
        return I18n.format("power.food", foodpoints);
    }
}
