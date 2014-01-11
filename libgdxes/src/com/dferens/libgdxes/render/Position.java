package com.dferens.libgdxes.render;

public enum Position {
    TOP_LEFT     ((byte)-1, (byte)1),
    TOP_RIGHT    ((byte)1, (byte)1),
    TOP_CENTER   ((byte)0, (byte)1),
    BOTTOM_LEFT  ((byte)-1, (byte)-1),
    BOTTOM_RIGHT ((byte)1, (byte)-1),
    BOTTOM_CENTER((byte)0, (byte)-1),
    CENTER_LEFT  ((byte)-1, (byte)0),
    CENTER_RIGHT ((byte)1, (byte)0),
    CENTER       ((byte)0, (byte)0);

    private byte dx;
    private byte dy;

    public byte getDx() { return this.dx; }
    public byte getDy() { return this.dy; }

    private Position(byte dx, byte dy) {
        this.dx = dx;
        this.dy = dy;
    }
}
