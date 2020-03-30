package application.blur;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class controller {

	@ResponseBody
	@RequestMapping("/blur")
	public ResponseEntity BlurImage()  throws IOException {

		// read the client's image
				URL url = new URL("https://images.wallpaperscraft.com/image/sea_ice_glacier_148212_1024x600.jpg");
				
				BufferedImage originalImgage = ImageIO.read(url);

				final HttpHeaders headers = new HttpHeaders();
				InputStream inputStream;
				try {
					inputStream = blur(originalImgage);

					if ( inputStream != null){
						System.out.println("stream not null ##### ");
						InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
						headers.setContentType(MediaType.IMAGE_PNG);


						return new ResponseEntity(inputStreamResource, headers, HttpStatus.OK);}
					else {
						System.out.println("stream is null");
						return new ResponseEntity(null, headers, HttpStatus.SERVICE_UNAVAILABLE);					}


				} catch (Exception exception) {
					headers.setContentType(MediaType.TEXT_HTML);
					System.out.println("BlurImage Catch");
					return new ResponseEntity(exception.getMessage(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
				}
	}

	
	
	
	public InputStream blur(BufferedImage originalImgage) {

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			int size = 20;
			float weight = 1.0f / (size * size);

			float[] array = new float[size * size];

			for (int i = 0; i < array.length; i++) {
				array[i] = weight;
			}


			Kernel kernel = new Kernel(size, size, array);
			ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null);
			BufferedImage blurred = op.filter(originalImgage, null);
			ImageIO.write(blurred, "png", os);
			return new ByteArrayInputStream(os.toByteArray());
		
		} catch (Exception e) {
			System.out.println("CATCH!!");
			e.printStackTrace();
			System.out.println(e);
			return null;
		}
	}
	
}
