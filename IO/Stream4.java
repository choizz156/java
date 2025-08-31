import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;


public class Stream4 {

	public static void main(String[] args) throws IOException {
		FileOutputStream fos = new FileOutputStream("IO/temp/hello.dat");
		byte[] input = {65, 55, 57};
		fos.write(input);
		fos.close();

		FileInputStream fis = new FileInputStream("IO/temp/hello.dat");
		byte[] readBytes = fis.readAllBytes();
		System.out.println("readBytes = " + Arrays.toString(readBytes));
		fis.close();
	}

}
