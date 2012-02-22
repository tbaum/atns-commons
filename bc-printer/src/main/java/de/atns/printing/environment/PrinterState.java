package de.atns.printing.environment;

import de.atns.printing.document.Mode;

import java.util.ArrayList;
import java.util.List;

public class PrinterState {

    private boolean paperOut = false;

    private boolean paused = false;

    private boolean bufferFull = false;

    private boolean corruptRam = false;

    private boolean lowTemperature = false;

    private boolean highTemperature = false;

    private boolean headUp = false;

    private boolean ribbonOut = false;

    private boolean thermoTransferMode = false;

    private int jobs = 0;

    private boolean ribbonIn = false;

    public int getJobs() {
        return this.jobs;
    }

    public void setJobs(final int jobs) {
        this.jobs = jobs;
    }

    public boolean isBufferFull() {
        return this.bufferFull;
    }

    public void setBufferFull(final boolean bufferFull) {
        this.bufferFull = bufferFull;
    }

    public boolean isCorruptRam() {
        return this.corruptRam;
    }

    public void setCorruptRam(final boolean corruptRam) {
        this.corruptRam = corruptRam;
    }

    public boolean isHeadUp() {
        return this.headUp;
    }

    public void setHeadUp(final boolean headUp) {
        this.headUp = headUp;
    }

    public boolean isHighTemperature() {
        return this.highTemperature;
    }

    public void setHighTemperature(final boolean highTemperature) {
        this.highTemperature = highTemperature;
    }

    public boolean isLowTemperature() {
        return this.lowTemperature;
    }

    public void setLowTemperature(final boolean lowTemperature) {
        this.lowTemperature = lowTemperature;
    }

    public boolean isPaperOut() {
        return this.paperOut;
    }

    public void setPaperOut(final boolean paperOut) {
        this.paperOut = paperOut;
    }

    public boolean isPaused() {
        return this.paused;
    }

    public void setPaused(final boolean paused) {
        this.paused = paused;
    }

    public boolean isRibbonOut() {
        return this.ribbonOut;
    }

    public void setRibbonOut(final boolean ribbonOut) {
        this.ribbonOut = ribbonOut;
    }

    public boolean isThermoTransferMode() {
        return this.thermoTransferMode;
    }

    public void setThermoTransferMode(final boolean thermoTransferMode) {
        this.thermoTransferMode = thermoTransferMode;
    }

    public void setRibbonIn(final boolean ribbonIn) {
        this.ribbonIn = ribbonIn;
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Paper out  : ").append(this.paperOut).append("\n");
        sb.append("Paused     : ").append(this.paused).append("\n");
        sb.append("Head up    : ").append(this.headUp).append("\n");
        sb.append("Ribbon out : ").append(this.ribbonOut).append("\n");
        sb.append("Ribbon in  : ").append(this.ribbonIn).append("\n");
        sb.append("Buffer full: ").append(this.bufferFull).append("\n");
        sb.append("Corrupt RAM: ").append(this.corruptRam).append("\n");
        sb.append("Temp low   : ").append(this.lowTemperature).append("\n");
        sb.append("Temp heigh : ").append(this.highTemperature).append("\n");
        sb.append("Jobs : ").append(this.jobs).append("\n");
        return sb.toString();
    }

    public List<String> getErrorMessages() {
        final ArrayList<String> result = new ArrayList<String>();
        if (this.paperOut)
            result.add("Papier-Ende");
        if (this.paused)
            result.add("Pause-Status");
        if (this.headUp)
            result.add("Deckel auf");
        if (this.ribbonOut)
            result.add("Farbband fehlt");
        if (this.ribbonIn)
            result.add("Farbband eingelegt");
        if (this.bufferFull)
            result.add("Druckerpuffer voll");
        if (this.corruptRam)
            result.add("RAM-Fehler");
        if (this.lowTemperature)
            result.add("Temperatur zu niedrig");
        if (this.highTemperature)
            result.add("Temperatur zu hoch");
        return result;
    }

    public boolean isPrintableState(final Mode mode) {
        final boolean modeOk = ((mode.equals(Mode.TT) && this.thermoTransferMode) || (mode.equals(Mode.TD) && !this.thermoTransferMode));
        return modeOk && statusOk();
    }

    public boolean statusOk() {
        return (!this.paperOut && !this.paused && !this.bufferFull && !this.corruptRam && !this.lowTemperature
                && !this.highTemperature && !this.headUp && !this.ribbonOut && !this.ribbonIn);
    }

    public boolean isPrinting() {
        return (this.jobs > 0);
    }

    public boolean isRecoverable() {
        return (!this.paperOut &&
                // !paused &&
                !this.bufferFull && !this.corruptRam && !this.lowTemperature && !this.highTemperature && !this.headUp);
    }

    public void setBufferFull(final String bufferFull) {
        this.bufferFull = "1".equals(bufferFull);
    }

    public void setCorruptRam(final String corruptRam) {
        this.corruptRam = "1".equals(corruptRam);
    }

    public void setHeadUp(final String headUp) {
        this.headUp = "1".equals(headUp);
    }

    public void setHighTemperature(final String highTemperature) {
        this.highTemperature = "1".equals(highTemperature);
    }

    public void setLowTemperature(final String lowTemperature) {
        this.lowTemperature = "1".equals(lowTemperature);
    }

    public void setPaperOut(final String paperOut) {
        this.paperOut = "1".equals(paperOut);
    }

    public void setPaused(final String paused) {
        this.paused = "1".equals(paused);
    }

    public void setRibbonIn(final String ribbonIn) {
        this.ribbonIn = "1".equals(ribbonIn);
    }

    public void setRibbonOut(final String ribbonOut) {
        this.ribbonOut = "1".equals(ribbonOut);
    }

    public void setThermoTransferMode(final String string) {
        this.thermoTransferMode = "1".equals(string);
    }
}
