package net.thevpc.tson.impl.format;

import net.thevpc.tson.*;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.util.AppendableWriter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class TsonWriterImpl implements TsonWriter {
    private TsonSerializer marshaller;
    private TsonFormatBuilder formatBuilder = Tson.format();
    private TsonFormat format = formatBuilder.build();

    public TsonWriterImpl(TsonSerializer marshaller) {
        this.marshaller = marshaller;
    }

    @Override
    public TsonWriter setOptionCompact(boolean configValue) {
        formatBuilder.setCompact(configValue);
        format=formatBuilder.build();
        return this;
    }

    @Override
    public TsonWriter setOption(String configName, Object configValue) {
        formatBuilder.setOption(configName, configValue);
        format=formatBuilder.build();
        return this;
    }

    /////////////////////////////////////

    @Override
    public void write(Appendable sb, Object any) throws IOException {
        try (Writer writer = AppendableWriter.of(sb)) {
            write(writer, any);
        }
    }

    @Override
    public void write(Path file, Object any) throws IOException {
        try (Writer writer = Files.newBufferedWriter(file)) {
            write(writer, any);
        }
    }

    @Override
    public void write(File file, Object any) throws IOException {
        try (Writer writer = new FileWriter(file)) {
            write(writer, any);
        }
    }

    @Override
    public void write(OutputStream stream, Object any) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(stream);
        write(writer, any);
        writer.flush();
    }

    @Override
    public void write(OutputStream stream, String encoding, Object any) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(stream, encoding);
        write(writer, any);
        writer.flush();
    }

    @Override
    public void write(Writer writer, Object any) throws IOException {
        TsonElement e = marshaller.serialize(any);
        writer.write(getFormat().format(e));
    }
    /////////////////////////////////////

    @Override
    public void writeDocument(Appendable sb, Object any) throws IOException {
        try (Writer writer = AppendableWriter.of(sb)) {
            writeDocument(writer, any);
        }
    }

    @Override
    public void writeDocument(Path file, Object any) throws IOException {
        try (Writer writer = Files.newBufferedWriter(file)) {
            writeDocument(writer, any);
        }
    }

    @Override
    public void writeDocument(File file, Object any) throws IOException {
        try (Writer writer = new FileWriter(file)) {
            writeDocument(writer, any);
        }
    }

    @Override
    public void writeDocument(OutputStream stream, Object any) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(stream);
        writeDocument(writer, any);
        writer.flush();
    }

    @Override
    public void writeDocument(OutputStream stream, String encoding, Object any) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(stream, encoding);
        writeDocument(writer, any);
        writer.flush();
    }

    @Override
    public void writeDocument(Writer writer, Object any) throws IOException {
        if (any instanceof TsonDocument) {
            writer.write(getFormat().format((TsonDocument) any));
        } else {
            TsonElement e = marshaller.serialize(any);
            writer.write(getFormat().format(Tson.document().content(e).build()));
        }
    }

    /////////////////////////////////////

    @Override
    public void write(Appendable sb, TsonElement any) throws IOException {
        try (Writer writer = AppendableWriter.of(sb)) {
            write(writer, any);
        }
    }

    @Override
    public void write(Path file, TsonElement any) throws IOException {
        try (Writer writer = Files.newBufferedWriter(file)) {
            write(writer, any);
        }
    }

    @Override
    public void write(File file, TsonElement any) throws IOException {
        try (Writer writer = new FileWriter(file)) {
            write(writer, any);
        }
    }

    @Override
    public void write(OutputStream stream, TsonElement any) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(stream);
        write(writer, any);
        writer.flush();
    }

    @Override
    public void write(OutputStream stream, String encoding, TsonElement any) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(stream, encoding);
        write(writer, any);
        writer.flush();
    }

    @Override
    public void write(Writer writer, TsonElement any) throws IOException {
        writer.write(getFormat().format(any));
    }
    /////////////////////////////////////

    @Override
    public void writeDocument(Appendable sb, TsonDocument any) throws IOException {
        try (Writer writer = AppendableWriter.of(sb)) {
            writeDocument(writer, any);
        }
    }

    @Override
    public void writeDocument(Path file, TsonDocument any) throws IOException {
        try (Writer writer = Files.newBufferedWriter(file)) {
            writeDocument(writer, any);
        }
    }

    @Override
    public void writeDocument(File file, TsonDocument any) throws IOException {
        try (Writer writer = new FileWriter(file)) {
            writeDocument(writer, any);
        }
    }

    @Override
    public void writeDocument(OutputStream stream, TsonDocument any) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(stream);
        writeDocument(writer, any);
        writer.flush();
    }

    @Override
    public void writeDocument(OutputStream stream, String encoding, TsonDocument any) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(stream, encoding);
        writeDocument(writer, any);
        writer.flush();
    }

    @Override
    public void writeDocument(Writer writer, TsonDocument any) throws IOException {
        writer.write(getFormat().format(any));
    }

    private TsonFormat getFormat() {
        return format;
    }
}
