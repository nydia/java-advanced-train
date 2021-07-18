package io.kimmking.cache.redistemplate;

import io.protostuff.LinkedBuffer;
import io.protostuff.Schema;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * @Auther: hqlv
 * @Date: 2021/7/18 23:59
 * @Description:
 */
public class ProtobufSerializer<T> implements RedisSerializer<T> {

    // RuntimeSchema是一个包含业务对象所有信息的类，包括类信息、字段信息
    private static final Schema<ProtoStuffWrapper> schema = RuntimeSchema.getSchema(ProtoStuffWrapper.class);

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if(t == null){
            return null;
        }
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);

        return new byte[0];
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        return null;
    }

    /**
     * 序列化包装类，深度克隆，避免无法获取schema
     *
     * @param <T> 业务对象
     */
    public static class ProtoStuffWrapper<T> implements Cloneable {
        private T t;

        ProtoStuffWrapper() {
        }

        ProtoStuffWrapper(T t) {
            this.t = t;
        }

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }

        @Override
        @SuppressWarnings("unchecked")
        public ProtoStuffWrapper<T> clone() {
            try {
                return (ProtoStuffWrapper<T>) super.clone();
            } catch (CloneNotSupportedException e) {
                return new ProtoStuffWrapper<T>();
            }
        }
    }

//    static class RuntimeSchema{
//
//    }

}
