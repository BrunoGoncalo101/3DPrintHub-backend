package pt.utp.estg.pp.printhub.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class StlParser {
    public record StlAnalysisResult(
        double volumeCm3,
        float widthX,
        float depthY,
        float heightZ
    ) {}

    public static StlAnalysisResult parseBinaryStl(InputStream inputStream) throws IOException {
        byte[] header = new byte[80];
        if (inputStream.read(header) < 80) {
            throw new IllegalArgumentException("Ficheiro STL inválido ou corrompido");
        }

        byte[] countBytes = new byte[4];
        if (inputStream.read(countBytes) < 4) {
            throw new IllegalArgumentException("Ficheiro STL sem contagem de triángulos");
        }

        int triangleCount = ByteBuffer.wrap(countBytes).order(ByteOrder.LITTLE_ENDIAN).getInt();

        float minX = Float.MAX_VALUE, maxX = -Float.MAX_VALUE;
        float minY = Float.MAX_VALUE, maxY = -Float.MAX_VALUE;
        float minZ = Float.MAX_VALUE, maxZ = -Float.MAX_VALUE;

        double totalSignedVolume = 0.0;
        byte[] triangleBuffer = new byte[50];

        for (int i = 0; i < triangleCount; i++) {
            int bytesRead = inputStream.read(triangleBuffer);
            if (bytesRead < 50) {
                break;
            }

        ByteBuffer bb = ByteBuffer.wrap(triangleBuffer).order(ByteOrder.LITTLE_ENDIAN);

        bb.getFloat(); bb.getFloat(); bb.getFloat();

        float x1 = bb.getFloat();
        float y1 = bb.getFloat();
        float z1 = bb.getFloat();

        float x2 = bb.getFloat();
        float y2 = bb.getFloat();
        float z2 = bb.getFloat();

        float x3 = bb.getFloat();
        float y3 = bb.getFloat();
        float z3 = bb.getFloat();

        minX = Math.min(minX, Math.min(x1, Math.min(x2, x3)));
        maxX = Math.max(maxX, Math.max(x1, Math.max(x2, x3)));

        minY = Math.min(minY, Math.min(y1, Math.min(y2, y3)));
        maxY = Math.max(maxY, Math.max(y1, Math.max(y2, y3)));

        minZ = Math.min(minZ, Math.min(z1, Math.min(z2, z3)));
        maxZ = Math.max(maxZ, Math.max(z1, Math.max(z2, z3)));

        double signedVolume = (-x3 * y2 * z1 + x2 * y3 * z1 + x3 * y1 * z2
                - x1 * y3 * z2 - x2 * y1 * z3 + x1 * y2 * z3) / 6.0;

        totalSignedVolume += signedVolume;
    }

    double volumeCm3 = Math.abs(totalSignedVolume) /1000.0;

    float widthX = (maxX >= minX) ? (maxX - minX) : 0f;
    float depthY = (maxY >= minY) ? (maxY - minY) : 0f;
    float heightZ = (maxZ >= minZ) ? (maxZ - minZ) : 0f;

    return new StlAnalysisResult(volumeCm3, widthX, depthY, heightZ);
}
}

