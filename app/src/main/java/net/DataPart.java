package net;

public class DataPart {
    private byte[] content;
    private String fileName;
    private String type;

    public DataPart(String name, byte[] data) {
        this.fileName = name;
        this.content = data;
    }

    String getFileName() {
        return this.fileName;
    }

    byte[] getContent() {
        return this.content;
    }

    String getType() {
        return this.type;
    }
}
