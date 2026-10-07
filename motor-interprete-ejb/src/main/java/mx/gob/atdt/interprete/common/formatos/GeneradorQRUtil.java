package mx.gob.atdt.interprete.common.formatos;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class GeneradorQRUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeneradorQRUtil.class);
	private static final String TIPO_IMAGEN_QR = "png";
	private static final int TAMANIO_IMAGEN_QR = 200;

	private GeneradorQRUtil() {
		/* Constructor privado para que no se pueda unstanciar la clase */
	}

	/**
	 * Método que realiza la generacion del código QR con el contenido que se le
	 * pase en el parametro contenidoQR. Se utiliza la dependencia com.google.zxing
	 * que proporciona los métodos requeridos para la generación del código QR
	 * 
	 * @param idPermiso
	 * @param contenidoQR
	 * @return
	 */
	public static byte[] generarCodigoQR(String contenidoQR) {
		ByteArrayOutputStream output = null;
		try {
			QRCodeWriter qrcode = new QRCodeWriter();
			BitMatrix matrix = qrcode.encode(contenidoQR, BarcodeFormat.QR_CODE, TAMANIO_IMAGEN_QR, TAMANIO_IMAGEN_QR);

			int matrixWidth = matrix.getWidth();
			BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_ARGB);
			int grayValue;
			for (int y = 0; y < TAMANIO_IMAGEN_QR; y++) {
				for (int x = 0; x < TAMANIO_IMAGEN_QR; x++) {
					grayValue = (matrix.get(x, y) ? 0 : 1) & 0xff;
					if (grayValue == 0)
						image.setRGB(x, y, 0xff000000);
					else
						image.setRGB(x, y, 0x00ffffff);
				}
			}
			output = new ByteArrayOutputStream();
			ImageIO.write(image, TIPO_IMAGEN_QR, output);
			return output.toByteArray();
		} catch (WriterException ex) {
			LOGGER.error("Ocurrio un error al intentar generar el QR", ex);
		} catch (IOException ex) {
			LOGGER.error("Ocurrio un error al intentar escribir la imagen", ex);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (Exception e) {
					LOGGER.warn("No se pudo cerrar el ByteArrayOutputStream al crear la imagen del QR", e);
				}
			}
		}
		return null;
	}

}
