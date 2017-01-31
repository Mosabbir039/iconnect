package network;

import java.awt.image.BufferedImage;

public interface CaptureListener {
	void onCaptureRecieve(BufferedImage snap);
}
