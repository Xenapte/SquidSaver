# SquidSaver

A Minecraft spigot plugin to prevent squids from dying on land. Requires at least 1.17.

Due to [MC-132473](https://bugs.mojang.com/browse/MC-132473), squids swimming near the shores usually end up on the land, where they cannot move again and eventually die to suffocation. This is particularly annoying if one bases near a river or ocean (constant squid hurt/death sounds and random ink sac items on the ground).

This plugin is a very primitive implementation for the goal of preventing them from constantly dying on the land - it just checks if all the squids are in water periodically and teleports them back in when they are not in water but have water or waterlogged blocks nearby.

## Configuration

- `squid-check-interval`: the period which the server checks for squids out of the water.

## Commands

- `/squid` or `/squid status`: show the current configuration.
- `/squid setinterval <interval>`: set the interval between checks.
- `/squid reload`: reload the configuration.