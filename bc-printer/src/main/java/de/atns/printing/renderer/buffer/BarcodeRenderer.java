package de.atns.printing.renderer.buffer;

import de.atns.printing.Converter;
import de.atns.printing.document.BarcodeElement;
import de.atns.printing.renderer.Renderer;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BarcodeRenderer implements Renderer<BarcodeElement> {

    private final DocumentRenderer dr;

    public BarcodeRenderer(final DocumentRenderer dr) {
        this.dr = dr;
    }

    @Override public void render(final BarcodeElement element) throws IOException {
        final AbstractBarcodeBean bean;
        String dataPrefix = "";

        switch (element.getType()) {
            case CODE39:
                bean = new Code39Bean();
                break;
            case CODE128:
                bean = new Code128Bean();
                break;
            case EAN13:
                bean = new EAN13Bean();
                break;
            case EAN128:
                bean = new Code128Bean();
                final StringBuilder bi = new StringBuilder();
                bi.append((char) 0xF1);
                dataPrefix = bi.toString();
//            if (element.getBarcode().matches("[0-9]*")) {
//                dataPrefix = ">;>8";
//            } else {
//                dataPrefix = ">:>8";
//            }
//            bean = new UPCEBean();
                break;
            default:
                throw new IllegalArgumentException("unable to render Barcode-Type " + element.getType());
        }

        // bean.setModuleWidth(
        // Converter.convertMMToDots(3,dr.getResolution())/100.0);
        bean.setModuleWidth(element.getModulo());

        bean.setBarHeight(element.getHeight() * 0.8);
        // bean.setBarHeight(
        // Converter.convertMMToDots(element.getHeight(),dr.getResolution())*0.254/4);
        bean.doQuietZone(false);

        if (!element.isInterpretationLine()) {
            bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
        }

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            final BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/png", this.dr.getResolution(),
                    BufferedImage.TYPE_BYTE_BINARY, false, 0);
            bean.generateBarcode(canvas, dataPrefix + element.getBarcode());
            canvas.finish();
        } finally {
            out.close();
        }
        final Image i = new ImageIcon(out.toByteArray()).getImage();
        this.dr.getGraphics().drawImage(i, Converter.convertMMToDots(element.getX(), this.dr.getResolution()),
                Converter.convertMMToDots(element.getY(), this.dr.getResolution()), null);
    }
}
