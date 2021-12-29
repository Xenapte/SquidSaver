# SquidSaver

A Minecraft spigot plugin to prevent squids (and glow squids after 1.17) from dying on land all the time (by teleporting squids near the water back). Supports Minecraft version **1.5 ~ 1.18**.

Due to [MC-132473](https://bugs.mojang.com/browse/MC-132473), squids swimming near the shores usually end up on the land, where they cannot move again and eventually die to suffocation. This is particularly annoying if one bases near a river or ocean (constant squid hurt/death sounds and random ink sac items on the ground).

This plugin is a very primitive implementation for the goal of preventing them from constantly dying on the land - it just checks if all the squids are in water periodically and teleports them back in when they are not in water but have water or waterlogged blocks nearby.

## Configuration

- `squid-check-interval`: the time interval which the server checks for squids out of the water.

## Commands

- `/squid` or `/squid status`: show the current configuration.
- `/squid setinterval <interval>`: set the interval between checks.
- `/squid reload`: reload the configuration.

## Notes

- Only squids (and glow squids) near water (more precisely, squids with a water or waterlogged block 1 block below them and in the planar radius of 1 block) will be teleported back.
