package be.julien.seed.graphics;

public class Color {
    public static float WHITE = convertARGB(1f, 1f, 1f, 1f);
    public static float ALPHA40 = convertARGB(.4f, 1f, 1f, 1f);
    public static float BLACK = convertARGB(1f, 0f, 0f, 0f);
    public static float ALPHA70 = convertARGB(.70f, 1f, 1f, 1f);
    public static final short MAX = 255, A = 24, R = 16, G = 8;

    public static float convertARGB(float a, float r, float g, float b) {
        return intToFloatColor(((int)(MAX * a) << A) | ((int)(MAX * b) << R) | ((int)(MAX * g) << G) | ((int)(MAX * r)));
    }

    public static Float convertARGB(float a, float all) {
        return intToFloatColor(((int)(MAX * a) << A) | ((int)(MAX * all) << R) | ((int)(MAX * all) << G) | ((int)(MAX * all)));
    }

    public static int tmpInt;
    public static float setAlpha(float color, float alpha) {
        tmpInt = floatToIntColor(color);
        return convertARGB(alpha, (tmpInt & 0xff) / 255f, ((tmpInt >>> 8) & 0xff) / 255f, ((tmpInt >>> 16) & 0xff) / 255f);
    }
    public static float setBlue(float color, float blue) {
        tmpInt = floatToIntColor(color);
        return convertARGB((tmpInt & 0xff) / 255f, (tmpInt & 0xff) / 255f, ((tmpInt >>> 8) & 0xff) / 255f, blue);
    }

    public static int floatToIntColor (float value) {
        return Float.floatToRawIntBits(value);
    }

    public static float intToFloatColor (int value) {
        return Float.intBitsToFloat(value & 0xfeffffff);
    }

}
