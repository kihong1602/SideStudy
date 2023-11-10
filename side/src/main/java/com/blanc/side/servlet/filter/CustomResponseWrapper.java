package com.blanc.side.servlet.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map;

public class CustomResponseWrapper extends HttpServletResponseWrapper {

  private final ByteArrayOutputStream buffer;
  private final ServletOutputStream outputStream;
  private final PrintWriter writer;

  public CustomResponseWrapper(HttpServletResponse response) throws IOException {
    super(response);
    this.buffer = new ByteArrayOutputStream();
    this.outputStream = new CustomOutputStream(buffer);
    this.writer = new PrintWriter(new OutputStreamWriter(buffer, getCharacterEncoding()));
  }

  @Override
  public ServletOutputStream getOutputStream() throws IOException {
    return outputStream;
  }

  @Override
  public PrintWriter getWriter() throws IOException {
    return writer;
  }

  @Override
  public void flushBuffer() throws IOException {
    if (writer != null) {
      writer.flush();
    }
    if (outputStream != null) {
      outputStream.flush();
    }
  }

  public byte[] getResponseAsByteArray() throws IOException {
    flushBuffer();
    return buffer.toByteArray();
  }

  public String getResponseAsString() throws IOException {
    flushBuffer();
    return buffer.toString(getCharacterEncoding());
  }

  public Map<String, Object> getResponseAsMap() throws IOException {
    String responseString = getResponseAsString();
    //JSON 형식의 응답을 Map 으로 변환
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(responseString, new TypeReference<Map<String, Object>>() {
    });
  }

  private class CustomOutputStream extends ServletOutputStream {

    private final ByteArrayOutputStream outputStream;

    public CustomOutputStream(ByteArrayOutputStream outputStream) {
      this.outputStream = outputStream;
    }

    @Override
    public void write(int b) throws IOException {
      outputStream.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
      outputStream.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
      outputStream.write(b, off, len);
    }


    @Override
    public boolean isReady() {
      return true;
    }

    @Override
    public void setWriteListener(WriteListener listener) {

    }
  }
}
