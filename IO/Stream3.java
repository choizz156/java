import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Stream3 {

	public static void main(String[] args) throws IOException {
		FileOutputStream fos = new FileOutputStream("IO/temp/hello.dat");
		byte[] input = {65, 55, 57};
		fos.write(input);
		fos.close();

		FileInputStream fis = new FileInputStream("IO/temp/hello.dat");
		byte[] buffer = new byte[10];
		int readCount = fis.read(buffer, 0, 10);
		System.out.println("readCount = " + readCount);;
		System.out.println(Arrays.toString(buffer));
		fis.close();
	}

}
