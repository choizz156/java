
package me.bigfilejdbc;

import java.io.IOException;
import java.io.InputStream;

/**
 * 지정된 크기만큼의 가상 데이터를 실시간으로 생성하는 InputStream.
 * 대용량 byte[] 배열을 미리 만들 필요가 없어 메모리를 절약할 수 있다.
 */
public class CustomInputStream extends InputStream {
    private final long totalSize;
    private long bytesGenerated = 0;

    public CustomInputStream(long totalSize) {
        this.totalSize = totalSize;
    }

    @Override
    public int read() throws IOException {
        if (bytesGenerated >= totalSize) {
            return -1; // End of stream
        }
        bytesGenerated++;
        // 'A' ~ 'Z' 사이의 문자를 반복해서 반환
        return 'A' + (int)((bytesGenerated - 1) % 26);
    }

    @Override
    public int available() throws IOException {
        long remaining = totalSize - bytesGenerated;
        return remaining > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) remaining;
    }
}
