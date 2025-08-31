import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Stream1 {

	public static void main(String[] args) throws IOException {
		FileOutputStream fos = new FileOutputStream("IO/temp/hello.dat");
		fos.write(65);
		fos.write(66);
		fos.write(67);

		fos.close();

		FileInputStream fis = new FileInputStream("IO/temp/hello.dat");
		System.out.println(fis.read());
		System.out.println(fis.read());
		System.out.println(fis.read());
		System.out.println(fis.read());
		fis.close();
	}

}
