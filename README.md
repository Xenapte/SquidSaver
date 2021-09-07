# SquidSaver

A Minecraft spigot plugin to prevent squids from dying on land. 

Due to [MC-132473](https://bugs.mojang.com/browse/MC-132473), squids now constantly swim and get on the land, where they cannot move again and eventually die, making it very annoying if one bases near a river or ocean (constant squid hurt/death sounds and random ink sac items).

This plugin is a very primitive implementation - it just checks if the squids are in water periodically and teleports them back when they are not in water but have water blocks nearby.