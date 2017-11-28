package be.julien.seed.physics;

import org.jetbrains.annotations.NotNull;

public enum Mask {

    Player(Val.energy, Val.life, Val.wall),
    Bullet(Val.life, Val.wall),
    Life(Val.life, Val.life, Val.wall, Val.energy, Val.player),
    Wall(Val.wall),
    Energy(Val.energy),
    Sensor(Val.sensor, Val.life, Val.wall, Val.energy);

    byte flag;
    byte colliders;

    Mask(byte flag, byte... others) {
        this.flag = flag;
        for (byte other : others) {
            colliders |= other;
        }
    }

    public boolean collidesWith(@NotNull Mask other) {
        return (colliders & other.flag) == other.flag;
    }

}

class Val {
    static final byte life = 0x1;
    static final byte wall = 0x2;
    static final byte energy = 0x4;
    static final byte sensor = 0x8;
    static final byte player = 0x10;
    static final byte bullet = 0x20;
}