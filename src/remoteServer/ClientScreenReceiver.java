package remoteServer;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


class ClientScreenReceiver extends Thread {
	
	private InputStream is = null;
	private JPanel clientPanel = null;
	private boolean continueLoop = true;
	public ClientScreenReceiver(InputStream is, JPanel cPanel) {
		this.is = is;
		clientPanel = cPanel;
		start();
	}
	
	@Override
	public void run() {
		
		BufferedImage image = null;

			while(continueLoop) {
				
				try {

					byte[] sizeInByte = new byte[64];
					is.read(sizeInByte);
					int length = ByteBuffer.wrap(sizeInByte).asIntBuffer().get();
					
					try {
						byte[] img = new byte[length];
						is.readNBytes(img, 0, img.length);
						
						image = ImageIO.read(new ByteArrayInputStream(img));

					} catch (Exception e) {
						e.printStackTrace();
					}
					
					if( image != null) {
						
					   image = (BufferedImage) image.getScaledInstance(clientPanel.getWidth(), clientPanel.getHeight(), Image.SCALE_FAST);
					   Graphics graphics = clientPanel.getGraphics();
					   graphics.drawImage(image, 0, 0, clientPanel.getWidth(), clientPanel.getHeight(), clientPanel);
					   System.out.println("Receiving image");
					   Thread.sleep(30);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
//				byte[] bytes = new byte[1024*1024];
//				int count = 0;
//				do {
//					count += clientOIS.read(bytes, count, bytes.length-count);
//				} 
//				while (!(count>4) && bytes[count - 2]==(byte)-1 && bytes[count-1]==(byte)-39);
////				ImageIcon imageIcon = (ImageIcon) clientOIS.readObject();
////				Image imageIcon = ImageIO.read(new ByteArrayInputStream(bytes));
//				image = ImageIO.read(new ByteArrayInputStream(bytes));
//				System.out.println("New Image received");
////				Image image = imageIcon.getScaledInstance(clientPanel.getWidth(), clientPanel.getHeight(), Image.SCALE_FAST);
////				image = image.getScaledInstance();
//				image = image.getScaledInstance(clientPanel.getWidth(), clientPanel.getHeight(), Image.SCALE_FAST);
//				
//				Graphics graphics = clientPanel.getGraphics();
//				graphics.drawImage(image, 0, 0, clientPanel.getWidth(), clientPanel.getHeight(), clientPanel);
			}
		
		
	}
	
	
	
}
