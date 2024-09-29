package com.juke.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juke.pojo.NewUser;
import com.juke.pojo.SimpleObj;
import org.springframework.core.GenericTypeResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import org.springframework.lang.Nullable;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MyJsonMessageConverter extends AbstractGenericHttpMessageConverter<Object> {

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public MyJsonMessageConverter() {
        super(MediaType.APPLICATION_JSON, new MediaType("application", "*+json"));
        setDefaultCharset(DEFAULT_CHARSET);
    }

    @Override
    public final Object read(Type type, @Nullable Class<?> contextClass, HttpInputMessage inputMessage)throws IOException, HttpMessageNotReadableException {

        return readResolved(GenericTypeResolver.resolveType(type, contextClass), inputMessage);
    }

    @Override
    protected final Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)throws IOException, HttpMessageNotReadableException {

        return readResolved(clazz, inputMessage);
    }

    private Object readResolved(Type resolvedType, HttpInputMessage inputMessage)throws IOException, HttpMessageNotReadableException {

        Reader reader = getReader(inputMessage);
        try {
            return readInternal(resolvedType, reader);
        }
        catch (Exception ex) {
            throw new HttpMessageNotReadableException("Could not read JSON: " + ex.getMessage(), ex, inputMessage);
        }
    }

    @Override
    protected final void writeInternal(Object object, @Nullable Type type, HttpOutputMessage outputMessage)throws IOException, HttpMessageNotWritableException {

        Writer writer = getWriter(outputMessage);

        try {
            writeInternal(object, type, writer);
        }
        catch (Exception ex) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
        }
        writer.flush();
    }


    /**
     * Method that reads the JSON-bound object from the given {@link Reader}.
     * @param resolvedType the resolved generic type
     * @param reader the {@code} Reader to use
     * @return the JSON-bound object
     * @throws Exception in case of read/parse failures
     */
    public Object readInternal(Type resolvedType, Reader reader) throws Exception{
        int i= reader.read();
        String json="";
        while(i!=-1){
            json=json+(char)i;
            i=reader.read();
        }
        ObjectMapper mapper = new ObjectMapper();
        Object obj = mapper.readValue(json, resolvedType.getClass());
        return obj;
    }

    /**
     * Method that writes the JSON-bound object to the given {@link Writer}.
     * @param object the object to write to the output message
     * @param type the type of object to write (may be {@code null})
     * @param writer the {@code} Writer to use
     * @throws Exception in case of write failures
     */
    public void writeInternal(Object object, @Nullable Type type, Writer writer) throws Exception {
        // 创建一个ObjectMapper对象，用于对象和JSON之间的转换
        ObjectMapper objectMapper = new ObjectMapper();
        // 将Java对象转换为JSON树格式
        JsonNode jsonNode = objectMapper.valueToTree(object);
        // 打印JSON树格式
        System.out.println(jsonNode.toString());
        writer.write(jsonNode.toString());
    }



    private static Reader getReader(HttpInputMessage inputMessage) throws IOException {
        return new InputStreamReader(inputMessage.getBody(), getCharset(inputMessage.getHeaders()));
    }

    private static Writer getWriter(HttpOutputMessage outputMessage) throws IOException {
        return new OutputStreamWriter(outputMessage.getBody(), getCharset(outputMessage.getHeaders()));
    }

    private static Charset getCharset(HttpHeaders headers) {
        Charset charset = (headers.getContentType() != null ? headers.getContentType().getCharset() : null);
        return (charset != null ? charset : DEFAULT_CHARSET);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        System.out.println("checking MyJsonMessageConverter applicable to "+clazz.getName());
        return SimpleObj.class.equals(clazz);
    }
}

